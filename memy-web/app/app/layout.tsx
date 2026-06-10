import { redirect } from 'next/navigation'
import { createClient } from '@/lib/supabase/server'
import AppSidebar from '@/components/layout/AppSidebar'

export default async function AppLayout({ children }: { children: React.ReactNode }) {
  const supabase = await createClient()
  const { data: { user } } = await supabase.auth.getUser()
  if (!user) redirect('/auth/login')

  const [profileRes, memysRes, collectionsRes] = await Promise.all([
    supabase.from('profiles').select('*').eq('id', user.id).single(),
    supabase.from('memys').select('id').eq('user_id', user.id),
    supabase.from('collections').select('*').eq('user_id', user.id).order('created_at'),
  ])

  const profile     = profileRes.data
  const memyCount   = memysRes.data?.length ?? 0
  const collections = collectionsRes.data ?? []

  return (
    <div className="flex h-screen bg-surface-page overflow-hidden">
      {/* Sidebar — hidden below 768px (bottom nav would replace it; add that for mobile) */}
      <div className="hidden md:flex">
        <AppSidebar
          profile     = {profile}
          collections = {collections}
          memyCount   = {memyCount}
          onNewMemy   = {() => {}}  // wired per-page via client component
        />
      </div>
      <main className="flex-1 overflow-hidden flex flex-col">
        {children}
      </main>
    </div>
  )
}
