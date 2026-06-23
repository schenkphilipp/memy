import { createClient } from '@/lib/supabase/server'
import { redirect } from 'next/navigation'
import ProfileClient from './ProfileClient'

export default async function ProfilePage() {
  const supabase = await createClient()
  const { data: { user } } = await supabase.auth.getUser()
  if (!user) redirect('/auth/login')

  const [profileRes, memysRes, collectionsRes] = await Promise.all([
    supabase.from('profiles').select('*').eq('id', user.id).single(),
    supabase.from('memys').select('id', { count: 'exact', head: true }).eq('user_id', user.id),
    supabase.from('collections').select('id', { count: 'exact', head: true }).eq('user_id', user.id),
  ])

  return (
    <ProfileClient
      profile={profileRes.data}
      memyCount={memysRes.count ?? 0}
      colCount={collectionsRes.count ?? 0}
    />
  )
}
