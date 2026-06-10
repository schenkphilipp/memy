import Link from 'next/link'
import { Heart, Smartphone } from 'lucide-react'

export default function HeroSection() {
  return (
    <section className="max-w-[1160px] mx-auto px-8 py-[88px] grid grid-cols-[1.05fr_0.95fr] gap-12 items-center
                        max-[767px]:grid-cols-1 max-[767px]:py-16">
      {/* Left */}
      <div className="flex flex-col gap-6">
        {/* Eyebrow pill */}
        <div className="inline-flex items-center gap-2 self-start
                        px-4 py-2 rounded-full bg-brand-wash border border-brand-tint">
          <Heart className="w-4 h-4 text-brand-strong" />
          <span className="font-ui text-caption font-semibold text-brand-strong">
            Your memories, kept beautifully
          </span>
        </div>

        {/* H1 */}
        <h1 className="font-display font-bold text-display text-text-strong leading-[1.1] tracking-[-0.02em]">
          Keep every moment{' '}
          <span className="text-brand">worth revisiting.</span>
        </h1>

        {/* Subhead */}
        <p className="font-ui text-[1.1875rem] text-text-muted leading-relaxed max-w-[460px]">
          Capture places, notes, links and photos in a tap. Organise into collections,
          see them on your personal map, and rediscover them anytime.
        </p>

        {/* Store buttons */}
        <div className="flex flex-wrap gap-3">
          <StoreButton platform="android" label="Google Play" />
          <StoreButton platform="ios" label="App Store" />
        </div>

        {/* Social proof */}
        <div className="flex items-center gap-3">
          <div className="flex -space-x-2">
            {['coral', 'teal', 'amber'].map((c, i) => (
              <div key={i}
                   className="w-8 h-8 rounded-full border-2 border-surface-page bg-neutral-200" />
            ))}
          </div>
          <p className="font-ui text-body-sm text-text-muted">
            Loved by people who keep things that matter
          </p>
        </div>
      </div>

      {/* Right — phone mock */}
      <div className="flex justify-center relative">
        {/* Glow */}
        <div className="absolute inset-[20%] rounded-full bg-brand/20 blur-3xl pointer-events-none" />
        <PhoneMock />
      </div>
    </section>
  )
}

function StoreButton({ platform, label }: { platform: 'android' | 'ios'; label: string }) {
  return (
    <a href="#"
       className="inline-flex items-center gap-2.5 px-5 h-12 rounded-full
                  bg-surface-inverse text-on-brand font-ui font-semibold text-body-sm
                  hover:opacity-80 active:scale-[0.97] transition-all duration-140">
      <Smartphone className="w-4 h-4" />
      {label}
    </a>
  )
}

function PhoneMock() {
  return (
    <div className="relative w-[310px] rounded-[40px] border-[8px] border-neutral-900
                    shadow-lg overflow-hidden bg-surface-page">
      {/* Status bar */}
      <div className="h-8 bg-surface-page flex items-center justify-between px-4 text-[10px] text-text-muted font-ui">
        <span>9:30</span>
        <div className="w-4 h-4 rounded-full bg-neutral-900" />
        <span>▶◀■</span>
      </div>
      {/* Top bar */}
      <div className="flex items-center justify-between px-4 py-2 bg-surface-page">
        <div className="flex items-center gap-2">
          <div className="w-7 h-7 rounded-[8px] bg-brand flex items-center justify-center">
            <span className="font-display font-bold text-on-brand text-sm">m</span>
          </div>
          <span className="font-display font-bold text-text-strong text-[15px]">memy</span>
          <span className="w-1 h-1 rounded-full bg-brand" />
        </div>
      </div>
      {/* Filter chips */}
      <div className="flex gap-2 px-3 pb-2 overflow-hidden">
        {['All','Places','Notes'].map((l,i) => (
          <span key={l} className={`px-3 py-0.5 rounded-full text-[10px] font-ui border ${i===0 ? 'border-brand bg-brand-wash text-brand-strong font-semibold' : 'border-border-subtle text-text-muted'}`}>
            {l}
          </span>
        ))}
      </div>
      {/* Mini feed */}
      <div className="grid grid-cols-2 gap-2 px-3 pb-3">
        {[
          { name:'Sunset at Miradouro', mood:'😌', tall:true },
          { name:'Slow morning',        mood:'☕', tall:false },
          { name:'First proper ramen',  mood:'🍜', tall:false },
          { name:'The book',            mood:'📖', tall:true },
        ].map((card, i) => (
          <div key={i} className="bg-surface-card rounded-[12px] overflow-hidden shadow-sm">
            <div className={`bg-gradient-to-br from-coral-200 to-coral-400 relative ${card.tall ? 'h-20' : 'h-12'}`}>
              <span className="absolute top-1 right-1 text-[10px] bg-white/80 rounded-full w-5 h-5 flex items-center justify-center">{card.mood}</span>
            </div>
            <div className="px-1.5 py-1">
              <p className="text-[9px] font-semibold text-text-strong font-ui truncate">{card.name}</p>
            </div>
          </div>
        ))}
      </div>
      {/* Bottom nav */}
      <div className="h-12 bg-surface-card border-t border-border-subtle flex items-center justify-around relative">
        {['🏠','⊞','','🗺','👤'].map((icon, i) => (
          <div key={i} className={`flex flex-col items-center justify-center text-[14px] ${i===2 ? 'w-0' : 'flex-1'}`}>
            {i !== 2 && <span>{icon}</span>}
          </div>
        ))}
        <div className="absolute top-[-18px] left-1/2 -translate-x-1/2
                        w-10 h-10 rounded-[14px] bg-brand shadow-coral
                        flex items-center justify-center">
          <span className="text-on-brand text-lg font-bold leading-none">+</span>
        </div>
      </div>
    </div>
  )
}
