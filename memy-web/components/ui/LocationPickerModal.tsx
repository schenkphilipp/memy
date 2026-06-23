'use client'
import { useState } from 'react'
import dynamic from 'next/dynamic'
import { X, MapPin } from 'lucide-react'
import Button from './Button'

const LeafletMapPicker = dynamic(() => import('./LeafletMapPicker'), {
  ssr: false,
  loading: () => (
    <div className="w-full h-full flex items-center justify-center bg-surface-sunken">
      <MapPin className="w-6 h-6 text-brand animate-pulse" />
    </div>
  ),
})

export interface PickedLocation { lat: number; lng: number; label: string }

interface LocationPickerModalProps {
  onClose: () => void
  onConfirm: (location: PickedLocation) => void
}

export default function LocationPickerModal({ onClose, onConfirm }: LocationPickerModalProps) {
  const [picked, setPicked] = useState<PickedLocation | null>(null)

  return (
    <>
      <div className="fixed inset-0 bg-black/20 z-30" onClick={onClose} />
      <div className="fixed inset-0 z-40 flex items-center justify-center p-4">
        <div
          className="bg-surface-card rounded-2xl shadow-lg w-full max-w-2xl flex flex-col overflow-hidden animate-scale-in"
          style={{ height: '70vh' }}
        >
          {/* Header */}
          <div className="flex items-center justify-between px-6 pt-5 pb-4 border-b border-border-subtle shrink-0">
            <div>
              <h2 className="font-display font-bold text-h3 text-text-strong">Pick a location</h2>
              <p className="font-ui text-caption text-text-muted mt-0.5">Tap anywhere on the map to drop a pin</p>
            </div>
            <button
              onClick={onClose}
              className="w-8 h-8 rounded-full flex items-center justify-center hover:bg-surface-sunken transition-colors"
              aria-label="Close"
            >
              <X className="w-4 h-4 text-text-muted" />
            </button>
          </div>

          {/* Map */}
          <div className="flex-1 relative">
            <LeafletMapPicker onPick={setPicked} />
          </div>

          {/* Footer — appears once a pin is dropped */}
          <div className={`border-t border-border-subtle px-6 py-4 flex items-center gap-4 shrink-0 transition-all ${picked ? 'opacity-100' : 'opacity-0 pointer-events-none'}`}>
            <MapPin className="w-4 h-4 text-brand shrink-0" />
            <span className="font-ui text-body-sm text-text-body flex-1 truncate">
              {picked?.label ?? ''}
            </span>
            <Button variant="secondary" onClick={onClose} size="sm">Cancel</Button>
            <Button variant="primary" size="sm" onClick={() => picked && onConfirm(picked)}>
              Confirm location
            </Button>
          </div>
        </div>
      </div>
    </>
  )
}
