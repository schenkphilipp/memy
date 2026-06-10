import type { Config } from 'tailwindcss'

const config: Config = {
  content: [
    './pages/**/*.{js,ts,jsx,tsx,mdx}',
    './components/**/*.{js,ts,jsx,tsx,mdx}',
    './app/**/*.{js,ts,jsx,tsx,mdx}',
  ],
  theme: {
    extend: {
      // ── Colors (from 01_design_tokens.md) ──────────────────────────────
      colors: {
        coral: {
          50:  '#fff5f5',
          100: '#ffe3e3',
          200: '#ffcccc',
          300: '#ff9d95',
          400: '#ff8383', // PRIMARY
          500: '#f2958d',
          600: '#ef6d6d', // hover/pressed
          700: '#d65656',
        },
        brand:        '#ff8383',
        'brand-strong': '#ef6d6d',
        'brand-tint':   '#ffcccc',
        'brand-wash':   '#fff5f5',
        'on-brand':     '#fffcfc',
        accent: {
          red: '#ff4b5e',
        },
        neutral: {
          25:  '#fffcfc',
          50:  '#f7f5f4',
          100: '#f5f5f5',
          200: '#eeeeee',
          300: '#e0e0e0',
          350: '#dadada',
          400: '#bdbdbd',
          500: '#9e9e9e',
          600: '#757575',
          700: '#616161',
          800: '#424242',
          900: '#212121',
          950: '#141414',
        },
        surface: {
          page:    '#fffcfc',
          card:    '#ffffff',
          sunken:  '#f5f5f5',
          muted:   '#eeeeee',
          inverse: '#212121',
        },
        text: {
          strong:  '#212121',
          heading: '#424242',
          body:    '#616161',
          muted:   '#757575',
          subtle:  '#bdbdbd',
          link:    '#ff8383',
        },
        border: {
          subtle:  '#e0e0e0',
          default: '#dadada',
        },
        success: '#4caf82',
        warning: '#f2a65a',
        info:    '#6aa9d8',
        danger:  '#ff4b5e',
      },

      // ── Typography ──────────────────────────────────────────────────────
      fontFamily: {
        display: ['Rubik', 'sans-serif'],
        ui:      ['Inter', 'sans-serif'],
        accent:  ['Montserrat', 'sans-serif'],
      },

      // ── Font sizes (px → rem) ───────────────────────────────────────────
      fontSize: {
        'display': ['2.5rem',   { lineHeight: '1.1',  letterSpacing: '-0.02em' }],
        'title':   ['2.375rem', { lineHeight: '1.1',  letterSpacing: '-0.01em' }],
        'h1':      ['1.75rem',  { lineHeight: '1.25', letterSpacing: '-0.01em' }],
        'h2':      ['1.375rem', { lineHeight: '1.25' }],
        'h3':      ['1.125rem', { lineHeight: '1.4'  }],
        'label':   ['1.0625rem',{ lineHeight: '1.4'  }],
        'body':    ['1rem',     { lineHeight: '1.6'  }],
        'body-sm': ['0.9375rem',{ lineHeight: '1.6'  }],
        'field':   ['0.875rem', { lineHeight: '1.4',  letterSpacing: '0.2px'  }],
        'caption': ['0.75rem',  { lineHeight: '1.4',  letterSpacing: '0.4px'  }],
      },

      // ── Spacing (4px grid) ──────────────────────────────────────────────
      spacing: {
        '4.5':  '1.125rem',
        '18':   '4.5rem',
        '22':   '5.5rem',
        '25':   '6.25rem',
      },

      // ── Border radius ───────────────────────────────────────────────────
      borderRadius: {
        xs:   '6px',
        sm:   '8px',
        md:   '12px',
        lg:   '20px',
        xl:   '24px',
        '2xl':'32px',
        full: '999px',
      },

      // ── Shadows ─────────────────────────────────────────────────────────
      boxShadow: {
        xs:     '0 1px 2px rgba(33,33,33,.06)',
        sm:     '0 2px 8px rgba(33,33,33,.06), 0 1px 2px rgba(33,33,33,.04)',
        md:     '0 8px 24px rgba(33,33,33,.08), 0 2px 6px rgba(33,33,33,.04)',
        lg:     '0 18px 40px rgba(33,33,33,.12), 0 4px 12px rgba(33,33,33,.06)',
        coral:  '0 14px 28px rgba(255,131,131,.28), 0 4px 10px rgba(255,131,131,.18)',
        focus:  '0 0 0 3px rgba(255,131,131,.35)',
      },

      // ── Transitions ─────────────────────────────────────────────────────
      transitionTimingFunction: {
        'ease-out-memy':   'cubic-bezier(.22,1,.36,1)',
        'ease-bounce':     'cubic-bezier(.34,1.56,.64,1)',
      },
      transitionDuration: {
        '140': '140ms',
        '220': '220ms',
        '360': '360ms',
      },
    },
  },
  plugins: [],
}

export default config
