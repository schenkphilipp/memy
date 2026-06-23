import { createClient } from '@/lib/supabase/server'
import { redirect } from 'next/navigation'
import MapClient from './MapClient'

export default async function MapPage() {
  const supabase = await createClient()
  const { data: { user } } = await supabase.auth.getUser()
  if (!user) redirect('/auth/login')

  const { data: memys } = await supabase
    .from('memys')
    .select('id, name, location_lat, location_lng, location_label, date, photo_url')
    .eq('user_id', user.id)
    .not('location_lat', 'is', null)

  return <MapClient initialLocated={memys ?? []} />
}
