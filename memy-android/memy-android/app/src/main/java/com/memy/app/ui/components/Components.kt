package com.memy.app.ui.components

import androidx.compose.animation.*
import androidx.compose.animation.core.*
import androidx.compose.foundation.*
import androidx.compose.foundation.interaction.MutableInteractionSource
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.*
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Add
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.*
import androidx.compose.ui.draw.*
import androidx.compose.ui.geometry.Offset
import androidx.compose.ui.graphics.*
import androidx.compose.ui.graphics.drawscope.DrawScope
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.*
import coil.compose.AsyncImage
import com.memy.app.ui.theme.*

// ── MemyButton ────────────────────────────────────────────────────────────────
@Composable
fun MemyButton(
    text: String,
    onClick: () -> Unit,
    modifier: Modifier = Modifier,
    variant: ButtonVariant = ButtonVariant.Primary,
    fullWidth: Boolean = true,
    enabled: Boolean = true,
) {
    val bgColor = when (variant) {
        ButtonVariant.Primary   -> Brand
        ButtonVariant.Secondary -> SurfaceCard
        ButtonVariant.Ghost     -> Color.Transparent
    }
    val contentColor = when (variant) {
        ButtonVariant.Primary   -> OnBrand
        ButtonVariant.Secondary -> Brand
        ButtonVariant.Ghost     -> Brand
    }
    val border = if (variant == ButtonVariant.Secondary)
        BorderStroke(1.5.dp, Brand) else null

    val interactionSource = remember { MutableInteractionSource() }
    var pressed by remember { mutableStateOf(false) }
    val scale by animateFloatAsState(
        targetValue = if (pressed) 0.97f else 1f,
        animationSpec = tween(140, easing = FastOutSlowInEasing),
        label = "btnScale"
    )

    Button(
        onClick      = onClick,
        enabled      = enabled,
        modifier     = modifier
            .then(if (fullWidth) Modifier.fillMaxWidth() else Modifier)
            .height(49.dp)
            .scale(scale),
        shape        = RoundedCornerShape(12.dp),
        colors       = ButtonDefaults.buttonColors(
            containerColor = bgColor,
            contentColor   = contentColor,
        ),
        border       = border,
        elevation    = if (variant == ButtonVariant.Primary)
            ButtonDefaults.buttonElevation(
                defaultElevation = 0.dp,
                pressedElevation = 0.dp,
            ) else ButtonDefaults.buttonElevation(0.dp),
        contentPadding = PaddingValues(horizontal = 24.dp),
    ) {
        Text(
            text       = text,
            style      = MaterialTheme.typography.labelLarge,
            fontWeight = FontWeight.Bold,
        )
    }
}

enum class ButtonVariant { Primary, Secondary, Ghost }

// ── CoralFAB ─────────────────────────────────────────────────────────────────
@Composable
fun CoralFAB(
    onClick: () -> Unit,
    modifier: Modifier = Modifier,
) {
    var pressed by remember { mutableStateOf(false) }
    val scale by animateFloatAsState(
        targetValue = if (pressed) 0.92f else 1f,
        animationSpec = tween(140),
        label = "fabScale"
    )
    Box(
        contentAlignment = Alignment.Center,
        modifier = modifier
            .size(60.dp)
            .scale(scale)
            .shadow(
                elevation    = 14.dp,
                shape        = RoundedCornerShape(20.dp),
                ambientColor = Coral400.copy(alpha = 0.18f),
                spotColor    = Coral400.copy(alpha = 0.28f),
            )
            .background(Brand, RoundedCornerShape(20.dp))
            .clickable(
                interactionSource = remember { MutableInteractionSource() },
                indication        = null,
                onClick           = onClick,
            )
    ) {
        Icon(
            imageVector        = Icons.Filled.Add,
            contentDescription = "Add a memy",
            tint               = OnBrand,
            modifier           = Modifier.size(28.dp),
        )
    }
}

