import { Smartphone } from 'lucide-react'

export function CTABand() {
  return (
    <section className="max-w-[1160px] mx-auto px-8 py-10 pb-20">
      <div className="relative rounded-[32px] bg-brand shadow-coral overflow-hidden texture-overlay px-12 py-16 text-center">
        <h2 className="font-display font-bold text-[2.5rem] text-on-brand leading-tight mb-4">
          Start your collection today
        </h2>
        <p className="font-ui text-[1.125rem] text-on-brand/80 mb-8 max-w-md mx-auto">
          Free to start. No clutter, no noise — just your memories, organised your way.
        </p>
        <div className="flex flex-wrap justify-center gap-3">
          <StoreBtn label="Google Play" />
          <StoreBtn label="App Store" />
        </div>
      </div>
    </section>
  )
}

function StoreBtn({ label }: { label: string }) {
  return (
    <a href="#"
       className="inline-flex items-center gap-2.5 px-6 h-12 rounded-full
                  bg-surface-inverse text-on-brand font-ui font-semibold text-body-sm
                  hover:opacity-80 active:scale-[0.97] transition-all duration-140">
      <Smartphone className="w-4 h-4" />
      {label}
    </a>
  )
}

export function Footer() {
  return (
    <footer className="border-t border-border-subtle">
      <div className="max-w-[1160px] mx-auto px-8 py-12 grid grid-cols-4 gap-8
                      max-[767px]:grid-cols-2">
        <div>
          <div className="flex items-center gap-2 mb-3">
            <div className="w-8 h-8 rounded-[10px] bg-brand flex items-center justify-center">
              <span className="font-display font-bold text-on-brand text-base">m</span>
            </div>
            <span className="font-display font-bold text-h3 text-text-strong">memy</span>
          </div>
          <p className="font-ui text-body-sm text-text-muted leading-relaxed">
            Capture, organize and revisit the memories you love.
          </p>
        </div>
        {[
          { heading: 'Product',  links: ['Features','Changelog','Pricing','Download'] },
          { heading: 'Company',  links: ['About','Blog','Careers','Press'] },
          { heading: 'Support',  links: ['Help centre','Privacy','Terms','Contact'] },
        ].map(({ heading, links }) => (
          <div key={heading}>
            <p className="font-accent font-semibold text-caption uppercase tracking-wider text-text-subtle mb-3">{heading}</p>
            <ul className="space-y-2">
              {links.map(l => (
                <li key={l}>
                  <a href="#" className="font-ui text-body-sm text-text-muted hover:text-text-strong transition-colors">{l}</a>
                </li>
              ))}
            </ul>
          </div>
        ))}
      </div>
      <div className="border-t border-border-subtle">
        <div className="max-w-[1160px] mx-auto px-8 py-4 flex items-center justify-between">
          <p className="font-ui text-caption text-text-subtle">© 2026 Memy. Made with care.</p>
          <div className="flex gap-4">
            {['Twitter','Instagram','GitHub'].map(s => (
              <a key={s} href="#" className="font-ui text-caption text-text-muted hover:text-text-strong transition-colors">{s}</a>
            ))}
          </div>
        </div>
      </div>
    </footer>
  )
}
