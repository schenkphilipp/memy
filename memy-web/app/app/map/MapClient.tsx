'use client'
import { useState } from 'react'
import dynamic from 'next/dynamic'
import { MapPin } from 'lucide-react'
import AppTopBar from '@/components/layout/AppTopBar'
import EmptyState from '@/components/ui/EmptyState'
import LocationPickerModal, { type PickedLocation } from '@/components/ui/LocationPickerModal'
import AddMemyModal from '@/components/ui/AddMemyModal'
import type { Memy } from '@/types/database'
import type { MapMemy } from '@/components/ui/GoogleMapView'

const GoogleMapView = dynamic(
  () => import('@/components/ui/GoogleMapView'),
  {
    ssr: false,
    loading: () => (
      <div className="w-full h-full flex items-center justify-center bg-surface-sunken">
        <MapPin className="w-6 h-6 text-brand animate-pulse" />
      </div>
    ),
  },
)

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
  const [located, setLocated]               = useState<LocatedMemy[]>(initialLocated)
  const [query, setQuery]                   = useState('')
  const [showPicker, setShowPicker]         = useState(false)
  const [pickedLocation, setPickedLocation] = useState<PickedLocation | null>(null)

  const filtered = query
    ? located.filter(m =>
        m.name.toLowerCase().includes(query.toLowerCase()) ||
        m.location_label?.toLowerCase().includes(query.toLowerCase()),
      )
    : located

  // Only pass memys with confirmed coordinates to the map
  const mapMemys: MapMemy[] = filtered.flatMap(m =>
    m.location_lat != null && m.location_lng != null
      ? [{ id: m.id, name: m.name, location_lat: m.location_lat, location_lng: m.location_lng, location_label: m.location_label }]
      : [],
  )

  function handlePickConfirm(loc: PickedLocation) {
    setPickedLocation(loc)
    setShowPicker(false)
  }

  function handleMemyAdded(memy: Memy) {
    if (memy.location_lat != null) {
      setLocated(prev => [...prev, {
        id: memy.id, name: memy.name,
        location_lat: memy.location_lat, location_lng: memy.location_lng,
        location_label: memy.location_label, date: memy.date, photo_url: memy.photo_url,
      }])
    }
    setPickedLocation(null)
  }

  const showEmpty = filtered.length === 0

  return (
    <>
      <AppTopBar title="Map" searchValue={query} onSearch={setQuery} />

      <div className="flex-1 relative overflow-hidden">
        {/* Real Google Map — always rendered */}
        <div className={`absolute inset-0 ${showEmpty ? 'pointer-events-none opacity-30' : ''}`}>
          <GoogleMapView
            memys={mapMemys}
            onCapture={() => setShowPicker(true)}
          />
        </div>

        {/* Empty state overlay */}
        {showEmpty && (
          <div className="absolute inset-0 flex items-center justify-center">
            <EmptyState
              icon={MapPin}
              title={query ? `Nothing found for "${query}"` : 'Nothing on the map yet'}
              subtitle={
                query
                  ? 'Try a different name or location.'
                  : `Pin a location when you save a memy and it'll show up here, right where it happened.`
              }
              ctaLabel="Capture a place"
              onCta={() => setShowPicker(true)}
            />
          </div>
        )}
      </div>

      {showPicker && (
        <LocationPickerModal
          onClose={() => setShowPicker(false)}
          onConfirm={handlePickConfirm}
        />
      )}

      {pickedLocation && (
        <AddMemyModal
          onClose={() => setPickedLocation(null)}
          onAdded={handleMemyAdded}
          initialLocation={pickedLocation}
        />
      )}
    </>
  )
}
