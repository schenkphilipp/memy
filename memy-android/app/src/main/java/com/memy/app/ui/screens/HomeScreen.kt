package com.memy.app.ui.screens

import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.staggeredgrid.*
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.horizontalScroll
import androidx.compose.runtime.*
import androidx.compose.ui.*
import androidx.compose.ui.unit.dp
import com.memy.app.AppViewModel
import com.memy.app.data.model.Memy
import com.memy.app.ui.components.*
import com.memy.app.ui.theme.*
import androidx.compose.animation.*
import androidx.compose.animation.core.*
import androidx.compose.foundation.background
import androidx.compose.ui.draw.alpha

private val filterLabels = listOf("All", "Places", "Notes", "Links", "Food")

@Composable
fun HomeScreen(
    viewModel: AppViewModel,
    onMemyClick: (String) -> Unit,
    onSearchClick: () -> Unit,
) {
    val allMemys by viewModel.memys.collectAsState()
    var selectedFilter by remember { mutableStateOf("All") }

    val filtered = remember(allMemys, selectedFilter) {
        when (selectedFilter) {
            "Places" -> allMemys.filter { it.location != null }
            "Notes"  -> allMemys.filter { it.description != null && it.url == null }
            "Links"  -> allMemys.filter { it.url != null }
            "Food"   -> allMemys.filter { it.tags.any { t -> t.contains("food", true) } }
            else     -> allMemys
        }
    }

    Column(modifier = Modifier
        .fillMaxSize()
        .background(SurfacePage)) {
        // Top bar
        MemyTopBar(
            onSearchClick = onSearchClick,
            avatarUrl     = viewModel.currentUser.avatarUrl,
        )

        // Filter chips
        Row(
            modifier            = Modifier
                .fillMaxWidth()
                .horizontalScroll(rememberScrollState())
                .padding(horizontal = 20.dp, vertical = 8.dp),
            horizontalArrangement = Arrangement.spacedBy(8.dp),
        ) {
            filterLabels.forEach { label ->
                MemyFilterChip(
                    label    = label,
                    selected = label == selectedFilter,
                    onClick  = { selectedFilter = label },
                )
            }
        }

        // Feed
        if (filtered.isEmpty()) {
            Box(modifier = Modifier.fillMaxSize(), contentAlignment = Alignment.Center) {
                EmptyState(
                    icon     = androidx.compose.material.icons.Icons.Filled.AutoAwesome,
                    title    = "Your first memy awaits",
                    subtitle = "Save a place, a note, a link or a photo\n— anything worth remembering. Tap\nthe + to begin.",
                    ctaLabel = "Add a memy",
                    onCta    = { /* open capture */ },
                )
            }
        } else {
            LazyVerticalStaggeredGrid(
                columns             = StaggeredGridCells.Fixed(2),
                modifier            = Modifier.fillMaxSize(),
                contentPadding      = PaddingValues(horizontal = 16.dp, vertical = 8.dp),
                horizontalArrangement = Arrangement.spacedBy(12.dp),
                verticalItemSpacing = 12.dp,
            ) {
                itemsIndexed(filtered, key = { _, m -> m.id }) { index, memy ->
                    val enterAnim = remember { Animatable(0f) }
                    LaunchedEffect(memy.id) {
                        delay((index * 45).toLong())
                        enterAnim.animateTo(1f, tween(220))
                    }

                    // Alternate card heights for the masonry effect
                    val photoHeight = if (index % 3 == 0) 184.dp else 132.dp

                    MemyCard(
                        name        = memy.name,
                        photoUrl    = memy.photoUrl,
                        mood        = memy.mood,
                        place       = memy.location?.label,
                        date        = formatDate(memy.date),
                        tags        = memy.tags,
                        photoHeightDp = photoHeight,
                        onClick     = { onMemyClick(memy.id) },
                        modifier    = Modifier.alpha(enterAnim.value),
                    )
                }
                item(span = StaggeredGridItemSpan.FullLine) {
                    Spacer(Modifier.height(88.dp)) // bottom nav clearance
                }
            }
        }
    }
}

private fun formatDate(date: java.time.LocalDate): String {
    val months = listOf("Jan","Feb","Mar","Apr","May","Jun",
                        "Jul","Aug","Sep","Oct","Nov","Dec")
    return "${date.dayOfMonth} ${months[date.monthValue - 1]}"
}

// import needed
import androidx.compose.material.icons.Icons
import kotlinx.coroutines.delay
