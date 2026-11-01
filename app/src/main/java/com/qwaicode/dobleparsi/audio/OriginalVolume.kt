/*
 * Doble Parsi V1.1.0 - live AI dubbing for Android
 * Engineered by QW-AI-Code
 *
 * Copyright (c) 2026 QW-AI-Code - https://github.com/QW-AI-Code
 * SPDX-FileCopyrightText: 2026 QW-AI-Code (github.com/QW-AI-Code)
 */
package com.qwaicode.dobleparsi.audio

import android.content.Context
import android.media.AudioManager

/**
 * Real mute for the original video sound — the Android twin of the extension's
 * `originalMonitorGain`.
 *
 * Audio focus ducking (the old approach) is only a request that most players ignore,
 * which is why the original never got quieter. Here the media stream itself is set
 * to 0 and restored afterwards. The dub is played on the alarm stream (see
 * [DubPlayer]) so it keeps its own volume and is not silenced together with the
 * original. MediaProjection playback capture reads the audio before the stream
 * volume is applied, so the model still hears the original while it is muted.
 */
class OriginalVolume(context: Context) {

    private val audioManager = context.getSystemService(Context.AUDIO_SERVICE) as AudioManager

    private var savedMusic = -1
    private var savedAlarm = -1
    private var engaged = false

    /** Call once when the session starts: makes the (separate) dub stream audible. */
    fun engage() {
        if (engaged) return
        engaged = true
        savedMusic = audioManager.getStreamVolume(AudioManager.STREAM_MUSIC)
        savedAlarm = audioManager.getStreamVolume(AudioManager.STREAM_ALARM)
        val alarmMax = audioManager.getStreamMaxVolume(AudioManager.STREAM_ALARM)
        val audible = (alarmMax * 0.8f).toInt().coerceAtLeast(1)
        if (savedAlarm < audible) set(AudioManager.STREAM_ALARM, audible)
    }

    /** Volume of the device as a fraction (0..1) of its maximum. */
    fun currentFraction(): Float {
        val max = audioManager.getStreamMaxVolume(AudioManager.STREAM_MUSIC).coerceAtLeast(1)
        return audioManager.getStreamVolume(AudioManager.STREAM_MUSIC).toFloat() / max
    }

    /**
     * Applies the original video volume. [fraction] < 0 means "the volume the user had
     * when the session started"; [muted] forces 0.
     */
    fun apply(muted: Boolean, fraction: Float) {
        if (!engaged) return
        val max = audioManager.getStreamMaxVolume(AudioManager.STREAM_MUSIC).coerceAtLeast(1)
        val target = when {
            muted -> 0
            fraction < 0f -> savedMusic.coerceIn(0, max)
            else -> Math.round(fraction.coerceIn(0f, 1f) * max)
        }
        set(AudioManager.STREAM_MUSIC, target)
    }

    /** Puts both streams back exactly as the user had them. */
    fun release() {
        if (!engaged) return
        engaged = false
        if (savedMusic >= 0) set(AudioManager.STREAM_MUSIC, savedMusic)
        if (savedAlarm >= 0) set(AudioManager.STREAM_ALARM, savedAlarm)
    }

    private fun set(stream: Int, volume: Int) {
        runCatching { audioManager.setStreamVolume(stream, volume, 0) }
    }
}
