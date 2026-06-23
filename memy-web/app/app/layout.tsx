import { redirect } from 'next/navigation'
import { createClient } from '@/lib/supabase/server'
import AppSidebarWrapper from '@/components/layout/AppSidebarWrapper'

export default async function AppLayout({ children }: { children: React.ReactNode }) {
  const supabase = await createClient()
  const { data: { user } } = await supabase.auth.getUser()
  if (!user) redirect('/auth/login')

  const [profileRes, memysRes, collectionsRes] = await Promise.all([
    supabase.from('profiles').select('*').eq('id', user.id).single(),
    supabase.from('memys').select('id, collection_ids').eq('user_id', user.id),
    supabase.from('collections').select('*').eq('user_id', user.id).order('created_at'),
  ])

  const profile   = profileRes.data
  const memyCount = memysRes.data?.length ?? 0
  const memys     = memysRes.data ?? []

  const countById = memys.reduce<Record<string, number>>((acc, m) => {
    m.collection_ids?.forEach(id => { acc[id] = (acc[id] ?? 0) + 1 })
    return acc
  }, {})

  const collections = (collectionsRes.data ?? []).map(col => ({
    ...col,
    memy_count: countById[col.id] ?? 0,
  }))

  return (
    <div className="flex h-screen bg-surface-page overflow-hidden">
      {/* Sidebar — hidden below 768px (bottom nav would replace it; add that for mobile) */}
      <div className="hidden md:flex">
        <AppSidebarWrapper
          profile={profile}
          collections={collections}
          memyCount={memyCount}
        />
      </div>
      <main className="flex-1 overflow-hidden flex flex-col">
        {children}
      </main>
    </div>
  )
}
