# Handoff: Memy — App & Website

This package lets a developer (or Claude Code) build the **Memy** product — an
Android app and a website — from the design system and prototypes in this project.

> **Memy** is a warm, playful-but-professional app to **capture, organize and revisit
> memories** (places, notes, links, photos). Brand: a friendly **coral** identity with
> generous rounded corners and an organic topographic texture.

---

## ⚠️ About this package
This package is **self-sufficient documentation** — the specs below describe the intended
look and behavior precisely enough to build from. They are **not production code to copy
verbatim**.

Your task is to **recreate the designs in the target environments**:
- **Android** → Kotlin + Jetpack Compose + Material 3 (recommended; see `02_android_app.md`)
- **Website** → Next.js + React (recommended; see `03_website.md`)

Reuse each platform's idiomatic patterns. The specs tell you *what* to build and the exact
visual values — not *how* to structure the production code. The live prototypes (and their
readable `.jsx`/CSS source) live in the **source Memy Design System project**; open them there
to see everything running.

## Fidelity
- **Android app screens** — **high-fidelity**. Recreate pixel-faithfully using the tokens here.
- **Website (landing + web app)** — **high-fidelity**.
- **Android wireframes** — **low-fidelity**, referenced only to show the navigation rationale
  (we chose **Navigation A**: bottom nav + center capture FAB).

---

## What's in this package
| File | What it covers |
|---|---|
| `00_overview.md` | Product, data model, brand one-pager |
| `01_design_tokens.md` | Every color, type, spacing, radius, shadow, motion value |
| `02_android_app.md` | Screen-by-screen spec for the Android app |
| `03_website.md` | Marketing landing + responsive web app spec |
| `04_components.md` | Reusable component contracts (Button, TextField, etc.) |
| `05_build_and_deploy.md` | Stack, backend, CI, release steps |
| `assets/` | Logo (all variants), topographic texture, the photo set |

## How to use it
1. Read `00_overview.md` then `01_design_tokens.md`.
2. Stand up the tokens in each platform (CSS vars for web; `Theme.kt` + `colors.xml` for Android).
3. Build the backend/data layer from `00_overview.md` (the data model) and `05_build_and_deploy.md`.
4. Implement screens from `02_android_app.md` / `03_website.md`, leaning on `04_components.md`.
5. For the live, running prototypes, open the UI-kit `.html` files in the **source Memy
   Design System project**.

## Visual reference (screenshots / PDF)
The capture tooling can't rasterize the prototypes' photo thumbnails, so instead of low-quality
PNGs, generate a **pixel-correct PDF** from the live screens:
- In the source project open **`ui_kits/memy-android/Gallery.html`** and print it (Cmd/Ctrl+P →
  Save as PDF). It lays out all 12 app screens (7 core + 5 empty/search states) with real photos
  and icons. Drop the saved PDF into this package as `screens.pdf`.
- The marketing landing (`ui_kits/memy-web/index.html`) and web app (`ui_kits/memy-web/webapp.html`)
  print the same way.

## Source design system
This package is extracted from the **Memy Design System** project. The live Design System
tab there shows all foundation cards, components, and UI kits — plus the readable prototype
source (`components/*.jsx` + `.d.ts`, `ui_kits/**`, `tokens/*.css`, `styles.css`). Original
brand input: the `SimpleLoginMemy.fig` Figma file and the `schenkphilipp/memy` repo (README
only at time of writing).

**The markdown specs (`00`–`05`) in this package are self-sufficient** — a developer who
wasn't in the original conversation can build the product from them alone. The `assets/`
folder holds the real logo, texture, and photo set to ship.
