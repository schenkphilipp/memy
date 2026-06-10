import { LucideIcon } from 'lucide-react'
import Button from './Button'

interface EmptyStateProps {
  icon: LucideIcon
  title: string
  subtitle: string
  ctaLabel: string
  onCta: () => void
}

export default function EmptyState({ icon: Icon, title, subtitle, ctaLabel, onCta }: EmptyStateProps) {
  return (
    <div className="flex flex-col items-center text-center px-10 py-16 animate-fade-rise">
      <div className="w-[92px] h-[92px] rounded-full bg-brand-wash flex items-center justify-center mb-6">
        <Icon className="w-10 h-10 text-brand" />
      </div>
      <h3 className="font-display font-bold text-h3 text-text-strong mb-2">{title}</h3>
      <p className="font-ui text-body-sm text-text-muted max-w-xs leading-relaxed mb-7">{subtitle}</p>
      <Button variant="primary" onClick={onCta} className="min-w-[180px]">{ctaLabel}</Button>
    </div>
  )
}
