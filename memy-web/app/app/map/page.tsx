import { createClient } from '@/lib/supabase/server'
import { redirect } from 'next/navigation'
import { MapPin } from 'lucide-react'
import EmptyState from '@/components/ui/EmptyState'
import AppTopBar from '@/components/layout/AppTopBar'

export default async function MapPage() {
  const supabase = await createClient()
  const { data: { user } } = await supabase.auth.getUser()
  if (!user) redirect('/auth/login')

  const { data: memys } = await supabase
    .from('memys')
    .select('id, name, location_lat, location_lng, location_label, date, photo_url')
    .eq('user_id', user.id)
    .not('location_lat', 'is', null)

  const located = memys ?? []

  return (
    <>
      <AppTopBar title="Map" searchValue="" onSearch={() => {}} />
      <div className="flex-1 relative overflow-hidden bg-[#F0EBE3]">
        {located.length === 0 ? (
          <div className="absolute inset-0 flex items-center justify-center">
            <EmptyState
              icon={MapPin}
              title="Nothing on the map yet"
              subtitle="Pin a location when you save a memy and it'll show up here, right where it happened."
              ctaLabel="Capture a place"
              onCta={() => {}}
            />
          </div>
        ) : (
          <div className="absolute inset-0 flex items-center justify-center">
            {/* 
              Replace this placeholder with a real map.
              Recommended options:
                - Google Maps JS API: https://developers.google.com/maps/documentation/javascript
                - Mapbox GL JS: https://docs.mapbox.com/mapbox-gl-js
              
              Pass `located` array with lat/lng coordinates as markers.
              Style the map with a warm, low-contrast theme matching the design spec.
            */}
            <div className="text-center text-text-muted">
              <MapPin className="w-10 h-10 text-brand mx-auto mb-3" />
              <p className="font-ui text-body font-semibold text-text-strong">
                {located.length} pinned {located.length === 1 ? 'memory' : 'memories'}
              </p>
              <p className="font-ui text-body-sm text-text-muted mt-1">
                Add your Google Maps or Mapbox API key to render the map.
              </p>
              <div className="mt-4 text-left bg-surface-card rounded-xl p-4 max-w-sm mx-auto border border-border-subtle">
                <p className="font-ui text-caption font-semibold text-text-muted mb-2 uppercase tracking-wider">Pinned locations</p>
                {located.slice(0, 5).map(m => (
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
