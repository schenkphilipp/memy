import { createClient } from '@/lib/supabase/server'
import { redirect, notFound } from 'next/navigation'
import CollectionDetailClient from './CollectionDetailClient'

interface Props { params: Promise<{ id: string }> }

export default async function CollectionDetailPage({ params }: Props) {
  const { id } = await params
  const supabase = await createClient()
  const { data: { user } } = await supabase.auth.getUser()
  if (!user) redirect('/auth/login')

  const [collectionRes, memysRes] = await Promise.all([
    supabase
      .from('collections')
      .select('*')
      .eq('id', id)
      .eq('user_id', user.id)
      .single(),
    supabase
      .from('memys')
      .select('*')
      .eq('user_id', user.id)
      .contains('collection_ids', [id])
      .order('created_at', { ascending: false }),
  ])

  if (!collectionRes.data) notFound()

  return (
    <CollectionDetailClient
      collection={collectionRes.data}
      initialMemys={memysRes.data ?? []}
    />
  )
}
