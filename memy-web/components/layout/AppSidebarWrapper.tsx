'use client'
import AppSidebar from './AppSidebar'
import type { Profile, Collection } from '@/types/database'

interface Props {
  profile: Profile | null
  collections: Collection[]
  memyCount: number
}

export default function AppSidebarWrapper({ profile, collections, memyCount }: Props) {
  function onNewMemy() {
    window.dispatchEvent(new Event('memy:open-add'))
  }

  return (
    <AppSidebar
      profile={profile}
      collections={collections}
      memyCount={memyCount}
      onNewMemy={onNewMemy}
    />
  )
}
