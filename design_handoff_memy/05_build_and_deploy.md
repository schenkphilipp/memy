# 05 · Build & Deploy

A practical path from these designs to shipped product. Build the shared layers once;
both clients consume them.

## 0. Shared design tokens (do first)
- Put the token values (`01_design_tokens.md`) into a small tokens source (e.g. **Style
  Dictionary**) that outputs: CSS custom properties (web) and `Color.kt`/`colors.xml` (Android).
- Ship fonts (Rubik, Inter, Montserrat) and the brand assets (`assets/`) as versioned files.

## 1. Backend & data (one API, both clients)
- **Entities**: `user`, `memy`, `collection` (schema in `00_overview.md`).
- **Stack options**:
  - Fastest: **Supabase** (Postgres + Auth + Storage + row-level security) or **Firebase**.
  - Custom: **Node + Postgres + Prisma**, REST or tRPC/GraphQL.
- **Auth**: email/password + phone (matches sign-up) + optional social. Issue JWT/session.
- **Storage**: object storage for photos with server-side thumbnails (photo-first product).
- **Geo**: store `lat/lng/label` on memys; the map queries by bounds.

## 2. Android app
- **Kotlin + Jetpack Compose + Material 3**; theme from the tokens.
- Modules: `auth`, `feed`, `capture`, `detail`, `collections`, `map` (Google Maps Compose),
  `profile`. Networking: Retrofit/Ktor; cache: Room; images: Coil.
- Build order: Auth → Home → Capture → Detail → Collections → Search → Map → Profile + empty states.

## 3. Website
- **Next.js + React**, importing the shared token CSS / components.
- **Marketing landing**: static / SSG, SEO + OpenGraph, edge-cached.
- **Web app**: authenticated routes on the same API; finish responsive breakpoints (see `03_website.md`).

## 4. Deploy
| Surface | Target |
|---|---|
| Backend | Supabase managed, or Railway / Render / Fly.io |
| Website | **Vercel** (Next.js) or Netlify |
| Android | **Google Play** — internal → closed beta → production (signed AAB) |
| Photos/assets | CDN-backed object storage |

## 5. CI/CD (GitHub Actions)
- Web: lint + typecheck + Playwright smoke; preview deploy per PR; prod on main.
- Android: lint + unit tests; build signed AAB; upload to Play internal track.
- Tokens: a change re-generates platform outputs and bumps the package both clients depend on.

## 6. Suggested sequence
1. Tokens + backend/auth + schema.
2. **Ship the marketing landing first** (cheap, builds presence while the app is built).
3. Android MVP (Auth → Capture → Feed → Detail) to a Play beta.
4. Web app in parallel, reusing the API.
5. Layer in Map, Collections, Search, empty states, polish → public launch.

## 7. Analytics & quality (light)
- Track: sign-up, first memy created, capture completion, search used, collection created.
- Add crash reporting (Crashlytics / Sentry) and accessibility passes (tap targets ≥44px,
  contrast, reduced-motion — all already respected in the designs).
