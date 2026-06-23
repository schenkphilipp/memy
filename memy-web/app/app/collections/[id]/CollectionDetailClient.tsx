'use client'
import { useState, useMemo } from 'react'
import { ArrowLeft } from 'lucide-react'
import Link from 'next/link'
import AppTopBar from '@/components/layout/AppTopBar'
import MemyCard from '@/components/ui/MemyCard'
import DetailPanel from '@/components/ui/DetailPanel'
import EditMemyModal from '@/components/ui/EditMemyModal'
import EmptyState from '@/components/ui/EmptyState'
import { Sparkles } from 'lucide-react'
import type { Collection, Memy } from '@/types/database'

interface CollectionDetailClientProps {
  collection: Collection
  initialMemys: Memy[]
}

export default function CollectionDetailClient({ collection, initialMemys }: CollectionDetailClientProps) {
  const [memys, setMemys]       = useState<Memy[]>(initialMemys)
  const [query, setQuery]       = useState('')
  const [selected, setSelected] = useState<Memy | null>(null)
  const [editing, setEditing]   = useState<Memy | null>(null)

  const filtered = useMemo(() => {
    if (!query) return memys
    const q = query.toLowerCase()
    return memys.filter(m =>
      m.name.toLowerCase().includes(q) ||
      m.tags.some(t => t.toLowerCase().includes(q)) ||
      m.location_label?.toLowerCase().includes(q) ||
      m.description?.toLowerCase().includes(q)
    )
  }, [memys, query])

  return (
    <>
      <AppTopBar title={collection.name} searchValue={query} onSearch={setQuery} />

      <div className="flex-1 overflow-y-auto px-6 py-6">
        <Link
          href="/app/collections"
          className="inline-flex items-center gap-1.5 text-caption font-ui text-text-muted hover:text-brand transition-colors mb-6"
        >
          <ArrowLeft className="w-3.5 h-3.5" />
          All collections
        </Link>

        {filtered.length === 0 ? (
          <EmptyState
            icon={Sparkles}
            title={query ? `No memys for "${query}"` : 'No memys in this collection'}
            subtitle={query
              ? 'Try a different word.'
              : 'Add memys to this collection when saving them.'}
            ctaLabel="Add a memy"
            onCta={() => window.dispatchEvent(new Event('memy:open-add'))}
          />
        ) : (
          <div className="masonry-grid">
            {filtered.map(memy => (
              <MemyCard key={memy.id} memy={memy} onClick={() => setSelected(memy)} />
            ))}
          </div>
        )}
      </div>

      {selected && (
        <DetailPanel
          memy={selected}
          onClose={() => setSelected(null)}
          onEdit={() => { setEditing(selected); setSelected(null) }}
          onDeleted={id => { setMemys(prev => prev.filter(m => m.id !== id)); setSelected(null) }}
        />
      )}

      {editing && (
        <EditMemyModal
          memy={editing}
          onClose={() => setEditing(null)}
          onSaved={updated => {
            setMemys(prev => prev.map(m => m.id === updated.id ? updated : m))
            setEditing(null)
          }}
        />
      )}
    </>
  )
}
