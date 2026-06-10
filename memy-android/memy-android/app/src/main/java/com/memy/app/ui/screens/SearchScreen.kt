package com.memy.app.ui.screens

import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.*
import androidx.compose.material.icons.outlined.*
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.*
import androidx.compose.ui.draw.clip
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.*
import coil.compose.AsyncImage
import com.memy.app.AppViewModel
import com.memy.app.data.model.Memy
import com.memy.app.ui.components.TagChip
import com.memy.app.ui.theme.*

@Composable
fun SearchScreen(
    viewModel: AppViewModel,
    onBack: () -> Unit,
    onMemyClick: (String) -> Unit,
) {
    val query by viewModel.searchQuery.collectAsState()
    val results by viewModel.searchResults.collectAsState()

    Column(
        modifier = Modifier
            .fillMaxSize()
            .background(SurfacePage),
    ) {
        // Search bar row
        Row(
            modifier          = Modifier
                .fillMaxWidth()
                .padding(horizontal = 16.dp, vertical = 12.dp),
            verticalAlignment = Alignment.CenterVertically,
        ) {
            IconButton(onClick = {
                viewModel.onSearchQueryChange("")
                onBack()
            }) {
                Icon(Icons.Filled.ArrowBack, "Back", tint = TextBody)
            }
            Spacer(Modifier.width(4.dp))
            Box(
                modifier = Modifier
                    .weight(1f)
                    .height(44.dp)
                    .background(SurfaceSunken, RoundedCornerShape(999.dp))
                    .padding(horizontal = 14.dp),
                contentAlignment = Alignment.CenterStart,
            ) {
                Row(verticalAlignment = Alignment.CenterVertically) {
                    Icon(Icons.Outlined.Search, null, tint = TextMuted, modifier = Modifier.size(18.dp))
                    Spacer(Modifier.width(8.dp))
                    BasicTextField(
                        value         = query,
                        onValueChange = viewModel::onSearchQueryChange,
                        textStyle     = MaterialTheme.typography.bodySmall.copy(color = TextStrong),
                        modifier      = Modifier.weight(1f),
                        singleLine    = true,
                        decorationBox = { inner ->
                            if (query.isEmpty()) Text("Search memys…",
                                style = MaterialTheme.typography.bodySmall.copy(color = TextSubtle))
                            inner()
                        }
                    )
                    if (query.isNotEmpty()) {
                        Spacer(Modifier.width(4.dp))
                        IconButton(
                            onClick  = { viewModel.onSearchQueryChange("") },
                            modifier = Modifier.size(20.dp),
                        ) {
                            Icon(Icons.Filled.Close, "Clear", tint = TextMuted, modifier = Modifier.size(16.dp))
                        }
                    }
                }
            }
        }

        when {
            query.isBlank() -> EmptyQueryState()
            results.isEmpty() -> NoResultsState(query)
            else -> ResultsList(results, onMemyClick)
        }
    }
}

@Composable
private fun EmptyQueryState() {
    Column(
        modifier = Modifier
            .fillMaxWidth()
            .padding(20.dp),
    ) {
        Text("Recent", style = MaterialTheme.typography.bodySmall.copy(
            color = TextMuted, fontWeight = FontWeight.SemiBold))
        Spacer(Modifier.height(12.dp))
        listOf("travel", "portugal", "food", "cozy").forEach { term ->
            Row(
                modifier          = Modifier
                    .fillMaxWidth()
                    .padding(vertical = 6.dp),
                verticalAlignment = Alignment.CenterVertically,
            ) {
                Icon(Icons.Outlined.History, null, tint = TextMuted, modifier = Modifier.size(18.dp))
                Spacer(Modifier.width(10.dp))
                Text(term, style = MaterialTheme.typography.bodyMedium.copy(color = TextBody))
            }
        }
        Spacer(Modifier.height(20.dp))
        Text("Browse by tag", style = MaterialTheme.typography.bodySmall.copy(
            color = TextMuted, fontWeight = FontWeight.SemiBold))
        Spacer(Modifier.height(12.dp))
        com.memy.app.ui.components.FlowRow(
            horizontalGap = 8.dp,
            verticalGap   = 8.dp,
        ) {
            listOf("travel","food","reading","cozy","hiking","portugal").forEach { tag ->
                TagChip(tag)
            }
        }
    }
}

