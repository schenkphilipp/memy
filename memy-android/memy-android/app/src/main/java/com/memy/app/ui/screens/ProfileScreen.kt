package com.memy.app.ui.screens

import androidx.compose.foundation.*
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.*
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.*
import androidx.compose.material.icons.outlined.*
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.*
import androidx.compose.ui.draw.*
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.*
import com.memy.app.AppViewModel
import com.memy.app.ui.components.*
import com.memy.app.ui.theme.*

@Composable
fun ProfileScreen(viewModel: AppViewModel) {
    val user        = viewModel.currentUser
    val memyCount   by viewModel.memys.collectAsState()
    val colCount    by viewModel.collections.collectAsState()
    val placeCount  = memyCount.count { it.location != null }

    Column(
        modifier = Modifier
            .fillMaxSize()
            .background(SurfacePage)
            .verticalScroll(rememberScrollState())
    ) {
        // Coral textured header
        Box(
            modifier = Modifier
                .fillMaxWidth()
                .height(150.dp)
        ) {
            WaveHeader(heightDp = 150.dp)
        }

        Column(modifier = Modifier.padding(horizontal = 20.dp)) {
            // Avatar overlapping header
            Box(modifier = Modifier.offset(y = (-40).dp)) {
                Box(
                    modifier = Modifier
                        .size(92.dp)
                        .background(SurfaceCard, CircleShape)
                        .padding(3.dp)
                ) {
                    Avatar(url = user.avatarUrl, size = 86.dp)
                }
            }

            // Name + subtitle (pull up to close offset gap)
            Column(modifier = Modifier.offset(y = (-28).dp)) {
                Text(
                    user.name,
                    style = MaterialTheme.typography.headlineLarge.copy(
                        fontFamily = RubikFamily,
                        fontWeight = FontWeight.Bold,
                        color      = TextStrong,
                        fontSize   = 24.sp,
                    )
                )
                Spacer(Modifier.height(2.dp))
                Text(
                    "Keeping ${memyCount.size} memys across ${colCount.size} collections",
                    style = MaterialTheme.typography.bodyMedium.copy(color = TextMuted),
                )

                Spacer(Modifier.height(16.dp))

                // Stats row
                Row(
                    modifier              = Modifier.fillMaxWidth(),
                    horizontalArrangement = Arrangement.spacedBy(32.dp),
                ) {
                    StatItem(memyCount.size.toString(), "Memys")
                    StatItem(colCount.size.toString(),  "Collections")
                    StatItem(placeCount.toString(),     "Places")
                }

                Spacer(Modifier.height(24.dp))

                // Settings list
                val settingsItems = listOf(
                    Triple(Icons.Outlined.Settings,         "Settings",       {}),
                    Triple(Icons.Outlined.Notifications,    "Notifications",  {}),
                    Triple(Icons.Outlined.Lock,             "Privacy",        {}),
                    Triple(Icons.Outlined.HelpOutline,      "Help & feedback",{}),
                )

                settingsItems.forEachIndexed { index, (icon, label, action) ->
                    if (index > 0) Divider(color = BorderSubtle, thickness = 0.5.dp)
                    SettingsRow(icon = icon, label = label, onClick = action)
                }
            }
        }
    }
}

@Composable
private fun StatItem(value: String, label: String) {
    Column {
        Text(
            value,
            style = MaterialTheme.typography.headlineMedium.copy(
                fontFamily = RubikFamily,
                fontWeight = FontWeight.Bold,
                color      = TextStrong,
            )
        )
        Text(
            label,
            style = MaterialTheme.typography.labelSmall.copy(color = TextMuted)
        )
    }
}

@Composable
private fun SettingsRow(
    icon: ImageVector,
    label: String,
    onClick: () -> Unit,
) {
    Row(
        modifier          = Modifier
            .fillMaxWidth()
            .height(52.dp)
            .clickable(onClick = onClick),
        verticalAlignment = Alignment.CenterVertically,
    ) {
        Icon(icon, null, tint = TextBody, modifier = Modifier.size(22.dp))
        Spacer(Modifier.width(14.dp))
        Text(
            label,
            style    = MaterialTheme.typography.bodyLarge.copy(color = TextStrong),
            modifier = Modifier.weight(1f),
        )
        Icon(Icons.Filled.ChevronRight, null, tint = TextSubtle, modifier = Modifier.size(20.dp))
    }
}
