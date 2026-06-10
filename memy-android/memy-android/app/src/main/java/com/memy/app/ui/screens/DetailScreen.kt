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
import androidx.compose.ui.graphics.*
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.*
import coil.compose.AsyncImage
import com.memy.app.AppViewModel
import com.memy.app.ui.components.*
import com.memy.app.ui.theme.*

@Composable
fun DetailScreen(
    memyId: String,
    viewModel: AppViewModel,
    onBack: () -> Unit,
) {
    val memy = viewModel.repo.getMemyById(memyId) ?: run {
        onBack(); return
    }

    Column(
        modifier = Modifier
            .fillMaxSize()
            .background(SurfacePage)
            .verticalScroll(rememberScrollState())
    ) {
        // Hero photo
        Box(
            modifier = Modifier
                .fillMaxWidth()
                .height(320.dp)
        ) {
            if (memy.photoUrl != null) {
                AsyncImage(
                    model              = memy.photoUrl,
                    contentDescription = memy.name,
                    contentScale       = ContentScale.Crop,
                    modifier           = Modifier.fillMaxSize(),
                )
            } else {
                Box(Modifier.fillMaxSize().background(SurfaceSunken))
            }

            // Top gradient scrim
            Box(
                modifier = Modifier
                    .fillMaxWidth()
                    .height(100.dp)
                    .background(
                        Brush.verticalGradient(
                            listOf(Color.Black.copy(0.45f), Color.Transparent)
                        )
                    )
                    .align(Alignment.TopCenter)
            )

            // Back button (glass)
            GlassIconButton(
                icon      = Icons.Filled.ArrowBack,
                onClick   = onBack,
                modifier  = Modifier
                    .align(Alignment.TopStart)
                    .padding(start = 16.dp, top = 48.dp),
            )

            // Favorite + more (glass)
            Row(
                modifier = Modifier
                    .align(Alignment.TopEnd)
                    .padding(end = 16.dp, top = 48.dp),
                horizontalArrangement = Arrangement.spacedBy(8.dp),
            ) {
                GlassIconButton(Icons.Filled.FavoriteBorder, onClick = {})
                GlassIconButton(Icons.Filled.MoreHoriz, onClick = {})
            }
        }

        // Body
        Column(modifier = Modifier.padding(horizontal = 20.dp, vertical = 20.dp)) {
            Text(
                memy.name,
                style = MaterialTheme.typography.headlineLarge.copy(
                    fontFamily = RubikFamily,
                    fontWeight = FontWeight.Bold,
                    color      = TextStrong,
                    fontSize   = 26.sp,
                )
            )

            Spacer(Modifier.height(10.dp))

            // Meta row
            Row(
                horizontalArrangement = Arrangement.spacedBy(16.dp),
                verticalAlignment     = Alignment.CenterVertically,
            ) {
                MetaItem(Icons.Outlined.CalendarToday, formatDate(memy.date))
                if (memy.location != null) MetaItem(Icons.Outlined.Place, memy.location.label)
                if (memy.mood != null)     MetaItem(Icons.Outlined.Mood, memy.mood)
            }

            if (memy.description != null) {
                Spacer(Modifier.height(16.dp))
                Text(
                    memy.description,
                    style = MaterialTheme.typography.bodyMedium.copy(
                        color      = TextBody,
                        lineHeight = 24.sp,
                    )
                )
            }

            // Link card
            if (memy.url != null) {
                Spacer(Modifier.height(16.dp))
                Row(
                    modifier          = Modifier
                        .fillMaxWidth()
                        .background(SurfaceSunken, RoundedCornerShape(12.dp))
                        .padding(horizontal = 14.dp, vertical = 12.dp),
                    verticalAlignment = Alignment.CenterVertically,
                ) {
                    Icon(Icons.Outlined.Link, null, tint = Brand, modifier = Modifier.size(18.dp))
                    Spacer(Modifier.width(10.dp))
                    Text(
                        memy.url,
                        style    = MaterialTheme.typography.bodySmall.copy(color = Brand),
                        modifier = Modifier.weight(1f),
                        maxLines = 1,
                    )
                    Icon(Icons.Outlined.OpenInNew, null, tint = TextMuted, modifier = Modifier.size(16.dp))
                }
            }

            // Tags
            if (memy.tags.isNotEmpty()) {
                Spacer(Modifier.height(14.dp))
                Row(horizontalArrangement = Arrangement.spacedBy(6.dp)) {
                    memy.tags.forEach { TagChip(it) }
                }
            }

            // Mini map placeholder
            if (memy.location != null) {
                Spacer(Modifier.height(16.dp))
                Box(
                    modifier = Modifier
                        .fillMaxWidth()
                        .height(140.dp)
                        .clip(RoundedCornerShape(12.dp))
                        .background(SurfaceSunken),
                    contentAlignment = Alignment.Center,
                ) {
                    // In production use GoogleMap composable here
                    Column(horizontalAlignment = Alignment.CenterHorizontally) {
                        Icon(Icons.Filled.LocationOn, null, tint = Brand, modifier = Modifier.size(28.dp))
                        Text(memy.location.label,
                            style = MaterialTheme.typography.bodySmall.copy(color = TextMuted))
                    }
                }
            }

            Spacer(Modifier.height(88.dp))
        }
    }
}

@Composable
private fun GlassIconButton(
    icon: androidx.compose.ui.graphics.vector.ImageVector,
    onClick: () -> Unit,
    modifier: Modifier = Modifier,
) {
    Box(
        modifier = modifier
            .size(40.dp)
            .background(
                Color.Black.copy(alpha = 0.35f),
                CircleShape
            )
            .clickable(onClick = onClick),
        contentAlignment = Alignment.Center,
    ) {
        Icon(icon, null, tint = Color.White, modifier = Modifier.size(20.dp))
    }
}

@Composable
private fun MetaItem(
    icon: androidx.compose.ui.graphics.vector.ImageVector,
    text: String,
) {
    Row(verticalAlignment = Alignment.CenterVertically) {
        Icon(icon, null, tint = TextMuted, modifier = Modifier.size(15.dp))
        Spacer(Modifier.width(4.dp))
        Text(text, style = MaterialTheme.typography.labelSmall.copy(color = TextMuted))
    }
}
