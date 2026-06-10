import { createClient } from '@/lib/supabase/server'
import { redirect } from 'next/navigation'
import HomeClient from './HomeClient'

export default async function HomePage() {
  const supabase = await createClient()
  const { data: { user } } = await supabase.auth.getUser()
  if (!user) redirect('/auth/login')

  const { data: memys } = await supabase
    .from('memys')
    .select('*')
    .eq('user_id', user.id)
    .order('created_at', { ascending: false })

  return <HomeClient initialMemys={memys ?? []} />
}
