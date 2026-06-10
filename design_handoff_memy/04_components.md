# 04 · Components — contracts

The design system ships React primitives (in the prototypes, on
`window.MemyDesignSystem_4a9135`). Recreate these as idiomatic components in your stack.
Source: (in the source project) `components/`. Each has a `.d.ts` (props) and `.prompt.md` (usage).

---

## Button
Variants: `primary` (coral fill + soft glow) · `secondary` (white + coral outline) ·
`ghost` (transparent) · `link` (inline coral text). Sizes `sm | md | lg`.
- Radius: md 12 (sm 8). Font: Inter 700. Press: scale 0.97.
- Optional leading/trailing icon (Material Symbol name).
```jsx
<Button variant="primary" size="lg" fullWidth>Login</Button>
```

## IconButton
Circular icon-only. Variants `solid` (coral + glow) · `tonal` (coral tint) · `ghost`.
Sizes sm 32 / md 40 / lg 48. The solid arrow is the signature "continue" affordance.

## TextField (underline)
Label above; leading icon; **1.5px underline** that animates `field-line → field-line-active`
(coral) on focus (the leading icon tints coral too). Optional trailing action (e.g. password eye).
Input text uses Rubik 14 / 0.2px tracking.
```jsx
<TextField label="Email" icon="mail" placeholder="demo@email.com" />
```

## Checkbox
14px rounded square; coral fill + white check when checked; optional inline label. (Used for
"Remember Me".)

## Card
Rounded surface. `elevation`: flat (hairline border) / sm / md. `radius`: md / lg / xl / 2xl.

## Badge / Tag
Small pill. Badge tones: brand / tint / neutral / success / danger. The memy "#tag" is a tag chip:
brand-strong text on brand-wash, radius 999, ~11.5px Inter 600.

## Avatar
Round image; `size` px; optional hairline ring.

## Logo (brand)
Variants: `icon` (rounded coral app icon) · `mark` (white "m/book" glyph for coral surfaces) ·
`lockup` (icon + "memy" wordmark) · `stacked`. Wordmark = Rubik 700 lowercase + coral dot.
Use the PNGs in `assets/` — never redraw.

## WaveHeader (brand)
The signature coral hero: a solid coral block with a soft wavy bottom edge and the topographic
texture overlay (~55%). Built for the 393–412px phone canvas; `offsetTop` controls how much wave
shows. Use on Welcome / Login / Sign-up / Profile header.

---

## Bottom navigation (Android, "Nav A")
4 destinations + center docked coral FAB (see `02_android_app.md`). Active = filled coral icon +
coral-wash pill + strong label.

## Icon helper
Both kits render Material Symbols Rounded via a small wrapper that sets
`font-variation-settings` for FILL/weight. In production use your platform's Material icon support
(Compose `Icon` + material-icons-extended; web `material-symbols` font or an icon lib).
