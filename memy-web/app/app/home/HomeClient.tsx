'use client'
import { useState, useMemo } from 'react'
import { Sparkles } from 'lucide-react'
import AppTopBar from '@/components/layout/AppTopBar'
import { FilterChip } from '@/components/ui/TagChip'
import MemyCard from '@/components/ui/MemyCard'
import DetailPanel from '@/components/ui/DetailPanel'
import EmptyState from '@/components/ui/EmptyState'
import type { Memy } from '@/types/database'

const FILTER_LABELS = ['All', 'Places', 'Notes', 'Links', 'Food']

// This is a client component that receives memys from a Server Component wrapper.
// The wrapper (HomePageServer) handles data fetching.
interface HomeClientProps { initialMemys: Memy[] }

export default function HomeClient({ initialMemys }: HomeClientProps) {
  const [memys]              = useState<Memy[]>(initialMemys)
  const [query, setQuery]    = useState('')
  const [filter, setFilter]  = useState('All')
  const [selected, setSelected] = useState<Memy | null>(null)

  const filtered = useMemo(() => {
    let list = memys
    if (query) {
      const q = query.toLowerCase()
      list = list.filter(m =>
        m.name.toLowerCase().includes(q) ||
        m.tags.some(t => t.toLowerCase().includes(q)) ||
        m.location_label?.toLowerCase().includes(q) ||
        m.description?.toLowerCase().includes(q)
      )
    }
    if (filter !== 'All') {
      if (filter === 'Places') list = list.filter(m => m.location_label)
      if (filter === 'Notes')  list = list.filter(m => m.description && !m.url)
      if (filter === 'Links')  list = list.filter(m => m.url)
      if (filter === 'Food')   list = list.filter(m => m.tags.some(t => t.toLowerCase().includes('food')))
    }
    return list
  }, [memys, query, filter])

  return (
    <>
      <AppTopBar title="Home" searchValue={query} onSearch={setQuery} />

      {/* Filter chips */}
      <div className="flex gap-2 px-6 py-3 border-b border-border-subtle overflow-x-auto shrink-0">
        {FILTER_LABELS.map(l => (
          <FilterChip key={l} label={l} selected={filter === l} onClick={() => setFilter(l)} />
        ))}
      </div>

      {/* Feed */}
      <div className="flex-1 overflow-y-auto px-6 py-6">
        {filtered.length === 0 ? (
          <EmptyState
            icon={Sparkles}
            title={query ? `No memys for "${query}"` : 'Your first memy awaits'}
            subtitle={query
              ? 'Try a different word, a place, or one of your tags.'
              : 'Save a place, a note, a link or a photo — anything worth remembering.'}
            ctaLabel="Add a memy"
            onCta={() => {}}
          />
        ) : (
          <div className="masonry-grid">
            {filtered.map(memy => (
              <MemyCard
                key={memy.id}
                memy={memy}
                onClick={() => setSelected(memy)}
              />
            ))}
          </div>
        )}
      </div>

      {/* Detail panel */}
      {selected && (
        <DetailPanel memy={selected} onClose={() => setSelected(null)} />
      )}
    </>
  )
}
