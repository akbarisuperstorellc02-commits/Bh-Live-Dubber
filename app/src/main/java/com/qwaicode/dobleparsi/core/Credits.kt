/*
 * BH VoiceDub Android application
 * Copyright (c) 2026 BH VoiceDub
 */
package com.qwaicode.dobleparsi.core

/** Single source of truth for the app's displayed identity and copyright. */
object Credits {
    const val AUTHOR = "BH VoiceDub"
    const val YEAR = "2026"
    const val VERSION = "1.0"

    const val COPYRIGHT = "© $YEAR $AUTHOR"
    const val COPYRIGHT_LONG = COPYRIGHT

    private val SIGNATURES = listOf(
        "BH VoiceDub",
        "Live dubbing for Android",
        "Made for clear listening"
    )

    fun signature(): String = SIGNATURES.first()
    fun signature(seed: Int): String = SIGNATURES[Math.floorMod(seed, SIGNATURES.size)]
}
