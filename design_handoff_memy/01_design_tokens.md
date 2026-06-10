# 01 · Design Tokens

These are the single source of truth. The HTML prototypes reference them as CSS custom
properties (see (in the source project) `tokens/`). Mirror them into each platform:
- **Web**: ship `styles.css` (it `@import`s the token files) — vars are already there.
- **Android**: translate to `Color.kt` / `Theme.kt` (Compose) or `colors.xml` + `themes.xml`.

---

## Colors

### Coral (primary brand)
| Token | Hex |
|---|---|
| `--coral-50`  | `#fff5f5` |
| `--coral-100` | `#ffe3e3` |
| `--coral-200` | `#ffcccc` (tint / glow) |
| `--coral-300` | `#ff9d95` |
| **`--coral-400`** | **`#ff8383`  ← PRIMARY** |
| `--coral-500` | `#f2958d` |
| `--coral-600` | `#ef6d6d` (hover/pressed) |
| `--coral-700` | `#d65656` |
| `--accent-red` | `#ff4b5e` (emphasis/alert) |

### Warm neutrals (greige)
`#ffffff` · `--neutral-25 #fffcfc` (warm off-white surface) · `#f7f5f4` · `#f5f5f5` ·
`#eeeeee` · `#e0e0e0` · `#dadada` · `#bdbdbd` · `#9e9e9e` · `#757575` · `#616161` ·
`#424242` · `--neutral-900 #212121` (ink) · `#141414`

### Support
`--success #4caf82` · `--warning #f2a65a` · `--info #6aa9d8` · `--danger #ff4b5e`

### Semantic aliases (reference these in code)
| Alias | = |
|---|---|
| `--brand` | coral-400 `#ff8383` |
| `--brand-strong` | coral-600 `#ef6d6d` |
| `--brand-tint` | coral-200 `#ffcccc` |
| `--brand-wash` | coral-50 `#fff5f5` |
| `--on-brand` | `#fffcfc` |
| `--surface-page` | `#fffcfc` |
| `--surface-card` | `#ffffff` |
| `--surface-sunken` | `#f5f5f5` |
| `--surface-muted` | `#eeeeee` |
| `--surface-inverse` | `#212121` |
| `--text-strong` | `#212121` |
| `--text-heading` | `#424242` |
| `--text-body` | `#616161` |
| `--text-muted` | `#757575` |
| `--text-subtle` | `#bdbdbd` |
| `--text-link` | coral-400 |
| `--border-subtle` | `#e0e0e0` |
| `--border-default` | `#dadada` |
| `--field-line` | `#bdbdbd` |
| `--field-line-active` | coral-400 |

---

## Typography
| Family | Var | Use |
|---|---|---|
| **Rubik** | `--font-display` | display, headings, the "memy" wordmark (700), input text (14) |
| **Inter** | `--font-ui` | UI labels, body, buttons |
| **Montserrat** | `--font-accent` | tiny eyebrow captions |

Weights: 400 / 500 / 600 / 700.

Scale (px): display 40 · title 38 · h1 28 · h2 22 · h3 18 · label 17 · body 16 ·
body-sm 15 · field 14 · caption 12.
Line-heights: tight 1.1 · snug 1.25 · normal 1.4 · relaxed 1.6.
Letter-spacing: headings `-0.01em`/`-0.02em`; input text `0.2px`.

**Minimum sizes**: body text never below 14px on mobile; tap targets ≥ 44px.

---

## Spacing — 4px grid
`4 · 8 · 12 · 16 · 20 · 24 · 32 · 40 · 48 · 64 · 80`
- Mobile screen gutters: **24px**
- Label → input gap: 12px · between fields: 18–24px
- Control height (primary button / input row): **49px**; compact 36px

---

## Radii
`xs 6 · sm 8 · md 12 (buttons, inputs) · lg 20 (inner cards) · xl 24 (hero) ·
2xl 32 (panels) · full 999 (pills, avatars)`

---

## Shadows / elevation (soft, low-contrast)
- `--shadow-xs` `0 1px 2px rgba(33,33,33,.06)`
- `--shadow-sm` `0 2px 8px rgba(33,33,33,.06), 0 1px 2px rgba(33,33,33,.04)`
- `--shadow-md` `0 8px 24px rgba(33,33,33,.08), 0 2px 6px rgba(33,33,33,.04)`
- `--shadow-lg` `0 18px 40px rgba(33,33,33,.12), 0 4px 12px rgba(33,33,33,.06)`
- **`--shadow-coral`** `0 14px 28px rgba(255,131,131,.28), 0 4px 10px rgba(255,131,131,.18)`
  — the signature lift under coral surfaces (FAB, CTA, primary button)
- Focus ring: `0 0 0 3px rgba(255,131,131,.35)`

---

## Motion
- Easing: `--ease-out cubic-bezier(.22,1,.36,1)` (default), `--ease-bounce cubic-bezier(.34,1.56,.64,1)`
- Durations: fast 140ms · base 220ms · slow 360ms
- Screens fade + rise 8px on entry. Press states **scale down** (0.97 buttons, 0.92 round FAB).
- No infinite/looping decoration.

---

## Texture
`assets/texture-coral.png` — faint white topographic contour lines, tiled ~360–420px,
**~55% opacity** over any coral surface (heroes, CTA band, profile header).
