package com.memy.app.ui.screens

import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.*
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.*
import androidx.compose.material.icons.outlined.*
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.*
import androidx.compose.ui.draw.clip
import androidx.compose.ui.draw.shadow
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.*
import coil.compose.AsyncImage
import com.memy.app.AppViewModel
import com.memy.app.data.model.Memy
import com.memy.app.ui.components.*
import com.memy.app.ui.theme.*

@Composable
fun MapScreen(
    viewModel: AppViewModel,
    onMemyClick: (String) -> Unit,
) {
    val allMemys by viewModel.memys.collectAsState()
    val locatedMemys = remember(allMemys) { allMemys.filter { it.location != null } }
    var selectedMemy by remember { mutableStateOf<Memy?>(null) }

    Box(modifier = Modifier.fillMaxSize()) {
        if (locatedMemys.isEmpty()) {
            // Empty state over the map background
            MapBackground(modifier = Modifier.fillMaxSize())
            Box(modifier = Modifier.fillMaxSize(), contentAlignment = Alignment.Center) {
                EmptyState(
                    icon     = Icons.Filled.WrongLocation,
                    title    = "Nothing on the map yet",
                    subtitle = "Pin a location when you save a memy\nand it'll show up here, right where it happened.",
                    ctaLabel = "Capture a place",
                    onCta    = {},
                )
            }
        } else {
            // Map background
            MapBackground(modifier = Modifier.fillMaxSize())

            // Floating pins (simplified — in production use Google Maps Compose)
            locatedMemys.forEach { memy ->
                val isSelected = selectedMemy?.id == memy.id
                // Position pins deterministically based on lat/lng normalized to screen
                // In production use GoogleMap's Marker composable
                Box(
                    modifier = Modifier
                        .offset(
                            x = normalizeToScreenX(memy.location!!.lng).dp,
                            y = normalizeToScreenY(memy.location.lat).dp,
                        )
                ) {
                    if (isSelected) {
                        // Photo bubble for selected pin
                        Box(
                            modifier = Modifier
                                .size(52.dp)
                                .shadow(8.dp, CircleShape,
                                    spotColor    = Coral400.copy(0.28f),
                                    ambientColor = Coral400.copy(0.18f))
                                .background(Color.White, CircleShape)
                                .clip(CircleShape)
                                .clickable { selectedMemy = null },
                        ) {
                            AsyncImage(
                                model              = memy.photoUrl,
                                contentDescription = memy.name,
                                contentScale       = ContentScale.Crop,
                                modifier           = Modifier.fillMaxSize(),
                            )
                        }
                    } else {
                        Icon(
                            Icons.Filled.LocationOn,
                            contentDescription = memy.name,
                            tint               = Brand,
                            modifier           = Modifier
                                .size(32.dp)
                                .clickable { selectedMemy = memy },
                        )
                    }
                }
            }

            // Search pill at top
            Box(
                modifier = Modifier
                    .align(Alignment.TopCenter)
                    .padding(horizontal = 16.dp, vertical = 56.dp)
            ) {
                Row(
                    modifier = Modifier
                        .fillMaxWidth()
                        .height(44.dp)
                        .background(SurfaceCard, RoundedCornerShape(999.dp))
                        .shadow(2.dp, RoundedCornerShape(999.dp))
                        .padding(horizontal = 14.dp),
                    verticalAlignment = Alignment.CenterVertically,
                ) {
                    Icon(Icons.Outlined.Search, null, tint = TextMuted, modifier = Modifier.size(18.dp))
                    Spacer(Modifier.width(8.dp))
                    Text("Search places & memys",
                        style = MaterialTheme.typography.bodySmall.copy(color = TextSubtle))
                    Spacer(Modifier.weight(1f))
                    Icon(Icons.Filled.Tune, null, tint = TextMuted, modifier = Modifier.size(18.dp))
                }
            }

            // Bottom sheet
            if (selectedMemy != null) {
                val m = selectedMemy!!
                Box(
                    modifier = Modifier
                        .align(Alignment.BottomCenter)
                        .padding(horizontal = 16.dp, vertical = 80.dp) // above nav bar
                ) {
                    Row(
                        modifier = Modifier
                            .fillMaxWidth()
                            .background(SurfaceCard, RoundedCornerShape(16.dp))
                            .shadow(4.dp, RoundedCornerShape(16.dp))
                            .clickable { onMemyClick(m.id) }
                            .padding(12.dp),
                        verticalAlignment = Alignment.CenterVertically,
                    ) {
                        AsyncImage(
                            model              = m.photoUrl,
                            contentDescription = m.name,
                            contentScale       = ContentScale.Crop,
                            modifier           = Modifier
                                .size(60.dp)
                                .clip(RoundedCornerShape(12.dp))
                                .background(SurfaceSunken),
                        )
                        Spacer(Modifier.width(14.dp))
                        Column(modifier = Modifier.weight(1f)) {
                            Text(m.name, style = MaterialTheme.typography.bodyMedium.copy(
                                fontWeight = FontWeight.SemiBold, color = TextStrong))
                            Spacer(Modifier.height(2.dp))
                            Row(verticalAlignment = Alignment.CenterVertically) {
                                Icon(Icons.Outlined.Place, null, tint = TextMuted, modifier = Modifier.size(12.dp))
                                Spacer(Modifier.width(2.dp))
                                Text(
                                    "${m.location?.label ?: ""} · ${formatDate(m.date)}",
                                    style = MaterialTheme.typography.labelSmall.copy(color = TextMuted)
                                )
                            }
                        }
                        Icon(Icons.Filled.ChevronRight, null, tint = TextSubtle)
                    }
                }
            }
        }
    }
}

