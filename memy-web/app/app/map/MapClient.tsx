'use client'
import { useState } from 'react'
import { MapPin } from 'lucide-react'
import AppTopBar from '@/components/layout/AppTopBar'
import EmptyState from '@/components/ui/EmptyState'

interface LocatedMemy {
  id: string
  name: string
  location_lat: number | null
  location_lng: number | null
  location_label: string | null
  date: string
  photo_url: string | null
}

interface MapClientProps { initialLocated: LocatedMemy[] }

export default function MapClient({ initialLocated }: MapClientProps) {
  const [query, setQuery] = useState('')

  const filtered = query
    ? initialLocated.filter(m =>
        m.name.toLowerCase().includes(query.toLowerCase()) ||
        m.location_label?.toLowerCase().includes(query.toLowerCase())
      )
    : initialLocated

  return (
    <>
      <AppTopBar title="Map" searchValue={query} onSearch={setQuery} />
      <div className="flex-1 relative overflow-hidden bg-[#F0EBE3]">
        {filtered.length === 0 ? (
          <div className="absolute inset-0 flex items-center justify-center">
            <EmptyState
              icon={MapPin}
              title={query ? `Nothing found for "${query}"` : 'Nothing on the map yet'}
              subtitle={query
                ? 'Try a different name or location.'
                : 'Pin a location when you save a memy and it'll show up here, right where it happened.'}
              ctaLabel="Capture a place"
              onCta={() => window.dispatchEvent(new Event('memy:open-add'))}
            />
          </div>
        ) : (
          <div className="absolute inset-0 flex items-center justify-center">
            <div className="text-center text-text-muted">
              <MapPin className="w-10 h-10 text-brand mx-auto mb-3" />
              <p className="font-ui text-body font-semibold text-text-strong">
                {filtered.length} pinned {filtered.length === 1 ? 'memory' : 'memories'}
              </p>
              <p className="font-ui text-body-sm text-text-muted mt-1">
                Add your Google Maps or Mapbox API key to render the map.
              </p>
              <div className="mt-4 text-left bg-surface-card rounded-xl p-4 max-w-sm mx-auto border border-border-subtle">
                <p className="font-ui text-caption font-semibold text-text-muted mb-2 uppercase tracking-wider">Pinned locations</p>
                {filtered.slice(0, 5).map(m => (
                  <div key={m.id} className="flex items-center gap-2 py-1.5 border-b border-border-subtle last:border-0">
                    <MapPin className="w-3.5 h-3.5 text-brand shrink-0" />
                    <span className="font-ui text-body-sm text-text-strong truncate">{m.name}</span>
                    <span className="font-ui text-caption text-text-muted ml-auto shrink-0">{m.location_label}</span>
                  </div>
                ))}
              </div>
            </div>
          </div>
        )}
      </div>
    </>
  )
}
