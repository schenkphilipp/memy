# 02 · Android App — screen spec

**Stack (recommended):** Kotlin · Jetpack Compose · Material 3 with the coral accent.
**Fidelity:** high. **Reference:** (in the source project) `ui_kits/memy-android/` (open `index.html` to interact).
**Device canvas:** 412 × 892 (Compose handles real sizing; values below are relative).

## Material 3 theming
Set `colorScheme.primary = #FF8383`, `onPrimary = #FFFCFC`, `surface = #FFFCFC`,
`surfaceVariant = #F5F5F5`, `onSurface = #212121`. Shapes: small 8 / medium 12 / large 20.
Type: map Compose `Typography` to Rubik (display/headline/title) + Inter (body/label).

## Navigation (chosen: "Nav A")
A bottom **NavigationBar** with 4 destinations and a **center-docked coral FAB** for capture:
`Home · Collections · [ + FAB ] · Map · Profile`
- Active item: coral icon (filled) + coral-wash pill behind it + `text-strong` label.
- Inactive: `text-muted` icon + label.
- FAB: 60×60, radius 20, coral fill, `shadow-coral`, raised 34px above the bar.

---

## Screens

### 1. Auth (Splash → Welcome → Login → Sign-up → Success)
High-fidelity reference: (in the source project) `ui_kits/memy-app/index.html`.
- **Splash**: centered rounded app icon (104px, radius 24, `shadow-coral`) + "memy" wordmark
  (Rubik 700) with a coral dot + tagline "your memories, everywhere". Auto-advances ~1.6s.
- **Welcome**: full **coral WaveHeader** (texture overlay) filling most of the screen;
  "Welcome" (Rubik 700, 40) + one-line subhead near the bottom; a round coral arrow FAB
  ("Continue") bottom-right.
- **Login**: WaveHeader at top (~1/3); "Sign in" (Rubik 500, 38) with a 74×3 coral underline;
  two **underline TextFields** (Email w/ mail icon, Password w/ lock icon + eye toggle);
  "Remember Me" checkbox + coral "Forgot Password?"; full-width coral **Login** button;
  centered "Don't have an Account? **Sign up**".
- **Sign-up**: smaller WaveHeader; fields Email, Phone no, Password, Confirm Password;
  full-width "Create account"; "Already have an account? **Login**".
- **Success**: white check badge, "You're in!", secondary "Restart demo".

### 2. Home (feed)
- **Top bar**: app icon (34) + "memy" wordmark + coral dot; right: search icon + avatar (34).
- **Filter chips** row: All / Places / Notes / Links / Food. Selected = coral border +
  brand-wash bg + brand-strong text; unselected = subtle border + muted text.
- **Feed**: 2-column **masonry** of memy cards.
  - Card: radius 20, `shadow-sm`, white. Photo (height 132 short / 184 tall, cover) with a
    mood-emoji chip top-right (28px white 85% circle). Below: name (Inter 600, 14, `text-strong`),
    place·date row (place pin 13px + `text-muted`, 11.5), tag chips (`#tag`, brand-strong on brand-wash).
  - Cards fade+rise on entry, staggered ~45ms.
- Tap card → Detail.

### 3. Search
Opened from the Home search icon.
- Back arrow + a pill search input (sunken bg, search icon, clear "×" when non-empty), autofocus.
- **Empty query**: "Recent" chips (history icon + term) and "Browse by tag" (coral tag chips).
- **Typing**: live results list — rows with 54px thumb, name, place·date, chevron. Matches
  name / tag / place (case-insensitive).
- **No matches**: centered empty state "No memys for "<q>"" + hint.
- Bottom nav remains (Home active).

### 4. Capture (the FAB)
Presented as a sheet/screen.
- Header: close "×" + "New memy" (Rubik 600, 20) + coral **Save** button (small).
- Fields, in order: photo area (168 tall, rounded, "add photo" affordance) · **Name** input ·
  **Link** (link icon) · **Description** (2-row textarea) · row of **Date** (calendar) + **Mood**
  (emoji picker: 5 swatches, selected = coral border + brand-wash) · **Location** (96-tall mini
  map with a coral pin) · **Tags** (current chips + dashed "+ tag" add chips).
- Inputs use the underline style (1.5px `field-line`, coral on focus).

### 5. Detail
- Full-bleed **hero photo** (320 tall) with a top gradient scrim; back button (glass) top-left;
  favorite + more (glass) top-right.
- Body: title (Rubik 700, 26) · meta row (date / place / mood, each with a 15px icon) ·
  description (Inter 15, line-height 1.6) · **link card** (sunken bg, link icon, url, open-in-new) ·
  tag chips · **mini-map** (140 tall, rounded) with a coral pin.

### 6. Collections
- Title "Collections" (Rubik 700, 28) + add icon.
- 2-column grid of album cards: a **2×2 photo mosaic** cover (110 tall) + name (Inter 600, 15)
  + "N memys" (muted). Staggered entrance.
- Bottom nav (Collections active).

### 7. Map
- Stylized map background (warm paper, soft roads/blocks/water — see reference SVG).
- Floating search pill + filter button at top.
- **Pins** (coral `location_on`); the selected pin becomes a 52px photo bubble with a white ring
  + `shadow-coral`.
- **Bottom sheet** for the selected memy: 60px thumb + name + place·date + chevron → Detail.
- Bottom nav (Map active).

### 8. Profile
- **Coral textured header** (150 tall, texture overlay).
- Avatar (88, white ring) overlapping the header; name (Rubik 700, 24) + "Keeping N memys…".
- Stats row: Memys / Collections / Places (Rubik 700 number + muted label).
- Settings list: Settings, Notifications, Privacy, Help & feedback (icon + label + chevron, divided).

### Empty states (reference: (in the source project) `ui_kits/memy-android/States.html`)
Each is a centered coral-tint circle (92px) with a Material icon, a Rubik-700 title, a muted
one-liner, and a coral CTA:
- **Home — no memys**: `auto_awesome` · "Your first memy awaits" · **Add a memy**.
- **Collections — empty**: `grid_view` · "No collections yet" · **New collection**.
- **Empty album**: coral textured header + `add_location_alt` · "This album is empty" · **Add to <name>**.
- **Map — nothing located**: `wrong_location` · "Nothing on the map yet" · **Capture a place**.

## Interactions & state
- App-level state: current tab, open detail (memy or null), capture sheet open, search open + query.
- Press feedback: scale 0.97 (buttons) / 0.92 (round FAB), 140ms ease-out.
- Screen change: fade + 8px rise, 360ms.
- Offline: cache feed locally (Room); optimistic create on Capture.