// ── UnderlineTextField ────────────────────────────────────────────────────────
@Composable
fun UnderlineTextField(
    value: String,
    onValueChange: (String) -> Unit,
    label: String,
    modifier: Modifier = Modifier,
    leadingIcon: (@Composable () -> Unit)? = null,
    trailingIcon: (@Composable () -> Unit)? = null,
    placeholder: String = "",
    singleLine: Boolean = true,
    visualTransformation: androidx.compose.ui.text.input.VisualTransformation =
        androidx.compose.ui.text.input.VisualTransformation.None,
) {
    OutlinedTextField(
        value            = value,
        onValueChange    = onValueChange,
        label            = { Text(label, style = MaterialTheme.typography.bodySmall) },
        placeholder      = { Text(placeholder, style = MaterialTheme.typography.bodySmall,
                                  color = TextSubtle) },
        leadingIcon      = leadingIcon,
        trailingIcon     = trailingIcon,
        singleLine       = singleLine,
        visualTransformation = visualTransformation,
        modifier         = modifier.fillMaxWidth(),
        textStyle        = MaterialTheme.typography.bodySmall.copy(color = TextStrong),
        colors           = OutlinedTextFieldDefaults.colors(
            focusedBorderColor    = FieldLineActive,
            unfocusedBorderColor  = FieldLine,
            focusedLabelColor     = FieldLineActive,
            unfocusedLabelColor   = TextMuted,
            focusedLeadingIconColor   = FieldLineActive,
            unfocusedLeadingIconColor = TextMuted,
            cursorColor           = Brand,
        ),
        shape = RoundedCornerShape(bottomStart = 0.dp, bottomEnd = 0.dp,
                                   topStart = 4.dp, topEnd = 4.dp),
    )
}

// ── TagChip ───────────────────────────────────────────────────────────────────
@Composable
fun TagChip(
    tag: String,
    modifier: Modifier = Modifier,
    onClick: (() -> Unit)? = null,
) {
    val m = if (onClick != null) modifier.clickable(onClick = onClick) else modifier
    Box(
        modifier = m
            .background(BrandWash, RoundedCornerShape(999.dp))
            .padding(horizontal = 8.dp, vertical = 3.dp)
    ) {
        Text(
            text  = "#$tag",
            style = MaterialTheme.typography.labelSmall.copy(
                fontFamily  = InterFamily,
                fontWeight  = FontWeight.SemiBold,
                fontSize    = 11.5.sp,
                color       = BrandStrong,
            ),
        )
    }
}

// ── FilterChip ────────────────────────────────────────────────────────────────
@Composable
fun MemyFilterChip(
    label: String,
    selected: Boolean,
    onClick: () -> Unit,
) {
    val bgColor      = if (selected) BrandWash   else Color.Transparent
    val borderColor  = if (selected) Brand       else BorderSubtle
    val textColor    = if (selected) BrandStrong else TextMuted

    Box(
        modifier = Modifier
            .height(32.dp)
            .border(1.5.dp, borderColor, RoundedCornerShape(999.dp))
            .background(bgColor, RoundedCornerShape(999.dp))
            .clickable(onClick = onClick)
            .padding(horizontal = 16.dp),
        contentAlignment = Alignment.Center,
    ) {
        Text(
            text  = label,
            style = MaterialTheme.typography.bodySmall.copy(
                fontFamily = InterFamily,
                fontWeight = if (selected) FontWeight.SemiBold else FontWeight.Normal,
                color      = textColor,
                fontSize   = 13.sp,
            ),
        )
    }
}

// ── Avatar ────────────────────────────────────────────────────────────────────
@Composable
fun Avatar(
    url: String?,
    size: Dp = 34.dp,
    modifier: Modifier = Modifier,
) {
    val shape = CircleShape
    if (url != null) {
        AsyncImage(
            model             = url,
            contentDescription = null,
            contentScale      = ContentScale.Crop,
            modifier          = modifier
                .size(size)
                .clip(shape)
                .background(SurfaceSunken),
        )
    } else {
        Box(
            modifier = modifier
                .size(size)
                .background(BrandWash, shape),
            contentAlignment = Alignment.Center,
        ) {
            Text("?", color = Brand, style = MaterialTheme.typography.bodySmall)
        }
    }
}

// ── WaveHeader ────────────────────────────────────────────────────────────────
// Coral hero block with a soft wavy bottom edge.
@Composable
fun WaveHeader(
    modifier: Modifier = Modifier,
    heightDp: Dp = 260.dp,
    content: @Composable BoxScope.() -> Unit = {},
) {
    Box(
        modifier = modifier
            .fillMaxWidth()
            .height(heightDp)
            .drawBehind {
                drawWaveBackground(this)
            },
        content = content,
    )
}

