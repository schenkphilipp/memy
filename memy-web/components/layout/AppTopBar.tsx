'use client'
import { Search, SlidersHorizontal } from 'lucide-react'

interface AppTopBarProps {
  title: string
  searchValue: string
  onSearch: (v: string) => void
}

export default function AppTopBar({ title, searchValue, onSearch }: AppTopBarProps) {
  return (
    <header className="h-16 border-b border-border-subtle bg-surface-card
                       flex items-center px-6 gap-6 sticky top-0 z-10 shrink-0">
      <h1 className="font-display font-bold text-h2 text-text-strong shrink-0">{title}</h1>
      <div className="flex-1 max-w-xs relative">
        <Search className="absolute left-3 top-1/2 -translate-y-1/2 w-4 h-4 text-text-muted" />
        <input
          type="search"
          placeholder="Search memys…"
          value={searchValue}
          onChange={e => onSearch(e.target.value)}
          className="w-full h-10 pl-9 pr-3 bg-surface-sunken rounded-full text-field font-ui
                     text-text-strong placeholder:text-text-subtle
                     focus:outline-none focus:ring-2 focus:ring-brand/30 transition-shadow"
        />
      </div>
      <button className="w-9 h-9 rounded-md border border-border-subtle flex items-center justify-center
                         text-text-muted hover:bg-surface-sunken transition-colors ml-auto">
        <SlidersHorizontal className="w-4 h-4" />
      </button>
    </header>
  )
}
