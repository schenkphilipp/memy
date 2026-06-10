package com.memy.app.ui.theme

import androidx.compose.material3.Typography
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.font.Font
import androidx.compose.ui.text.font.FontFamily
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.sp

// ── Font families ────────────────────────────────────────────────────────────
// Add Rubik, Inter, Montserrat .ttf files to res/font/ and reference them here.
// Fallback to system sans-serif during development if fonts are not yet added.
val RubikFamily = FontFamily(
    Font(com.memy.app.R.font.rubik_regular,  FontWeight.Normal),
    Font(com.memy.app.R.font.rubik_medium,   FontWeight.Medium),
    Font(com.memy.app.R.font.rubik_semibold, FontWeight.SemiBold),
    Font(com.memy.app.R.font.rubik_bold,     FontWeight.Bold),
)

val InterFamily = FontFamily(
    Font(com.memy.app.R.font.inter_regular,  FontWeight.Normal),
    Font(com.memy.app.R.font.inter_medium,   FontWeight.Medium),
    Font(com.memy.app.R.font.inter_semibold, FontWeight.SemiBold),
    Font(com.memy.app.R.font.inter_bold,     FontWeight.Bold),
)

val MontserratFamily = FontFamily(
    Font(com.memy.app.R.font.montserrat_regular,  FontWeight.Normal),
    Font(com.memy.app.R.font.montserrat_medium,   FontWeight.Medium),
    Font(com.memy.app.R.font.montserrat_semibold, FontWeight.SemiBold),
)

// ── Type scale ───────────────────────────────────────────────────────────────
// Maps Memy scale → Material 3 slots
val MemyTypography = Typography(
    // Display 40 / Rubik 700 — hero headlines
    displayLarge = TextStyle(
        fontFamily = RubikFamily,
        fontWeight = FontWeight.Bold,
        fontSize = 40.sp,
        lineHeight = (40 * 1.1).sp,
        letterSpacing = (-0.02).sp
    ),
    // Title 38 / Rubik 500 — welcome screen
    displayMedium = TextStyle(
        fontFamily = RubikFamily,
        fontWeight = FontWeight.Medium,
        fontSize = 38.sp,
        lineHeight = (38 * 1.1).sp,
        letterSpacing = (-0.01).sp
    ),
    // H1 28 / Rubik 700
    headlineLarge = TextStyle(
        fontFamily = RubikFamily,
        fontWeight = FontWeight.Bold,
        fontSize = 28.sp,
        lineHeight = (28 * 1.25).sp,
        letterSpacing = (-0.01).sp
    ),
    // H2 22 / Rubik 700
    headlineMedium = TextStyle(
        fontFamily = RubikFamily,
        fontWeight = FontWeight.Bold,
        fontSize = 22.sp,
        lineHeight = (22 * 1.25).sp,
    ),
    // H3 18 / Rubik 600
    headlineSmall = TextStyle(
        fontFamily = RubikFamily,
        fontWeight = FontWeight.SemiBold,
        fontSize = 18.sp,
        lineHeight = (18 * 1.4).sp,
    ),
    // Title / label 17 / Inter 600
    titleLarge = TextStyle(
        fontFamily = InterFamily,
        fontWeight = FontWeight.SemiBold,
        fontSize = 17.sp,
        lineHeight = (17 * 1.4).sp,
    ),
    // Body 16 / Inter 400
    bodyLarge = TextStyle(
        fontFamily = InterFamily,
        fontWeight = FontWeight.Normal,
        fontSize = 16.sp,
        lineHeight = (16 * 1.6).sp,
    ),
    // Body-sm 15 / Inter 400
    bodyMedium = TextStyle(
        fontFamily = InterFamily,
        fontWeight = FontWeight.Normal,
        fontSize = 15.sp,
        lineHeight = (15 * 1.6).sp,
    ),
    // Field 14 / Rubik 400
    bodySmall = TextStyle(
        fontFamily = RubikFamily,
        fontWeight = FontWeight.Normal,
        fontSize = 14.sp,
        lineHeight = (14 * 1.4).sp,
        letterSpacing = 0.2.sp
    ),
    // Label / button / Inter 700
    labelLarge = TextStyle(
        fontFamily = InterFamily,
        fontWeight = FontWeight.Bold,
        fontSize = 15.sp,
        lineHeight = (15 * 1.25).sp,
    ),
    // Caption 12 / Montserrat 500 — eyebrow
    labelSmall = TextStyle(
        fontFamily = MontserratFamily,
        fontWeight = FontWeight.Medium,
        fontSize = 12.sp,
        lineHeight = (12 * 1.4).sp,
        letterSpacing = 0.4.sp
    ),
)
