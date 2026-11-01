/*
 * Doble Parsi V1.1.0 - live AI dubbing for Android
 * Engineered by QW-AI-Code
 *
 * Copyright (c) 2026 QW-AI-Code - https://github.com/QW-AI-Code
 * SPDX-FileCopyrightText: 2026 QW-AI-Code (github.com/QW-AI-Code)
 */
package com.qwaicode.dobleparsi.net

import okhttp3.MediaType.Companion.toMediaType
import okhttp3.OkHttpClient
import okhttp3.Request
import okhttp3.RequestBody.Companion.toRequestBody
import org.json.JSONArray
import org.json.JSONObject
import java.util.concurrent.TimeUnit

/**
 * API key + connection test — port of src/api-check.js.
 * One tiny request (maxOutputTokens = 1) and a classification of the answer.
 */
object ApiCheck {

    enum class Kind { OK, NO_KEY, BAD_KEY, QUOTA, MISSING, OFFLINE, ERROR }

    data class Result(
        val kind: Kind,
        val model: String = "",
        val ms: Long = 0,
        val retrySeconds: Long = 0
    )

    private const val BASE = "https://generativelanguage.googleapis.com/v1beta"
    private val MODELS = listOf("gemini-flash-latest", "gemini-flash-lite-latest", "gemini-2.5-flash")

    private val http = OkHttpClient.Builder()
        .callTimeout(20, TimeUnit.SECONDS)
        .build()

    private val body = JSONObject()
        .put("contents", JSONArray().put(JSONObject()
            .put("role", "user")
            .put("parts", JSONArray().put(JSONObject().put("text", "ping")))))
        .put("generationConfig", JSONObject().put("maxOutputTokens", 1).put("temperature", 0))
        .toString()

    /** Blocking; call from a background dispatcher. */
    fun test(apiKey: String): Result {
        val key = apiKey.trim()
        if (key.isEmpty()) return Result(Kind.NO_KEY)
        var last = Result(Kind.ERROR)
        for (model in MODELS) {
            val started = System.currentTimeMillis()
            val request = Request.Builder()
                .url("$BASE/models/$model:generateContent")
                .header("x-goog-api-key", key)
                .post(body.toRequestBody("application/json".toMediaType()))
                .build()
            last = try {
                http.newCall(request).execute().use { response ->
                    val raw = response.body?.string().orEmpty()
                    val message = runCatching {
                        JSONObject(raw).optJSONObject("error")?.optString("message").orEmpty()
                    }.getOrDefault("")
                    classify(response.code, message, model, System.currentTimeMillis() - started)
                }
            } catch (_: Throwable) {
                Result(Kind.OFFLINE, model)
            }
            if (last.kind != Kind.MISSING && last.kind != Kind.ERROR) return last
        }
        return last
    }

    private fun classify(status: Int, message: String, model: String, ms: Long): Result = when {
        status == 200 -> Result(Kind.OK, model, ms)
        status == 429 || Regex("quota|exhausted|rate", RegexOption.IGNORE_CASE).containsMatchIn(message) ->
            Result(Kind.QUOTA, model, ms, retrySeconds(message))
        status == 404 || Regex("not found|not supported|does not exist", RegexOption.IGNORE_CASE).containsMatchIn(message) ->
            Result(Kind.MISSING, model, ms)
        status == 403 || (status == 400 && Regex("api key", RegexOption.IGNORE_CASE).containsMatchIn(message)) ->
            Result(Kind.BAD_KEY, model, ms)
        else -> Result(Kind.ERROR, model, ms)
    }

    private fun retrySeconds(message: String): Long =
        Regex("retry in ([0-9.]+)s", RegexOption.IGNORE_CASE).find(message)
            ?.groupValues?.get(1)?.toDoubleOrNull()?.let { Math.ceil(it).toLong() } ?: 0L
}
