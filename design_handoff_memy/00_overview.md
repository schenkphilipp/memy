# 00 · Overview

## Product
**Memy** — capture, organize, and revisit your memories. A "memy" is any moment, place,
note, link, or photo worth keeping. Users save them in a tap, sort them into collections,
see them on a personal map, and search them back anytime.

## Brand voice
Playful but professional. Friendly and human, never goofy.
- Address the user as **you**; the brand is **we**.
- **Sentence case** everywhere except tiny eyebrow captions.
- Buttons are short, verb-first: "Login", "Create account", "Save", "Add a memy".
- Microcopy is warm and lightly conversational ("Your first memy awaits").
- **No emoji in chrome/UI labels** (emoji only appear as user-chosen *mood* values on memys).
- Example: ~~"Save and share your memes"~~ → "Capture, organize and revisit the memories you love."

## Data model
The product is built around two entities (as supplied by the team):

### `user`
All attributes required for login and account management.
```
id            string (uuid, pk)
email         string (unique)
passwordHash  string            // or external auth provider id
name          string
phone         string?           // collected on sign-up
avatarUrl     string?
createdAt     datetime
```

### `memy`
```
id            string (uuid, pk)
userId        string (fk → user.id)
name          string            // title, e.g. "Sunset at Miradouro"
description   string?           // free note
url           string?           // an associated link
tags          string[]          // e.g. ["travel","portugal"]
photoUrl      string?           // primary image (photo-first product)
date          date              // when the memory happened
mood          string?           // an emoji, e.g. "😌"
location      { lat:number, lng:number, label:string }?   // for the map
collectionIds string[]          // membership in collections/albums
createdAt     datetime
```

### `collection` (albums)
```
id        string (uuid, pk)
userId    string (fk)
name      string            // "Trips", "Good eats"
coverUrls string[]          // up to 4 memy photos for the mosaic cover
memyCount number            // denormalized for the grid
```

## Core surfaces
1. **Android app** (primary) — Material 3 + coral. Nav: bottom bar with a center capture FAB.
   Screens: Auth, Home feed, Search, Capture, Detail, Collections, Map, Profile + empty states.
2. **Website** — marketing landing (brand presence + downloads) and a responsive web app
   (sidebar + feed + detail) on the same API.

## Key user flows
- **Auth**: Splash → Welcome → Login ⇄ Sign-up → app.
- **Capture**: FAB → add photo, name, link, description, date, mood, location pin, tags → Save.
- **Browse**: Home masonry feed → filter chips / search → open Detail.
- **Organize**: Collections grid → open a collection → its memys.
- **Revisit**: Map of pins → tap pin → bottom-sheet → Detail.

## Iconography
- **Android & web**: Material Symbols Rounded (line icons, ~24px). This is the production set.
- The original Figma used a Hugeicons-style line set — Material Symbols is the chosen
  production substitute (same stroke feel). Swap to licensed Hugeicons if exact parity matters.
- Logo mark: a white "m" whose second leg becomes a stack of pages (a book of memories),
  on the coral rounded app icon. Never redraw it — use the supplied PNGs.