@Composable
private fun NoResultsState(query: String) {
    Column(
        modifier              = Modifier
            .fillMaxSize()
            .padding(40.dp),
        horizontalAlignment   = Alignment.CenterHorizontally,
        verticalArrangement   = Arrangement.Center,
    ) {
        Box(
            modifier = Modifier
                .size(80.dp)
                .background(com.memy.app.ui.theme.BrandWash, androidx.compose.foundation.shape.CircleShape),
            contentAlignment = Alignment.Center,
        ) {
            Icon(Icons.Filled.SearchOff, null,
                tint     = Brand,
                modifier = Modifier.size(36.dp))
        }
        Spacer(Modifier.height(20.dp))
        Text(
            "No memys for "$query"",
            style     = MaterialTheme.typography.headlineSmall.copy(color = TextStrong),
            textAlign = androidx.compose.ui.text.style.TextAlign.Center,
        )
        Spacer(Modifier.height(8.dp))
        Text(
            "Try a different word, a place, or one of your tags.",
            style     = MaterialTheme.typography.bodyMedium.copy(color = TextMuted),
            textAlign = androidx.compose.ui.text.style.TextAlign.Center,
        )
    }
}

@Composable
private fun ResultsList(results: List<Memy>, onMemyClick: (String) -> Unit) {
    Text(
        "${results.size} RESULTS",
        style    = MaterialTheme.typography.labelSmall.copy(
            color        = TextMuted,
            fontWeight   = FontWeight.SemiBold,
            letterSpacing = 1.sp,
        ),
        modifier = Modifier.padding(horizontal = 20.dp, vertical = 4.dp),
    )
    LazyColumn {
        items(results, key = { it.id }) { memy ->
            Row(
                modifier          = Modifier
                    .fillMaxWidth()
                    .clickable { onMemyClick(memy.id) }
                    .padding(horizontal = 20.dp, vertical = 10.dp),
                verticalAlignment = Alignment.CenterVertically,
            ) {
                // Thumbnail
                if (memy.photoUrl != null) {
                    AsyncImage(
                        model              = memy.photoUrl,
                        contentDescription = memy.name,
                        contentScale       = ContentScale.Crop,
                        modifier           = Modifier
                            .size(54.dp)
                            .clip(RoundedCornerShape(12.dp))
                            .background(SurfaceSunken),
                    )
                } else {
                    Box(Modifier.size(54.dp).clip(RoundedCornerShape(12.dp)).background(SurfaceSunken))
                }
                Spacer(Modifier.width(14.dp))
                Column(modifier = Modifier.weight(1f)) {
                    Text(memy.name,
                        style = MaterialTheme.typography.bodyMedium.copy(
                            fontWeight = FontWeight.SemiBold, color = TextStrong))
                    Spacer(Modifier.height(2.dp))
                    val sub = buildString {
                        if (memy.location != null) append("${memy.location.label} · ")
                        append(formatDate(memy.date))
                    }
                    Text(sub, style = MaterialTheme.typography.labelSmall.copy(color = TextMuted))
                }
                Icon(Icons.Filled.ChevronRight, null, tint = TextSubtle)
            }
            Divider(color = BorderSubtle, thickness = 0.5.dp,
                modifier = Modifier.padding(horizontal = 20.dp))
        }
        item { Spacer(Modifier.height(88.dp)) }
    }
}

// Needed imports
import androidx.compose.foundation.text.BasicTextField
import androidx.compose.material.icons.outlined.History
