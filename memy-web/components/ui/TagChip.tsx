import { cn } from '@/lib/utils'

export function TagChip({ tag, className }: { tag: string; className?: string }) {
  return (
    <span className={cn(
      'inline-flex items-center px-2 py-0.5 rounded-full',
      'bg-brand-wash text-brand-strong font-ui font-semibold text-[11.5px]',
      className
    )}>
      #{tag}
    </span>
  )
}

interface FilterChipProps {
  label: string
  selected: boolean
  onClick: () => void
}

export function FilterChip({ label, selected, onClick }: FilterChipProps) {
  return (
    <button
      onClick={onClick}
      className={cn(
        'h-8 px-4 rounded-full border text-field font-ui transition-all duration-140',
        'active:scale-[0.97]',
        selected
          ? 'bg-brand-wash border-brand text-brand-strong font-semibold'
          : 'bg-transparent border-border-subtle text-text-muted hover:border-border-default',
      )}
    >
      {label}
    </button>
  )
}
