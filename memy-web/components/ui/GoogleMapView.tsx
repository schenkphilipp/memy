/// <reference types="@types/google.maps" />
'use client'
import { useEffect, useRef, useState } from 'react'
import { setOptions, importLibrary } from '@googlemaps/js-api-loader'
import { MapPin } from 'lucide-react'

setOptions({
  key: process.env.NEXT_PUBLIC_GOOGLE_MAPS_API_KEY ?? '',
  v: 'weekly',
})

export interface MapMemy {
  id: string
  name: string
  location_lat: number
  location_lng: number
  location_label: string | null
}

interface Props {
  memys: MapMemy[]
  onCapture: () => void
}

export default function GoogleMapView({ memys, onCapture }: Props) {
  const containerRef = useRef<HTMLDivElement>(null)
  const mapRef       = useRef<google.maps.Map | null>(null)
  const markersRef   = useRef<google.maps.marker.AdvancedMarkerElement[]>([])
  const infoWindowRef = useRef<google.maps.InfoWindow | null>(null)
  const MarkerCtorRef = useRef<typeof google.maps.marker.AdvancedMarkerElement | null>(null)
  const memysRef      = useRef(memys)
  const [mapReady, setMapReady] = useState(false)

  useEffect(() => { memysRef.current = memys }, [memys])

  // Initialise the map once
  useEffect(() => {
    if (!containerRef.current) return
    let destroyed = false

    Promise.all([
      importLibrary('maps'),
      importLibrary('marker'),
    ]).then(([{ Map, InfoWindow }, { AdvancedMarkerElement }]) => {
      if (destroyed || !containerRef.current) return

      const map = new Map(containerRef.current, {
        center:            { lat: 20, lng: 10 },
        zoom:              2,
        mapId:             'memy-map',
        mapTypeControl:    false,
        streetViewControl: false,
        fullscreenControl: false,
      })
      mapRef.current      = map
      infoWindowRef.current = new InfoWindow()
      MarkerCtorRef.current = AdvancedMarkerElement
      setMapReady(true)

      // Centre on user location when map is empty
      if (!memysRef.current.length) {
        navigator.geolocation?.getCurrentPosition(pos => {
          if (!destroyed) {
            map.setCenter({ lat: pos.coords.latitude, lng: pos.coords.longitude })
            map.setZoom(10)
          }
        })
      }
    })

    return () => {
      destroyed = true
      markersRef.current.forEach(m => { m.map = null })
      markersRef.current = []
      mapRef.current = null
    }
  }, [])

  // Rebuild markers whenever the memy list changes
  useEffect(() => {
    const map           = mapRef.current
    const AdvancedMarker = MarkerCtorRef.current
    const infoWindow    = infoWindowRef.current
    if (!mapReady || !map || !AdvancedMarker || !infoWindow) return

    // Clear existing markers
    markersRef.current.forEach(m => { m.map = null })
    markersRef.current = []

    if (memys.length === 0) return

    const bounds = new google.maps.LatLngBounds()

    memys.forEach(memy => {
      const position = { lat: memy.location_lat, lng: memy.location_lng }
      const marker = new AdvancedMarker({ map, position, title: memy.name })

      marker.addListener('click', () => {
        infoWindow.setContent(
          `<div style="font-family:ui-sans-serif,system-ui;padding:2px 4px">` +
          `<p style="font-weight:600;font-size:14px;margin:0 0 3px">${escapeHtml(memy.name)}</p>` +
          (memy.location_label
            ? `<p style="font-size:12px;color:#888;margin:0">${escapeHtml(memy.location_label)}</p>`
            : '') +
          `</div>`,
        )
        infoWindow.open({ map, anchor: marker })
      })

      markersRef.current.push(marker)
      bounds.extend(position)
    })

    if (memys.length === 1) {
      map.setCenter({ lat: memys[0].location_lat, lng: memys[0].location_lng })
      map.setZoom(13)
    } else {
      map.fitBounds(bounds, 80)
    }
  }, [memys, mapReady])

  return (
    <div className="relative w-full h-full">
      <div ref={containerRef} className="w-full h-full" />
      <button
        onClick={onCapture}
        className="absolute bottom-6 right-6 flex items-center gap-2 px-4 py-2.5
                   bg-surface-card rounded-full shadow-lg border border-border-subtle
                   font-ui text-body-sm font-semibold text-text-strong
                   hover:bg-surface-sunken transition-colors"
      >
        <MapPin className="w-4 h-4 text-brand" />
        Capture a place
      </button>
    </div>
  )
}

function escapeHtml(s: string) {
  return s
    .replace(/&/g, '&amp;')
    .replace(/</g, '&lt;')
    .replace(/>/g, '&gt;')
    .replace(/"/g, '&quot;')
}
