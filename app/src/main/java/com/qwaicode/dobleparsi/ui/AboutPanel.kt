/*
 * BH VoiceDub Android application
 * Copyright (c) 2026 BH VoiceDub
 */
package com.qwaicode.dobleparsi.ui

import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Copyright
import androidx.compose.material.icons.filled.GraphicEq
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import com.qwaicode.dobleparsi.R
import com.qwaicode.dobleparsi.core.Credits

/** A clean, self-contained app information panel with no developer profile or external links. */
@Composable
fun AboutPanel() {
    Panel(title = str(R.string.section_about), icon = Icons.Filled.Copyright) {
        Row(verticalAlignment = Alignment.CenterVertically, modifier = Modifier.fillMaxWidth()) {
            Icon(
                imageVector = Icons.Filled.GraphicEq,
                contentDescription = str(R.string.app_name),
                tint = DP.Brand,
                modifier = Modifier.size(42.dp)
            )
            Spacer(Modifier.width(14.dp))
            Column(Modifier.weight(1f)) {
                Text(
                    text = Credits.AUTHOR,
                    style = MaterialTheme.typography.titleSmall,
                    fontWeight = FontWeight.Bold,
                    color = DP.Text
                )
                Spacer(Modifier.height(3.dp))
                Text(
                    text = str(R.string.about_role),
                    style = MaterialTheme.typography.labelSmall,
                    color = DP.TextMuted
                )
            }
        }
        Spacer(Modifier.height(14.dp))
        RowDivider()
        Spacer(Modifier.height(12.dp))
        Text(
            text = "${Credits.COPYRIGHT} · ${str(R.string.about_rights)}",
            style = MaterialTheme.typography.labelSmall,
            color = DP.TextSecondary
        )
        Spacer(Modifier.height(5.dp))
        Text(
            text = str(R.string.about_license),
            style = MaterialTheme.typography.labelSmall,
            color = DP.TextMuted
        )
        Spacer(Modifier.height(5.dp))
        Text(
            text = str(R.string.about_source_credit),
            style = MaterialTheme.typography.labelSmall,
            color = DP.TextMuted
        )
    }
}
