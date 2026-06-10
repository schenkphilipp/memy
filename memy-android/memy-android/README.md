# Memy — Android App (Kotlin + Jetpack Compose)

A pixel-faithful implementation of the Memy design system in Kotlin + Jetpack Compose +
Material 3, directly from the `02_android_app.md` spec and `screens_pdf.pdf` reference.

---

## Quick start

```bash
# 1. Open this folder in Android Studio (Electric Eel or newer)
# 2. Add your fonts to app/src/main/res/font/ (see Fonts section)
# 3. Add your Google Maps API key to AndroidManifest.xml
# 4. Run on a device / emulator (minSdk 26)
```

---

## Project structure

```
app/src/main/java/com/memy/app/
├── MainActivity.kt              # Entry point
├── MemyApp.kt                   # NavHost + BottomNavBar scaffold
├── AppViewModel.kt              # Shared app-level state
├── data/
│   ├── model/Models.kt          # User, Memy, Collection, MemyLocation
│   └── repository/
│       ├── MemyRepository.kt    # In-memory repo (swap for Room + Retrofit)
│       └── SampleData.kt        # Seed data matching the PDF screens
├── ui/
│   ├── theme/
│   │   ├── Color.kt             # All design tokens from 01_design_tokens.md
│   │   ├── Type.kt              # Rubik + Inter + Montserrat scale
│   │   └── Theme.kt             # Material 3 colorScheme + shapes
│   ├── components/
│   │   ├── Components.kt        # MemyButton, CoralFAB, UnderlineTextField,
│   │   │                        # TagChip, FilterChip, Avatar, WaveHeader,
│   │   │                        # MemyCard, CollectionCard, EmptyState, TopBar
│   │   └── FlowRow.kt           # Simple wrapping flow layout
│   ├── navigation/Navigation.kt  # Screen routes + bottom nav items
│   └── screens/
│       ├── AuthScreens.kt       # Splash, Welcome, Login, SignUp, AuthSuccess
│       ├── HomeScreen.kt        # Masonry feed + filter chips
│       ├── SearchScreen.kt      # Live search, recent, no-results states
│       ├── CaptureScreen.kt     # Full memy capture form
│       ├── DetailScreen.kt      # Hero photo, meta, map, tags
│       ├── CollectionsScreen.kt # Grid + AlbumScreen (single collection)
│       ├── MapScreen.kt         # Pins, bottom sheet, empty state
│       └── ProfileScreen.kt     # Coral header, stats, settings
```

---

## Fonts (required)

Download and add to `app/src/main/res/font/`:

| File name              | Font          | Weight |
|------------------------|---------------|--------|
| `rubik_regular.ttf`    | Rubik         | 400    |
| `rubik_medium.ttf`     | Rubik         | 500    |
| `rubik_semibold.ttf`   | Rubik         | 600    |
| `rubik_bold.ttf`       | Rubik         | 700    |
| `inter_regular.ttf`    | Inter         | 400    |
| `inter_medium.ttf`     | Inter         | 500    |
| `inter_semibold.ttf`   | Inter         | 600    |
| `inter_bold.ttf`       | Inter         | 700    |
| `montserrat_regular.ttf`  | Montserrat | 400    |
| `montserrat_medium.ttf`   | Montserrat | 500    |
| `montserrat_semibold.ttf` | Montserrat | 600    |

Google Fonts download:
- Rubik: https://fonts.google.com/specimen/Rubik
- Inter: https://fonts.google.com/specimen/Inter
- Montserrat: https://fonts.google.com/specimen/Montserrat

---

## Google Maps API key

1. Create a key at https://console.cloud.google.com
2. Enable "Maps SDK for Android"
3. Replace `YOUR_MAPS_API_KEY_HERE` in `AndroidManifest.xml`

For production the `MapScreen` uses a Canvas placeholder; swap it for
`GoogleMap` composable from `maps-compose` using the `LatLng` coordinates
already on each `Memy.location`.

---

## Design tokens

All values are sourced verbatim from `01_design_tokens.md`:

| Token type | File |
|------------|------|
| Colors + semantic aliases | `ui/theme/Color.kt` |
| Typography scale | `ui/theme/Type.kt` |
| Shapes (radii) | `ui/theme/Theme.kt` → `MemyShapes` |
| Shadows | Applied inline via `.shadow()` with the exact rgba values |
| Motion | `tween(140/220/360)` with `FastOutSlowInEasing` per spec |

---

## Screens implemented

| Screen | Status | Notes |
|--------|--------|-------|
| Splash | ✅ | Auto-advances at 1.6 s |
| Welcome | ✅ | WaveHeader hero + arrow FAB |
| Login | ✅ | Underline fields, checkbox, forgot |
| Sign-up | ✅ | 4 fields, create account CTA |
| Auth success | ✅ | Auto-advances to Home |
| Home feed | ✅ | 2-col masonry, filter chips, staggered entrance |
| Home — empty | ✅ | `auto_awesome` empty state |
| Search — typing | ✅ | Live filter, recent, tag browse |
| Search — results | ✅ | Thumbnail list with chevron |
| Search — no results | ✅ | Friendly empty state |
| Capture | ✅ | Photo, all fields, mood picker, map placeholder |
| Detail | ✅ | Hero photo, glass buttons, meta, link card, mini-map |
| Collections | ✅ | 2×2 mosaic grid, staggered entrance |
| Collections — empty | ✅ | `grid_view` empty state |
| Album | ✅ | Coral header, masonry, empty state |
| Map | ✅ | Canvas map, pins, bottom sheet |
| Map — empty | ✅ | `wrong_location` empty state |
| Profile | ✅ | Coral header, avatar, stats, settings rows |

---

## Production checklist

- [ ] Replace `MemyRepository` with Room database + Retrofit/Ktor API calls
- [ ] Add Supabase / Firebase Auth for real login
- [ ] Swap Canvas map with `GoogleMap` composable + real `Marker` composable pins
- [ ] Wire photo picker (Activity result API → Coil upload to Storage)
- [ ] Add Room offline cache and optimistic capture
- [ ] Replace Unsplash placeholder URLs with real CDN photo URLs
- [ ] Add Crashlytics + analytics events (see `05_build_and_deploy.md`)
- [ ] Run accessibility audit (tap targets ≥ 44px, contrast — both already respected)
- [ ] Sign the release AAB and upload to Play internal track

---

## Color reference (key tokens)

```
Brand (primary):     #FF8383  — coral fill
BrandStrong:         #EF6D6D  — hover/pressed
BrandWash:           #FFF5F5  — tinted backgrounds
OnBrand:             #FFFCFC  — text on coral
SurfacePage:         #FFFCFC  — warm off-white
TextStrong:          #212121
TextBody:            #616161
TextMuted:           #757575
```
