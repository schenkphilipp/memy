import { Zap, Grid, Map, Clock } from 'lucide-react'

const props = [
  {
    icon: Zap,
    title: 'Capture in a tap',
    body: 'Save a place, note, link or photo in seconds — before the moment slips away.',
  },
  {
    icon: Grid,
    title: 'Organise gently',
    body: 'Group memys into collections like Trips or Good eats. No rigid folders.',
  },
  {
    icon: Map,
    title: 'See where it happened',
    body: 'Every memory with a location lands on your personal map, beautifully.',
  },
  {
    icon: Clock,
    title: 'Revisit anytime',
    body: 'Search, browse, or scroll back through everything you\'ve captured.',
  },
]

export default function ValueProps() {
  return (
    <section id="features" className="max-w-[1160px] mx-auto px-8 py-20">
      <div className="grid grid-cols-4 gap-6 max-[1099px]:grid-cols-2 max-[767px]:grid-cols-1">
        {props.map(({ icon: Icon, title, body }) => (
          <div key={title}
               className="bg-surface-card rounded-xl shadow-sm border border-border-subtle p-7">
            <div className="w-[50px] h-[50px] rounded-2xl bg-brand-wash flex items-center justify-center mb-4">
              <Icon className="w-6 h-6 text-brand" />
            </div>
            <h3 className="font-display font-bold text-h3 text-text-strong mb-2">{title}</h3>
            <p className="font-ui text-[14.5px] text-text-muted leading-relaxed">{body}</p>
          </div>
        ))}
      </div>
    </section>
  )
}
