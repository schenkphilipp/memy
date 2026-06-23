'use client'
import { useState, useMemo } from 'react'
import Link from 'next/link'
import { Grid, Plus } from 'lucide-react'
import AppTopBar from '@/components/layout/AppTopBar'
import CollectionCard from '@/components/ui/CollectionCard'
import EmptyState from '@/components/ui/EmptyState'
import AddCollectionModal from '@/components/ui/AddCollectionModal'
import type { Collection } from '@/types/database'

interface MemyCover { photo_url: string | null; collection_ids: string[] }
interface CollectionsClientProps {
  initialCollections: Collection[]
  memyCovers: MemyCover[]
}

export default function CollectionsClient({ initialCollections, memyCovers }: CollectionsClientProps) {
  const [collections, setCollections] = useState<Collection[]>(initialCollections)
  const [query, setQuery]             = useState('')
  const [showAdd, setShowAdd]         = useState(false)

  const filtered = useMemo(() => {
    if (!query) return collections
    const q = query.toLowerCase()
    return collections.filter(c => c.name.toLowerCase().includes(q))
  }, [collections, query])

  return (
    <>
      <AppTopBar title="Collections" searchValue={query} onSearch={setQuery} />
      <div className="flex-1 overflow-y-auto px-6 py-6 flex flex-col gap-6">
        {filtered.length === 0 ? (
          <EmptyState
            icon={Grid}
            title={query ? `No collections for "${query}"` : 'No collections yet'}
            subtitle={query
              ? 'Try a different name.'
              : 'Group memys into albums — Trips, Good eats, Reading. Make your first one and start sorting.'}
            ctaLabel="New collection"
            onCta={() => setShowAdd(true)}
          />
        ) : (
          <div className="grid grid-cols-3 gap-6 max-[1099px]:grid-cols-2 max-[767px]:grid-cols-1">
            {filtered.map(col => {
              const covers = memyCovers
                .filter(m => m.collection_ids.includes(col.id) && m.photo_url)
                .slice(0, 4)
                .map(m => m.photo_url as string)
              return (
                <Link key={col.id} href={`/app/collections/${col.id}`}>
                  <CollectionCard collection={col} coverUrls={covers} onClick={() => {}} />
                </Link>
              )
            })}
          </div>
        )}

        {filtered.length > 0 && (
          <button
            onClick={() => setShowAdd(true)}
            className="flex items-center gap-2 self-start px-4 py-2 rounded-md border border-dashed border-border-subtle
                       font-ui text-body-sm text-text-muted hover:border-brand hover:text-brand transition-colors duration-140"
          >
            <Plus className="w-4 h-4" />
            New collection
          </button>
        )}
      </div>

      {showAdd && (
        <AddCollectionModal
          onClose={() => setShowAdd(false)}
          onAdded={col => setCollections(prev => [...prev, col])}
        />
      )}
    </>
  )
}
