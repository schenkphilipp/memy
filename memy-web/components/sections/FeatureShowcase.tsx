import { FolderOpen, Map, Camera } from 'lucide-react'

const rows = [
  {
    eyebrow: { icon: FolderOpen, label: 'Collections' },
    headline: 'Your memories, beautifully grouped.',
    body: 'Create albums for Trips, Good eats, Reading — whatever fits your life. Browse by collection or let everything live together on the feed.',
    reverse: false,
    gradientFrom: 'from-coral-200', gradientTo: 'to-coral-400',
  },
  {
    eyebrow: { icon: Map, label: 'Map' },
    headline: 'Every memory pinned to the moment.',
    body: 'Attach a location when you save a memy and it shows up on your personal map — tap any pin to revisit the story behind it.',
    reverse: true,
    gradientFrom: 'from-blue-100', gradientTo: 'to-blue-300',
  },
  {
    eyebrow: { icon: Camera, label: 'Capture' },
    headline: 'One tap. Everything saved.',
    body: 'Add a photo, title, link, description, date, mood and location in one quick flow. Your memory is safe before you even put your phone down.',
    reverse: false,
    gradientFrom: 'from-amber-100', gradientTo: 'to-orange-200',
  },
]

export default function FeatureShowcase() {
  return (
    <section id="how-it-works"
             className="max-w-[1160px] mx-auto px-8 flex flex-col gap-[90px] py-10 pb-20">
      {rows.map(({ eyebrow, headline, body, reverse, gradientFrom, gradientTo }) => (
        <div key={headline}
             className={`grid grid-cols-2 gap-16 items-center max-[767px]:grid-cols-1
                         ${reverse ? 'max-[767px]:flex-col-reverse' : ''}`}>

          {/* Text side */}
          <div className={`flex flex-col gap-5 ${reverse ? 'order-2 max-[767px]:order-1' : ''}`}>
            <div className="flex items-center gap-2">
              <eyebrow.icon className="w-4 h-4 text-brand" />
              <span className="font-accent font-semibold text-caption uppercase tracking-wider text-brand">
                {eyebrow.label}
              </span>
            </div>
            <h2 className="font-display font-bold text-h1 text-text-strong leading-snug">
              {headline}
            </h2>
            <p className="font-ui text-[1.0625rem] text-text-muted leading-relaxed">{body}</p>
            <a href="#" className="font-ui font-semibold text-body-sm text-brand hover:underline self-start">
              Learn more →
            </a>
          </div>

          {/* Image side */}
          <div className={`relative flex justify-center items-center ${reverse ? 'order-1 max-[767px]:order-2' : ''}`}>
            {/* Large card */}
            <div className={`w-64 h-44 rounded-[32px] shadow-lg bg-gradient-to-br ${gradientFrom} ${gradientTo} rotate-[-2deg]`} />
            {/* Smaller overlapping card */}
            <div className={`absolute bottom-[-16px] right-4 w-36 h-24 rounded-2xl shadow-md
                             bg-white border-[5px] border-white rotate-[3deg]
                             bg-gradient-to-br ${gradientFrom} ${gradientTo} opacity-80`} />
          </div>
        </div>
      ))}
    </section>
  )
}
