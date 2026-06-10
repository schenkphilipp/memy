package com.memy.app.ui.theme

import androidx.compose.ui.graphics.Color

// ── Coral (primary brand) ───────────────────────────────────────────────────
val Coral50   = Color(0xFFFFF5F5)
val Coral100  = Color(0xFFFFE3E3)
val Coral200  = Color(0xFFFFCCCC)   // tint / glow
val Coral300  = Color(0xFFFF9D95)
val Coral400  = Color(0xFFFF8383)   // PRIMARY
val Coral500  = Color(0xFFF2958D)
val Coral600  = Color(0xFFEF6D6D)   // hover / pressed
val Coral700  = Color(0xFFD65656)
val AccentRed = Color(0xFFFF4B5E)   // emphasis / alert / danger

// ── Warm neutrals (greige) ──────────────────────────────────────────────────
val White        = Color(0xFFFFFFFF)
val Neutral25    = Color(0xFFFFFCFC)   // warm off-white surface
val Neutral50    = Color(0xFFF7F5F4)
val Neutral100   = Color(0xFFF5F5F5)
val Neutral200   = Color(0xFFEEEEEE)
val Neutral300   = Color(0xFFE0E0E0)
val Neutral350   = Color(0xFFDADADA)
val Neutral400   = Color(0xFFBDBDBD)
val Neutral500   = Color(0xFF9E9E9E)
val Neutral600   = Color(0xFF757575)
val Neutral700   = Color(0xFF616161)
val Neutral800   = Color(0xFF424242)
val Neutral900   = Color(0xFF212121)   // ink
val Neutral950   = Color(0xFF141414)

// ── Support ─────────────────────────────────────────────────────────────────
val Success = Color(0xFF4CAF82)
val Warning = Color(0xFFF2A65A)
val Info    = Color(0xFF6AA9D8)
val Danger  = AccentRed

// ── Semantic aliases ─────────────────────────────────────────────────────────
val Brand          = Coral400
val BrandStrong    = Coral600
val BrandTint      = Coral200
val BrandWash      = Coral50
val OnBrand        = Neutral25

val SurfacePage    = Neutral25
val SurfaceCard    = White
val SurfaceSunken  = Neutral100
val SurfaceMuted   = Neutral200
val SurfaceInverse = Neutral900

val TextStrong     = Neutral900
val TextHeading    = Neutral800
val TextBody       = Neutral700
val TextMuted      = Neutral600
val TextSubtle     = Neutral400
val TextLink       = Coral400

val BorderSubtle   = Neutral300
val BorderDefault  = Neutral350
val FieldLine      = Neutral400
val FieldLineActive = Coral400
