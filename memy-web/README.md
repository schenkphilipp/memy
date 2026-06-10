# Memy — Website (Next.js + Supabase)

Marketing landing page + responsive web app, built from the `03_website.md` spec.
Deploys to Vercel with Supabase as the backend.

---

## What's included

| Surface | Route | Type |
|---------|-------|------|
| Marketing landing | `/` | Static (SSG), public |
| Login | `/auth/login` | Client component |
| Sign-up | `/auth/signup` | Client component |
| Home feed | `/app/home` | Server + Client |
| Collections | `/app/collections` | Server |
| Map | `/app/map` | Server |
| Profile | `/app/profile` | Server |

---

## 1 — Supabase setup (5 min)

1. Go to [supabase.com](https://supabase.com) → **New project**.
2. Note your **Project URL** and **anon/public key** (Settings → API).
3. Run the migration in the SQL editor:
   ```
   supabase/migrations/001_initial_schema.sql
   ```
   This creates `profiles`, `memys`, `collections`, a storage bucket for photos,
   and enables Row Level Security so users only see their own data.
4. Enable **Email auth** under Authentication → Providers (enabled by default).

---

## 2 — Local development

```bash
# 1. Clone and install
git clone https://github.com/schenkphilipp/memy
cd memy
npm install

# 2. Create .env.local from the template
cp .env.local.example .env.local
# → Fill in NEXT_PUBLIC_SUPABASE_URL and NEXT_PUBLIC_SUPABASE_ANON_KEY

# 3. Run
npm run dev
# → http://localhost:3000
```

---

## 3 — Deploy to Vercel (recommended)

```bash
npm i -g vercel
vercel
```

Set the same two env vars in the Vercel dashboard under **Settings → Environment Variables**:
- `NEXT_PUBLIC_SUPABASE_URL`
- `NEXT_PUBLIC_SUPABASE_ANON_KEY`

Then add your Vercel deployment URL to **Supabase → Authentication → URL Configuration**:
- Site URL: `https://your-app.vercel.app`
- Redirect URLs: `https://your-app.vercel.app/**`

---

## 4 — Connect to the GitHub repo

```bash
cd memy-web   # (or wherever this code lives)
git init
git remote add origin https://github.com/schenkphilipp/memy
git add .
git commit -m "feat: Next.js website + Supabase backend"
git push -u origin main
```

Vercel auto-deploys on every push to `main` once the project is linked.

---

## Project structure

```
├── app/
│   ├── layout.tsx                  # Root layout (fonts, metadata)
│   ├── page.tsx                    # Marketing landing (SSG)
│   ├── globals.css                 # Tailwind + design tokens as CSS vars
│   ├── auth/
│   │   ├── login/page.tsx          # Login form (Supabase Auth)
│   │   └── signup/page.tsx         # Sign-up form
│   └── app/
│       ├── layout.tsx              # Authenticated shell — sidebar + auth guard
│       ├── home/
│       │   ├── page.tsx            # Server: fetches memys
│       │   └── HomeClient.tsx      # Client: search, filter, detail panel
│       ├── collections/page.tsx    # Collections grid
│       ├── map/page.tsx            # Map (placeholder — add Maps API key)
│       └── profile/page.tsx        # Profile + settings
│
├── components/
│   ├── ui/                         # Button, Input, TagChip, MemyCard,
│   │   │                           # CollectionCard, DetailPanel, EmptyState
│   │   └── ...
│   ├── layout/
│   │   ├── AppSidebar.tsx          # 248px sidebar
│   │   └── AppTopBar.tsx           # Search top bar
│   └── sections/                   # Landing page sections
│       ├── LandingNav.tsx
│       ├── HeroSection.tsx
│       ├── ValueProps.tsx
│       ├── FeatureShowcase.tsx
│       └── CTABand.tsx + Footer
│
├── lib/
│   ├── supabase/
│   │   ├── client.ts               # Browser Supabase client
│   │   └── server.ts               # Server Supabase client (SSR)
│   └── utils.ts                    # cn(), formatDate()
│
├── types/database.ts               # Full TypeScript types for all tables
├── middleware.ts                    # Session refresh + route protection
├── supabase/migrations/
│   └── 001_initial_schema.sql      # Schema: profiles, memys, collections + RLS
├── tailwind.config.ts              # All design tokens as Tailwind theme
└── .env.local.example              # Env vars template
```

---

## Design tokens

All token values from `01_design_tokens.md` are in `tailwind.config.ts`:

```ts
colors: {
  brand:        '#ff8383',   // Coral 400 — primary
  'brand-strong': '#ef6d6d', // hover/pressed
  'brand-wash':   '#fff5f5', // tinted bg
  surface: { page: '#fffcfc', card: '#ffffff', sunken: '#f5f5f5' },
  text:    { strong: '#212121', body: '#616161', muted: '#757575' },
  // ...
}
boxShadow: {
  coral: '0 14px 28px rgba(255,131,131,.28), 0 4px 10px rgba(255,131,131,.18)',
}
```

Use them as Tailwind classes: `bg-brand`, `text-brand-strong`, `shadow-coral`, etc.

---

## Responsive breakpoints (from spec)

| Width | Layout |
|-------|--------|
| ≥1100px | Sidebar (248px) + 3-col masonry + 460px detail panel |
| 768–1099px | Sidebar collapses to icon rail; 2-col feed; detail panel 50% |
| <768px | Sidebar hidden; 1-col feed; detail panel full-screen |

---

## Production checklist

- [ ] Add Google Maps / Mapbox key for the Map page (`NEXT_PUBLIC_GOOGLE_MAPS_API_KEY`)
- [ ] Wire the "New memy" button to a capture modal (Supabase Storage upload → `memys` insert)
- [ ] Wire the "New collection" button (modal → `collections` insert)
- [ ] Add optimistic UI with `useOptimistic` or SWR for the feed
- [ ] Add OG image route (`app/opengraph-image.tsx`)
- [ ] Accessibility audit (contrast, focus rings, `aria-label`s — all already present)
- [ ] Add Sentry for error monitoring
- [ ] Configure Supabase email templates to match the Memy brand

---

## Supabase schema at a glance

```sql
profiles   (id, email, name, phone, avatar_url, created_at)
memys      (id, user_id, name, description, url, tags[], photo_url,
            date, mood, location_lat, location_lng, location_label,
            collection_ids[], created_at, fts tsvector)
collections (id, user_id, name, cover_urls[], memy_count, created_at)
```

All tables have RLS: users can only read/write their own rows.
Full-text search is indexed on the `fts` generated column.
Photo uploads go to the `memy-photos` storage bucket (public read, owner write).
