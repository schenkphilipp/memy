import { createClient } from '@/lib/supabase/server'
import { redirect } from 'next/navigation'
import CollectionsClient from './CollectionsClient'

export default async function CollectionsPage() {
  const supabase = await createClient()
  const { data: { user } } = await supabase.auth.getUser()
  if (!user) redirect('/auth/login')

  const [{ data: collections }, { data: memyCovers }] = await Promise.all([
    supabase.from('collections').select('*').eq('user_id', user.id).order('created_at'),
    supabase.from('memys')
      .select('photo_url, collection_ids')
      .eq('user_id', user.id)
      .not('photo_url', 'is', null)
      .order('created_at', { ascending: false }),
  ])

  return (
    <CollectionsClient
      initialCollections={collections ?? []}
      memyCovers={memyCovers ?? []}
    />
  )
}
