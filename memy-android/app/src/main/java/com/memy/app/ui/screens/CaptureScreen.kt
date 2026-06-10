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
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.*
import com.memy.app.AppViewModel
import com.memy.app.data.model.*
import com.memy.app.ui.components.*
import com.memy.app.ui.theme.*
import java.time.LocalDate
import java.util.UUID

private val moodOptions = listOf("😌", "🥹", "🤩", "🍜", "⛰️")

@Composable
fun CaptureScreen(
    viewModel: AppViewModel,
    onSave: () -> Unit,
    onClose: () -> Unit,
) {
    var name        by remember { mutableStateOf("") }
    var link        by remember { mutableStateOf("") }
    var description by remember { mutableStateOf("") }
    var selectedMood by remember { mutableStateOf<String?>(null) }
    var tagInput    by remember { mutableStateOf("") }
    var tags        by remember { mutableStateOf(listOf<String>()) }

    Column(
        modifier = Modifier
            .fillMaxSize()
            .background(SurfacePage)
            .verticalScroll(rememberScrollState())
    ) {
        // Header
        Row(
            modifier          = Modifier
                .fillMaxWidth()
                .padding(horizontal = 20.dp, vertical = 14.dp),
            verticalAlignment = Alignment.CenterVertically,
        ) {
            IconButton(onClick = onClose) {
                Icon(Icons.Filled.Close, "Close", tint = TextBody)
            }
            Text(
                "New memy",
                style    = MaterialTheme.typography.headlineSmall.copy(
                    fontFamily = RubikFamily,
                    color      = TextStrong,
                ),
                modifier = Modifier.weight(1f).padding(start = 4.dp),
            )
            Button(
                onClick = {
                    if (name.isNotBlank()) {
                        viewModel.saveMemy(
                            Memy(
                                id          = UUID.randomUUID().toString(),
                                userId      = viewModel.currentUser.id,
                                name        = name,
                                description = description.ifBlank { null },
                                url         = link.ifBlank { null },
                                tags        = tags,
                                mood        = selectedMood,
                                date        = LocalDate.now(),
                            )
                        )
                        onSave()
                    }
                },
                shape  = RoundedCornerShape(12.dp),
                colors = ButtonDefaults.buttonColors(containerColor = Brand),
                contentPadding = PaddingValues(horizontal = 20.dp, vertical = 0.dp),
                modifier = Modifier.height(36.dp),
            ) {
                Text("Save", style = MaterialTheme.typography.labelLarge.copy(color = OnBrand))
            }
        }

        Column(modifier = Modifier.padding(horizontal = 24.dp)) {
            // Photo area
            Box(
                modifier = Modifier
                    .fillMaxWidth()
                    .height(168.dp)
                    .clip(RoundedCornerShape(16.dp))
                    .background(SurfaceSunken)
                    .border(1.5.dp, BorderSubtle, RoundedCornerShape(16.dp)),
                contentAlignment = Alignment.Center,
            ) {
                Column(horizontalAlignment = Alignment.CenterHorizontally) {
                    Icon(Icons.Outlined.AddPhotoAlternate, null,
                        tint = TextMuted, modifier = Modifier.size(32.dp))
                    Spacer(Modifier.height(6.dp))
                    Text("Add a photo", style = MaterialTheme.typography.bodySmall.copy(color = TextMuted))
                }
            }

            Spacer(Modifier.height(20.dp))

            // Name
            UnderlineTextField(name, { name = it }, "Name")

            Spacer(Modifier.height(20.dp))

            // Link
            UnderlineTextField(
                link, { link = it }, "Link",
                leadingIcon = {
                    Icon(Icons.Outlined.Link, null, tint = TextMuted, modifier = Modifier.size(20.dp))
                }
            )

            Spacer(Modifier.height(20.dp))

            // Description
            UnderlineTextField(
                description, { description = it }, "Description",
                singleLine  = false,
            )

            Spacer(Modifier.height(20.dp))

            // Date + Mood row
            Row(Modifier.fillMaxWidth()) {
                // Date
                Column(modifier = Modifier.weight(1f)) {
                    Row(verticalAlignment = Alignment.CenterVertically) {
                        Icon(Icons.Outlined.CalendarToday, null,
                            tint = TextMuted, modifier = Modifier.size(16.dp))
                        Spacer(Modifier.width(4.dp))
                        Text("Date", style = MaterialTheme.typography.bodySmall.copy(
                            color = TextMuted, fontWeight = FontWeight.SemiBold))
                    }
                    Spacer(Modifier.height(8.dp))
                    Box(
                        modifier = Modifier
                            .background(SurfaceSunken, RoundedCornerShape(8.dp))
                            .padding(horizontal = 12.dp, vertical = 10.dp)
                    ) {
                        val today = LocalDate.now()
                        Text(
                            "${today.dayOfMonth} ${monthName(today.monthValue)} ${today.year}",
                            style = MaterialTheme.typography.bodySmall.copy(color = TextStrong),
                        )
                    }
                }

                Spacer(Modifier.width(16.dp))

                // Mood
                Column(modifier = Modifier.weight(1f)) {
                    Text("Mood", style = MaterialTheme.typography.bodySmall.copy(
                        color = TextMuted, fontWeight = FontWeight.SemiBold))
                    Spacer(Modifier.height(8.dp))
                    Row(horizontalArrangement = Arrangement.spacedBy(6.dp)) {
                        moodOptions.forEach { emoji ->
                            val isSelected = emoji == selectedMood
                            Box(
                                modifier = Modifier
                                    .size(38.dp)
                                    .then(
                                        if (isSelected)
                                            Modifier.border(2.dp, Brand, RoundedCornerShape(10.dp))
                                                    .background(BrandWash, RoundedCornerShape(10.dp))
                                        else
                                            Modifier.background(SurfaceSunken, RoundedCornerShape(10.dp))
                                    )
                                    .clickable {
                                        selectedMood = if (isSelected) null else emoji
                                    },
                                contentAlignment = Alignment.Center,
                            ) {
                                Text(emoji, fontSize = 20.sp)
                            }
                        }
                    }
                }
            }

            Spacer(Modifier.height(20.dp))

            // Location mini-map placeholder
            Row(verticalAlignment = Alignment.CenterVertically) {
                Icon(Icons.Outlined.Place, null, tint = TextMuted, modifier = Modifier.size(16.dp))
                Spacer(Modifier.width(4.dp))
                Text("Location", style = MaterialTheme.typography.bodySmall.copy(
                    color = TextMuted, fontWeight = FontWeight.SemiBold))
            }
            Spacer(Modifier.height(8.dp))
            Box(
                modifier = Modifier
                    .fillMaxWidth()
                    .height(96.dp)
                    .clip(RoundedCornerShape(12.dp))
                    .background(SurfaceSunken),
                contentAlignment = Alignment.Center,
            ) {
                // Placeholder — in production embed Google Maps Compose here
                Column(horizontalAlignment = Alignment.CenterHorizontally) {
                    Icon(Icons.Filled.LocationOn, null, tint = Brand, modifier = Modifier.size(24.dp))
                    Text("Tap to pin a location",
                        style = MaterialTheme.typography.labelSmall.copy(color = TextMuted))
                }
            }

            Spacer(Modifier.height(20.dp))

            // Tags
            Row(verticalAlignment = Alignment.CenterVertically) {
                Icon(Icons.Outlined.Sell, null, tint = TextMuted, modifier = Modifier.size(16.dp))
                Spacer(Modifier.width(4.dp))
                Text("Tags", style = MaterialTheme.typography.bodySmall.copy(
                    color = TextMuted, fontWeight = FontWeight.SemiBold))
            }
            Spacer(Modifier.height(8.dp))
            Row(
                horizontalArrangement = Arrangement.spacedBy(6.dp),
                modifier = Modifier.horizontalScroll(rememberScrollState()),
            ) {
                tags.forEach { tag ->
                    TagChip(tag, modifier = Modifier.clickable {
                        tags = tags - tag
                    })
                }
                // Add tag chip
                Box(
                    modifier = Modifier
                        .height(24.dp)
                        .border(1.dp, BorderDefault, RoundedCornerShape(999.dp))
                        .padding(horizontal = 10.dp),
                    contentAlignment = Alignment.Center,
                ) {
                    BasicTextField(
                        value         = tagInput,
                        onValueChange = { v ->
                            if (v.endsWith(" ") || v.endsWith(",")) {
                                val t = v.trim().trimEnd(',')
                                if (t.isNotBlank()) tags = tags + t
                                tagInput = ""
                            } else {
                                tagInput = v
                            }
                        },
                        textStyle = MaterialTheme.typography.labelSmall.copy(
                            color = TextStrong, fontSize = 12.sp),
                        singleLine = true,
                        modifier   = Modifier.widthIn(min = 60.dp),
                        decorationBox = { inner ->
                            if (tagInput.isEmpty()) Text("+ tag",
                                style = MaterialTheme.typography.labelSmall.copy(
                                    color = TextMuted, fontSize = 12.sp))
                            inner()
                        }
                    )
                }
            }

            Spacer(Modifier.height(88.dp))
        }
    }
}

private fun monthName(month: Int) = listOf("Jan","Feb","Mar","Apr","May","Jun",
    "Jul","Aug","Sep","Oct","Nov","Dec")[month - 1]

import androidx.compose.foundation.horizontalScroll
import androidx.compose.foundation.text.BasicTextField
