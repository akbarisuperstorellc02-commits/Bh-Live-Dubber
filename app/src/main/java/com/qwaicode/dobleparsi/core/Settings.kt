/*
 * Doble Parsi V1.1.0 - live AI dubbing for Android
 * Engineered by QW-AI-Code
 *
 * Copyright (c) 2026 QW-AI-Code - https://github.com/QW-AI-Code
 * SPDX-FileCopyrightText: 2026 QW-AI-Code (github.com/QW-AI-Code)
 */
package com.qwaicode.dobleparsi.core

import android.content.Context
import androidx.core.content.edit
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow

/**
 * Persisted settings - mirrors src/defaults.js of the extension.
 *
 * Defaults are tuned for "the dub starts together with the original audio and the
 * original stays quiet":
 *   dubSyncMs     = 0      -> no artificial delay on top of the model latency
 *   originalLevel = 0.18   -> the original is only a quiet bed under the dub
 */
data class Settings(
    val apiKey: String = "",
    val uiLang: String = "fa",
    val targetLang: String = "fa",
    val translateModel: String = Languages.MODELS.first(),
    /** Dub audible (false = dub muted). */
    val livePlayback: Boolean = true,
    /** Original video sound muted (media stream set to 0 while dubbing). */
    val originalMuted: Boolean = false,
    /** Original video volume as a fraction of the device maximum; -1 = keep the current device volume. */
    val originalVolume: Float = -1f,
    /** Dub voice volume, 0..1. */
    val dubVolume: Float = 1f,
    val saveAudio: Boolean = true,
    val audioSource: String = "mix",
    val saveSubtitle: Boolean = true,
    val subtitleFormat: String = "srt",
    val bilingualSubtitle: Boolean = false,
    val subtitleOffsetMs: Int = 1200,
    val useOriginalFilename: Boolean = true,

    // -- dub timing / original level ---------------------------------------
    /** Extra delay added on purpose, 0 = exactly in sync with the original. */
    val dubSyncMs: Int = 0,
    /** Level of the original audio inside the saved mix (0.02 .. 0.6). */
    val originalLevel: Float = 0.18f,

    // -- floating bubble ---------------------------------------------------
    val showBubble: Boolean = true,
    /** Keep the bubble on screen after a session so the next dub starts from it. */
    val keepBubble: Boolean = true,
    /** Show the running dub time inside the bubble. */
    val bubbleTimer: Boolean = true,
    /** Fade the bubble down while it is idle. */
    val bubbleFade: Boolean = true,
    /** Collapse the bubble into a small edge tab while it is idle. */
    val bubbleCollapse: Boolean = true,
    /** Snap / auto-move the bubble to the closest screen edge after a drag. */
    val bubbleSnap: Boolean = true,
    /** Smart mode: follow the session state, wake up on its own, stay out of the way. */
    val bubbleSmart: Boolean = true,
    /** Bubble size multiplier (0.8 .. 1.4). */
    val bubbleScale: Float = 1.0f,

    // -- Gemini token monitor ----------------------------------------------
    val tokenMonitor: Boolean = true,
    /** Daily token budget copied from AI Studio, 0 = unknown / not set. */
    val dailyTokenLimit: Int = 0
)

class SettingsStore private constructor(context: Context) {

    companion object {
        @Volatile
        private var instance: SettingsStore? = null

        /** One store per process, so the service and the UI never drift apart. */
        fun get(context: Context): SettingsStore =
            instance ?: synchronized(this) {
                instance ?: SettingsStore(context).also { instance = it }
            }
    }

    private val prefs = context.applicationContext
        .getSharedPreferences("doble_parsi_settings", Context.MODE_PRIVATE)

    private val _state = MutableStateFlow(read())
    val state: StateFlow<Settings> = _state.asStateFlow()

    val value: Settings get() = _state.value

