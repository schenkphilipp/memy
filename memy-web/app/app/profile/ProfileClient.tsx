'use client'
import { Settings, Bell, Lock, HelpCircle, ChevronRight } from 'lucide-react'
import AppTopBar from '@/components/layout/AppTopBar'
import type { Profile } from '@/types/database'

const SETTINGS = [
  { icon: Settings,   label: 'Settings' },
  { icon: Bell,       label: 'Notifications' },
  { icon: Lock,       label: 'Privacy' },
  { icon: HelpCircle, label: 'Help & feedback' },
]

interface ProfileClientProps {
  profile: Profile | null
  memyCount: number
  colCount: number
}

export default function ProfileClient({ profile, memyCount, colCount }: ProfileClientProps) {
  return (
    <>
      <AppTopBar title="Profile" searchValue="" onSearch={() => {}} />
      <div className="flex-1 overflow-y-auto">
        {/* Coral header */}
        <div className="relative bg-brand texture-overlay wave-bottom h-36" />

        <div className="px-8 pt-4 pb-12 max-w-xl">
          {/* Avatar */}
          <div className="w-[88px] h-[88px] rounded-full bg-surface-card border-4 border-surface-card
                          flex items-center justify-center -mt-10 mb-4 shadow-sm">
            <span className="font-display font-bold text-h1 text-brand">
              {profile?.name?.[0]?.toUpperCase() ?? '?'}
            </span>
          </div>

          <h2 className="font-display font-bold text-h2 text-text-strong">{profile?.name}</h2>
          <p className="font-ui text-body-sm text-text-muted mt-1">
            Keeping {memyCount} memys across {colCount} collections
          </p>

          {/* Stats */}
          <div className="flex gap-8 mt-5 mb-8">
            <StatItem value={String(memyCount)} label="Memys" />
            <StatItem value={String(colCount)}  label="Collections" />
          </div>

          {/* Settings list */}
          <div className="border border-border-subtle rounded-xl overflow-hidden">
            {SETTINGS.map(({ icon: Icon, label }, i) => (
              <div key={label}>
                {i > 0 && <div className="h-px bg-border-subtle" />}
                <button className="w-full flex items-center gap-4 px-5 py-4
                                   hover:bg-surface-sunken transition-colors">
                  <Icon className="w-5 h-5 text-text-body" />
                  <span className="font-ui text-body text-text-strong flex-1 text-left">{label}</span>
                  <ChevronRight className="w-4 h-4 text-text-subtle" />
                </button>
              </div>
            ))}
          </div>
        </div>
      </div>
    </>
  )
}

function StatItem({ value, label }: { value: string; label: string }) {
  return (
    <div>
      <p className="font-display font-bold text-h2 text-text-strong">{value}</p>
      <p className="font-ui text-caption text-text-muted">{label}</p>
    </div>
  )
}
