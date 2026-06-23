/// <reference types="@types/google.maps" />
'use client'
import { useEffect, useRef } from 'react'
import { setOptions, importLibrary } from '@googlemaps/js-api-loader'

setOptions({
  key: process.env.NEXT_PUBLIC_GOOGLE_MAPS_API_KEY ?? '',
  v: 'weekly',
})

interface Props {
  onPick: (loc: { lat: number; lng: number; label: string }) => void
}

export default function GoogleMapPicker({ onPick }: Props) {
  const containerRef = useRef<HTMLDivElement>(null)
  const markerRef    = useRef<google.maps.marker.AdvancedMarkerElement | null>(null)
  const onPickRef    = useRef(onPick)
  useEffect(() => { onPickRef.current = onPick }, [onPick])

  useEffect(() => {
    if (!containerRef.current) return
    let destroyed = false

    Promise.all([
      importLibrary('maps'),
      importLibrary('marker'),
      importLibrary('geocoding'),
    ]).then(([{ Map }, { AdvancedMarkerElement }, { Geocoder }]) => {
      if (destroyed || !containerRef.current) return

      const map = new Map(containerRef.current, {
        center:            { lat: 48.8566, lng: 2.3522 },
        zoom:              4,
        mapId:             'location-picker',
        mapTypeControl:    false,
        streetViewControl: false,
        fullscreenControl: false,
      })

      const geocoder = new Geocoder()

      map.addListener('click', (e: google.maps.MapMouseEvent) => {
        const latLng = e.latLng
        if (!latLng) return
        const lat = latLng.lat()
        const lng = latLng.lng()

        if (markerRef.current) markerRef.current.map = null
        markerRef.current = new AdvancedMarkerElement({ position: { lat, lng }, map })

        geocoder.geocode({ location: { lat, lng } }, (results, status) => {
          const label =
            status === 'OK' && results?.[0]
              ? results[0].formatted_address
              : `${lat.toFixed(5)}, ${lng.toFixed(5)}`
          if (!destroyed) onPickRef.current({ lat, lng, label })
        })
      })

      navigator.geolocation?.getCurrentPosition(pos => {
        if (!destroyed) {
          map.setCenter({ lat: pos.coords.latitude, lng: pos.coords.longitude })
          map.setZoom(13)
        }
      })
    })

    return () => {
      destroyed = true
      if (markerRef.current) markerRef.current.map = null
      markerRef.current = null
    }
  }, [])

  return <div ref={containerRef} className="w-full h-full" />
}