    private fun read(): Settings {
        val d = Settings()
        return Settings(
            apiKey = prefs.getString("apiKey", d.apiKey).orEmpty(),
            uiLang = prefs.getString("uiLang", d.uiLang) ?: d.uiLang,
            targetLang = prefs.getString("targetLang", d.targetLang) ?: d.targetLang,
            translateModel = prefs.getString("translateModel", d.translateModel) ?: d.translateModel,
            livePlayback = prefs.getBoolean("livePlayback", d.livePlayback),
            originalMuted = prefs.getBoolean("originalMuted", d.originalMuted),
            originalVolume = prefs.getFloat("originalVolume", d.originalVolume),
            dubVolume = prefs.getFloat("dubVolume", d.dubVolume),
            saveAudio = prefs.getBoolean("saveAudio", d.saveAudio),
            audioSource = prefs.getString("audioSource", d.audioSource) ?: d.audioSource,
            saveSubtitle = prefs.getBoolean("saveSubtitle", d.saveSubtitle),
            subtitleFormat = prefs.getString("subtitleFormat", d.subtitleFormat) ?: d.subtitleFormat,
            bilingualSubtitle = prefs.getBoolean("bilingualSubtitle", d.bilingualSubtitle),
            subtitleOffsetMs = prefs.getInt("subtitleOffsetMs", d.subtitleOffsetMs),
            useOriginalFilename = prefs.getBoolean("useOriginalFilename", d.useOriginalFilename),
            dubSyncMs = prefs.getInt("dubSyncMs", d.dubSyncMs),
            originalLevel = prefs.getFloat("originalLevel", d.originalLevel),
            showBubble = prefs.getBoolean("showBubble", d.showBubble),
            keepBubble = prefs.getBoolean("keepBubble", d.keepBubble),
            bubbleTimer = prefs.getBoolean("bubbleTimer", d.bubbleTimer),
            bubbleFade = prefs.getBoolean("bubbleFade", d.bubbleFade),
            bubbleCollapse = prefs.getBoolean("bubbleCollapse", d.bubbleCollapse),
            bubbleSnap = prefs.getBoolean("bubbleSnap", d.bubbleSnap),
            bubbleSmart = prefs.getBoolean("bubbleSmart", d.bubbleSmart),
            bubbleScale = prefs.getFloat("bubbleScale", d.bubbleScale),
            tokenMonitor = prefs.getBoolean("tokenMonitor", d.tokenMonitor),
            dailyTokenLimit = prefs.getInt("dailyTokenLimit", d.dailyTokenLimit)
        )
    }

    /** Re-reads from disk; used by the service when it wakes up from standby. */
    fun refresh(): Settings {
        val current = read()
        _state.value = current
        return current
    }

    fun update(transform: (Settings) -> Settings) {
        val next = transform(_state.value)
        prefs.edit {
            putString("apiKey", next.apiKey)
            putString("uiLang", next.uiLang)
            putString("targetLang", next.targetLang)
            putString("translateModel", next.translateModel)
            putBoolean("livePlayback", next.livePlayback)
            putBoolean("originalMuted", next.originalMuted)
            putFloat("originalVolume", next.originalVolume)
            putFloat("dubVolume", next.dubVolume)
            putBoolean("saveAudio", next.saveAudio)
            putString("audioSource", next.audioSource)
            putBoolean("saveSubtitle", next.saveSubtitle)
            putString("subtitleFormat", next.subtitleFormat)
            putBoolean("bilingualSubtitle", next.bilingualSubtitle)
            putInt("subtitleOffsetMs", next.subtitleOffsetMs)
            putBoolean("useOriginalFilename", next.useOriginalFilename)
            putInt("dubSyncMs", next.dubSyncMs)
            putFloat("originalLevel", next.originalLevel)
            putBoolean("showBubble", next.showBubble)
            putBoolean("keepBubble", next.keepBubble)
            putBoolean("bubbleTimer", next.bubbleTimer)
            putBoolean("bubbleFade", next.bubbleFade)
            putBoolean("bubbleCollapse", next.bubbleCollapse)
            putBoolean("bubbleSnap", next.bubbleSnap)
            putBoolean("bubbleSmart", next.bubbleSmart)
            putFloat("bubbleScale", next.bubbleScale)
            putBoolean("tokenMonitor", next.tokenMonitor)
            putInt("dailyTokenLimit", next.dailyTokenLimit)
        }
        _state.value = next
    }
}