// Simplified map background drawn in Canvas
@Composable
private fun MapBackground(modifier: Modifier = Modifier) {
    // In production this is replaced by GoogleMap composable.
    // Here we draw a warm paper-style placeholder.
    Box(modifier = modifier.background(Color(0xFFF0EBE3))) {
        // Road lines drawn via Canvas to mimic the map aesthetic from the spec
        androidx.compose.foundation.Canvas(modifier = Modifier.fillMaxSize()) {
            val w = size.width; val h = size.height
            val roadColor = Color(0xFFE0D8CF)
            val waterColor = Color(0xFFD4E8E0)

            // Simulate roads
            drawLine(roadColor, start = androidx.compose.ui.geometry.Offset(0f, h * 0.35f),
                end = androidx.compose.ui.geometry.Offset(w, h * 0.55f), strokeWidth = 28f)
            drawLine(roadColor, start = androidx.compose.ui.geometry.Offset(w * 0.3f, 0f),
                end = androidx.compose.ui.geometry.Offset(w * 0.45f, h), strokeWidth = 20f)
            drawLine(roadColor, start = androidx.compose.ui.geometry.Offset(0f, h * 0.7f),
                end = androidx.compose.ui.geometry.Offset(w, h * 0.65f), strokeWidth = 16f)

            // Water patch bottom-left
            drawOval(waterColor,
                topLeft = androidx.compose.ui.geometry.Offset(-40f, h * 0.75f),
                size = androidx.compose.ui.geometry.Size(w * 0.55f, h * 0.35f))

            // Block fills
            drawRect(Color(0xFFE8E2DA), topLeft = androidx.compose.ui.geometry.Offset(w*0.5f, h*0.4f),
                size = androidx.compose.ui.geometry.Size(w*0.18f, h*0.12f))
            drawRect(Color(0xFFE8E2DA), topLeft = androidx.compose.ui.geometry.Offset(w*0.3f, h*0.58f),
                size = androidx.compose.ui.geometry.Size(w*0.14f, h*0.10f))
        }
    }
}

// Naive coordinate mapping for the demo pins (not geographically accurate)
private fun normalizeToScreenX(lng: Double): Float = ((lng + 180) / 360 * 320).toFloat()
private fun normalizeToScreenY(lat: Double): Float = ((90 - lat) / 180 * 560).toFloat()
