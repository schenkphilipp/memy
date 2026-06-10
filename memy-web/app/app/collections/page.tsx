import { createClient } from '@/lib/supabase/server'
import { redirect } from 'next/navigation'
import Link from 'next/link'
import { Grid } from 'lucide-react'
import CollectionCard from '@/components/ui/CollectionCard'
import EmptyState from '@/components/ui/EmptyState'
import AppTopBar from '@/components/layout/AppTopBar'

export default async function CollectionsPage() {
  const supabase = await createClient()
  const { data: { user } } = await supabase.auth.getUser()
  if (!user) redirect('/auth/login')

  const { data: collections } = await supabase
    .from('collections')
    .select('*')
    .eq('user_id', user.id)
    .order('created_at')

  const list = collections ?? []

  return (
    <>
      <AppTopBar title="Collections" searchValue="" onSearch={() => {}} />
      <div className="flex-1 overflow-y-auto px-6 py-6">
        {list.length === 0 ? (
          <EmptyState
            icon={Grid}
            title="No collections yet"
            subtitle="Group memys into albums — Trips, Good eats, Reading. Make your first one and start sorting."
            ctaLabel="New collection"
            onCta={() => {}}
          />
        ) : (
          <div className="grid grid-cols-3 gap-6 max-[1099px]:grid-cols-2 max-[767px]:grid-cols-1">
            {list.map(col => (
              <Link key={col.id} href={`/app/collections/${col.id}`}>
                <CollectionCard collection={col} onClick={() => {}} />
              </Link>
            ))}
          </div>
        )}
      </div>
    </>
  )
}