private fun drawWaveBackground(scope: DrawScope) {
    with(scope) {
        // Solid coral fill
        drawRect(color = Brand)

        // Wave cutout at the bottom — a simple bezier wave
        val w = size.width
        val h = size.height
        val waveH = 36.dp.toPx()

        val path = Path().apply {
            moveTo(0f, h - waveH)
            cubicTo(
                w * 0.25f, h,
                w * 0.75f, h - waveH * 2,
                w, h - waveH
            )
            lineTo(w, h)
            lineTo(0f, h)
            close()
        }
        // Paint the wave in the page background color to create the cutout illusion
        drawPath(path, color = SurfacePage)
    }
}

// ── MemyCard (feed card) ──────────────────────────────────────────────────────
@Composable
fun MemyCard(
    name: String,
    photoUrl: String?,
    mood: String?,
    place: String?,
    date: String,
    tags: List<String>,
    onClick: () -> Unit,
    modifier: Modifier = Modifier,
    photoHeightDp: Dp = 160.dp,
) {
    Card(
        onClick   = onClick,
        modifier  = modifier.fillMaxWidth(),
        shape     = RoundedCornerShape(20.dp),
        colors    = CardDefaults.cardColors(containerColor = SurfaceCard),
        elevation = CardDefaults.cardElevation(defaultElevation = 2.dp),
    ) {
        Column {
            // Photo
            Box(modifier = Modifier
                .fillMaxWidth()
                .height(photoHeightDp)) {
                if (photoUrl != null) {
                    AsyncImage(
                        model              = photoUrl,
                        contentDescription = name,
                        contentScale       = ContentScale.Crop,
                        modifier           = Modifier.fillMaxSize(),
                    )
                } else {
                    Box(
                        Modifier
                            .fillMaxSize()
                            .background(SurfaceSunken),
                    )
                }
                // Mood chip
                if (mood != null) {
                    Box(
                        modifier = Modifier
                            .align(Alignment.TopEnd)
                            .padding(8.dp)
                            .size(28.dp)
                            .background(
                                SurfaceCard.copy(alpha = 0.85f),
                                CircleShape
                            ),
                        contentAlignment = Alignment.Center,
                    ) {
                        Text(mood, fontSize = 14.sp)
                    }
                }
            }
            // Content
            Column(modifier = Modifier.padding(10.dp)) {
                Text(
                    text  = name,
                    style = MaterialTheme.typography.bodySmall.copy(
                        fontFamily = InterFamily,
                        fontWeight = FontWeight.SemiBold,
                        color      = TextStrong,
                    ),
                    maxLines = 2,
                )
                Spacer(Modifier.height(3.dp))
                Text(
                    text  = if (place != null) "$place · $date" else date,
                    style = MaterialTheme.typography.labelSmall.copy(
                        color    = TextMuted,
                        fontSize = 11.5.sp,
                    ),
                )
                if (tags.isNotEmpty()) {
                    Spacer(Modifier.height(6.dp))
                    Row(horizontalArrangement = Arrangement.spacedBy(4.dp)) {
                        tags.take(2).forEach { TagChip(it) }
                    }
                }
            }
        }
    }
}

// ── CollectionCard ────────────────────────────────────────────────────────────
@Composable
fun CollectionCard(
    name: String,
    memyCount: Int,
    coverUrls: List<String>,
    onClick: () -> Unit,
    modifier: Modifier = Modifier,
) {
    Card(
        onClick   = onClick,
        modifier  = modifier.fillMaxWidth(),
        shape     = RoundedCornerShape(20.dp),
        colors    = CardDefaults.cardColors(containerColor = SurfaceCard),
        elevation = CardDefaults.cardElevation(defaultElevation = 2.dp),
    ) {
        Column {
            // 2x2 mosaic cover
            Box(
                modifier = Modifier
                    .fillMaxWidth()
                    .height(110.dp)
                    .clip(RoundedCornerShape(topStart = 20.dp, topEnd = 20.dp))
            ) {
                Row(modifier = Modifier.fillMaxSize()) {
                    val left  = coverUrls.getOrNull(0)
                    val right = coverUrls.getOrNull(1)
                    CoverPhoto(left,  Modifier.weight(1f).fillMaxHeight())
                    Spacer(Modifier.width(1.dp))
                    Column(modifier = Modifier.weight(1f)) {
                        val topR    = coverUrls.getOrNull(2)
                        val bottomR = coverUrls.getOrNull(3)
                        CoverPhoto(topR,    Modifier.weight(1f).fillMaxWidth())
                        Spacer(Modifier.height(1.dp))
                        CoverPhoto(bottomR, Modifier.weight(1f).fillMaxWidth())
                    }
                }
                // Name overlay
                Box(
                    modifier = Modifier
                        .align(Alignment.BottomStart)
                        .background(
                            Brush.verticalGradient(
                                listOf(Color.Transparent, Color.Black.copy(alpha = 0.5f))
                            )
                        )
                        .fillMaxWidth()
                        .padding(horizontal = 10.dp, vertical = 8.dp)
                ) {
                    Column {
                        Text(
                            name,
                            style      = MaterialTheme.typography.bodySmall.copy(
                                fontFamily = InterFamily,
                                fontWeight = FontWeight.SemiBold,
                                color      = White,
                                fontSize   = 15.sp,
                            )
                        )
                        Text(
                            "$memyCount memys",
                            style = MaterialTheme.typography.labelSmall.copy(
                                color    = White.copy(alpha = 0.8f),
                                fontSize = 11.sp,
                            )
                        )
                    }
                }
            }
        }
    }
}

