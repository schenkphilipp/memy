# 03 · Website — landing + web app spec

**Stack (recommended):** Next.js (App Router) + React + the shared token CSS.
**Fidelity:** high. **Reference:** (in the source project) `ui_kits/memy-web/` (`index.html` landing, `webapp.html` app).
The prototypes are React already, so component structure ports closely.

---

## A. Marketing landing (`index.html`)
Goal: **brand presence** + drive app downloads. Content width max **1160px**, centered, 32px gutters.

### Nav (sticky, blurred)
72px tall, `rgba(255,252,252,.82)` + backdrop-blur, hairline bottom border.
Logo (36, radius 10) + "memy" wordmark + coral dot · links (Features / How it works / Stories) ·
right: "Log in" text + coral **Get the app** button.

### Hero (2-col grid 1.05 / 0.95, ~72/88px vertical pad)
- Left: eyebrow pill (heart icon + "Your memories, kept beautifully", brand-strong on brand-wash) ·
  H1 "Keep every moment **worth revisiting**." (Rubik 700, 58, "revisiting" in coral) ·
  subhead (Inter 19, muted, max 460) · two **store buttons** (dark `surface-inverse` pills:
  `android` icon + "Google Play", `phone_iphone` icon + "App Store") · social-proof row
  (3 overlapped avatars + "Loved by people who keep things that matter").
- Right: a **phone mock** (310px) showing the live feed (coral top bar, filter chips, masonry of
  the warm photos, bottom nav with coral FAB). Soft coral radial glow behind it; soft ground shadow.

### Value props (4-col grid)
Cards: white, radius 24, `shadow-sm`, hairline border, 26 pad. Each: coral-wash rounded-16 icon
tile (50px) + title (Rubik 700, 19) + body (Inter 14.5, muted).
1. **Capture in a tap** (`bolt`) · 2. **Organize gently** (`grid_view`) ·
3. **See where it happened** (`map`) · 4. **Revisit anytime** (`history`).

### Feature showcase (3 alternating rows, 90px gap)
Each row: 2-col. Text side: uppercase coral eyebrow (icon + tag) · H2 (Rubik 700, 34) · body
(Inter 17) · "Learn more →" link. Image side: a large photo card (radius 32, `shadow-lg`,
rotated −2°) with a smaller overlapping photo card (150px, white 5px border, rotated +3°).
Rows: **Collections** (beach + market), **Map** (mountains + road, flipped), **Capture** (sunset + coffee).

### CTA band
Full-width coral panel (radius 32, `shadow-coral`) with the **topographic texture** at 50%.
H2 "Start your collection today" (white, Rubik 700, 40) + subhead + two store buttons.

### Footer
4-col (brand blurb + Product / Company / Support link columns), then a divider row with
"© 2026 Memy. Made with care." + 3 social icons.

---

## B. Web app (`webapp.html`)
The product as a responsive desktop web app, shown in browser chrome in the prototype.

### Layout: sidebar + main
- **Sidebar** (248px, white, right border): logo + wordmark · full-width coral **New memy** button ·
  nav (Home / Collections / Map / Tags — active = brand-wash pill + brand-strong) · "Collections"
  list (emoji + name) · account footer (avatar + name + "N memys" + settings icon).
- **Main**: top bar (page title "Home", Rubik 700 24; right: 320px pill **search** input + filter
  button) · scrollable **3-column masonry** of memy cards (radius 20, photo 170/240 tall, mood chip,
  name, place·date, tag chips; staggered fade-in).
- **Detail panel**: clicking a card slides in a 460px right panel over a scrim — hero photo (300),
  close (glass), title, meta, description, link card, tags, Edit / Share buttons.
- **Search** filters the feed live (name / tag / place). No matches → centered message.

### Responsive behavior (described; implement at build)
- **≥1100px**: sidebar (248) + 3-col feed + 460 detail panel.
- **768–1099px**: sidebar collapses to an icon rail (~72px); feed 2-col; detail panel ~50% width.
- **<768px**: sidebar becomes a bottom bar or hamburger drawer; feed 1-col; detail becomes a
  full-screen route. (This mirrors the Android layout — reuse those decisions.)

---

## Shared
- Icons: Material Symbols Rounded. Fonts: Rubik + Inter (+ Montserrat for captions).
- Imagery: the local `assets/photos/*.jpg` set in the prototype — **swap for real photography**.
- All colors/spacing/radii/shadows come from `01_design_tokens.md` (ship `styles.css` directly).
- Links/buttons hover: ~0.78 opacity or shift toward `--brand-strong`; respect reduced-motion.
