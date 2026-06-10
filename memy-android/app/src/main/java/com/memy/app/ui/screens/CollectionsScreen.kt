package com.memy.app.ui.screens

import androidx.compose.animation.core.*
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.grid.*
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.*
import androidx.compose.material.icons.outlined.*
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.*
import androidx.compose.ui.draw.alpha
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.*
import com.memy.app.AppViewModel
import com.memy.app.ui.components.*
import com.memy.app.ui.theme.*
import kotlinx.coroutines.delay

// ── Collections Grid ──────────────────────────────────────────────────────────
@Composable
fun CollectionsScreen(
    viewModel: AppViewModel,
    onCollectionClick: (String) -> Unit,
) {
    val collections by viewModel.collections.collectAsState()

    Column(
        modifier = Modifier
            .fillMaxSize()
            .background(SurfacePage)
    ) {
        // Title bar
        Row(
            modifier          = Modifier
                .fillMaxWidth()
                .padding(horizontal = 20.dp, vertical = 16.dp),
            verticalAlignment = Alignment.CenterVertically,
        ) {
            Text(
                "Collections",
                style = MaterialTheme.typography.headlineLarge.copy(
                    fontFamily = RubikFamily,
                    fontWeight = FontWeight.Bold,
                    color      = TextStrong,
                )
            )
            Spacer(Modifier.weight(1f))
            IconButton(onClick = { /* new collection */ }) {
                Icon(Icons.Filled.Add, "New collection", tint = TextBody)
            }
        }

        if (collections.isEmpty()) {
            Box(modifier = Modifier.fillMaxSize(), contentAlignment = Alignment.Center) {
                EmptyState(
                    icon     = Icons.Filled.GridView,
                    title    = "No collections yet",
                    subtitle = "Group memys into albums — Trips,\nGood eats, Reading. Make your first\none and start sorting.",
                    ctaLabel = "New collection",
                    onCta    = {},
                )
            }
        } else {
            LazyVerticalGrid(
                columns             = GridCells.Fixed(2),
                modifier            = Modifier.fillMaxSize(),
                contentPadding      = PaddingValues(horizontal = 16.dp, vertical = 4.dp),
                horizontalArrangement = Arrangement.spacedBy(12.dp),
                verticalArrangement = Arrangement.spacedBy(12.dp),
            ) {
                itemsIndexed(collections, key = { _, c -> c.id }) { index, col ->
                    val alpha = remember { Animatable(0f) }
                    LaunchedEffect(col.id) {
                        delay((index * 60).toLong())
                        alpha.animateTo(1f, tween(220))
                    }
                    CollectionCard(
                        name       = col.name,
                        memyCount  = col.memyCount,
                        coverUrls  = col.coverUrls,
                        onClick    = { onCollectionClick(col.id) },
                        modifier   = Modifier.alpha(alpha.value),
                    )
                }
                item(span = { GridItemSpan(2) }) {
                    Spacer(Modifier.height(88.dp))
                }
            }
        }
    }
}

// ── Album (single collection) ─────────────────────────────────────────────────
@Composable
fun AlbumScreen(
    collectionId: String,
    viewModel: AppViewModel,
    onBack: () -> Unit,
    onMemyClick: (String) -> Unit,
) {
    val collection = viewModel.repo.getCollectionById(collectionId)
    val memys      = viewModel.memysForCollection(collectionId)

    Column(
        modifier = Modifier
            .fillMaxSize()
            .background(SurfacePage)
    ) {
        // Coral textured header (WaveHeader variant)
        Box(
            modifier = Modifier
                .fillMaxWidth()
                .height(140.dp)
        ) {
            WaveHeader(heightDp = 140.dp)
            IconButton(
                onClick  = onBack,
                modifier = Modifier.align(Alignment.TopStart).padding(start = 8.dp, top = 40.dp),
            ) {
                Icon(Icons.Filled.ArrowBack, "Back", tint = OnBrand)
            }
            Column(
                modifier = Modifier
                    .align(Alignment.BottomStart)
                    .padding(start = 20.dp, bottom = 28.dp),
            ) {
                Text(
                    collection?.name ?: "Album",
                    style = MaterialTheme.typography.headlineMedium.copy(
                        fontFamily = RubikFamily, color = OnBrand, fontWeight = FontWeight.Bold)
                )
                Text(
                    "${memys.size} memys",
                    style = MaterialTheme.typography.bodySmall.copy(color = OnBrand.copy(0.8f))
                )
            }
        }

        if (memys.isEmpty()) {
            Box(modifier = Modifier.fillMaxSize(), contentAlignment = Alignment.Center) {
                EmptyState(
                    icon     = Icons.Outlined.AddLocationAlt,
                    title    = "This album is empty",
                    subtitle = "Add memys from your collection or\ncapture a new one to drop it straight in here.",
                    ctaLabel = "Add to ${collection?.name ?: "album"}",
                    onCta    = {},
                )
            }
        } else {
            LazyVerticalGrid(
                columns             = GridCells.Fixed(2),
                modifier            = Modifier.fillMaxSize(),
                contentPadding      = PaddingValues(16.dp),
                horizontalArrangement = Arrangement.spacedBy(12.dp),
                verticalArrangement = Arrangement.spacedBy(12.dp),
            ) {
                itemsIndexed(memys, key = { _, m -> m.id }) { index, memy ->
                    val photoH = if (index % 3 == 0) 184.dp else 132.dp
                    MemyCard(
                        name          = memy.name,
                        photoUrl      = memy.photoUrl,
                        mood          = memy.mood,
                        place         = memy.location?.label,
                        date          = formatDate(memy.date),
                        tags          = memy.tags,
                        photoHeightDp = photoH,
                        onClick       = { onMemyClick(memy.id) },
                    )
                }
                item(span = { GridItemSpan(2) }) { Spacer(Modifier.height(88.dp)) }
            }
        }
    }
}