@Composable
private fun CoverPhoto(url: String?, modifier: Modifier) {
    if (url != null) {
        AsyncImage(
            model              = url,
            contentDescription = null,
            contentScale       = ContentScale.Crop,
            modifier           = modifier.background(SurfaceSunken),
        )
    } else {
        Box(modifier = modifier.background(SurfaceSunken))
    }
}

// ── EmptyState ────────────────────────────────────────────────────────────────
@Composable
fun EmptyState(
    icon: androidx.compose.ui.graphics.vector.ImageVector,
    title: String,
    subtitle: String,
    ctaLabel: String,
    onCta: () -> Unit,
    modifier: Modifier = Modifier,
) {
    Column(
        modifier              = modifier
            .fillMaxWidth()
            .padding(horizontal = 40.dp),
        horizontalAlignment   = Alignment.CenterHorizontally,
        verticalArrangement   = Arrangement.Center,
    ) {
        Box(
            modifier         = Modifier
                .size(92.dp)
                .background(BrandWash, CircleShape),
            contentAlignment = Alignment.Center,
        ) {
            Icon(icon, contentDescription = null, tint = Brand, modifier = Modifier.size(40.dp))
        }
        Spacer(Modifier.height(24.dp))
        Text(
            title,
            style      = MaterialTheme.typography.headlineSmall.copy(color = TextStrong),
            textAlign  = androidx.compose.ui.text.style.TextAlign.Center,
        )
        Spacer(Modifier.height(10.dp))
        Text(
            subtitle,
            style     = MaterialTheme.typography.bodyMedium.copy(color = TextMuted),
            textAlign = androidx.compose.ui.text.style.TextAlign.Center,
        )
        Spacer(Modifier.height(28.dp))
        MemyButton(ctaLabel, onClick = onCta, fullWidth = false,
            modifier = Modifier.widthIn(min = 180.dp))
    }
}

// ── MemyTopBar ─────────────────────────────────────────────────────────────────
@Composable
fun MemyTopBar(
    onSearchClick: () -> Unit,
    avatarUrl: String?,
) {
    Row(
        modifier            = Modifier
            .fillMaxWidth()
            .height(56.dp)
            .padding(horizontal = 20.dp),
        verticalAlignment   = Alignment.CenterVertically,
    ) {
        // Logo area
        Box(
            modifier = Modifier
                .size(34.dp)
                .background(Brand, RoundedCornerShape(10.dp)),
            contentAlignment = Alignment.Center,
        ) {
            Text("m", style = MaterialTheme.typography.bodyMedium.copy(
                color = OnBrand, fontWeight = FontWeight.Bold, fontSize = 18.sp))
        }
        Spacer(Modifier.width(8.dp))
        Text(
            "memy",
            style = MaterialTheme.typography.headlineMedium.copy(
                fontFamily = RubikFamily,
                fontWeight = FontWeight.Bold,
                color      = TextStrong,
                fontSize   = 20.sp,
            )
        )
        // Coral dot
        Spacer(Modifier.width(2.dp))
        Box(Modifier
            .size(6.dp)
            .background(Brand, CircleShape)
            .align(Alignment.Bottom)
            .padding(bottom = 4.dp)
        )
        Spacer(Modifier.weight(1f))
        IconButton(onClick = onSearchClick) {
            Icon(
                imageVector        = Icons.Outlined.Search,
                contentDescription = "Search",
                tint               = TextBody,
            )
        }
        Spacer(Modifier.width(4.dp))
        Avatar(url = avatarUrl, size = 34.dp)
    }
}

// Pull in needed icons
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.outlined.Search
