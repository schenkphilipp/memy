'use client'
import Link from 'next/link'
import { usePathname } from 'next/navigation'
import { Home, Grid, Map, Tag, Plus, Settings } from 'lucide-react'
import { cn } from '@/lib/utils'
import type { Profile, Collection } from '@/types/database'

const navItems = [
  { href: '/app/home',        label: 'Home',        icon: Home },
  { href: '/app/collections', label: 'Collections', icon: Grid },
  { href: '/app/map',         label: 'Map',         icon: Map },
  { href: '/app/tags',        label: 'Tags',        icon: Tag },
]

interface SidebarProps {
  profile: Profile | null
  collections: Collection[]
  memyCount: number
  onNewMemy: () => void
}

export default function AppSidebar({ profile, collections, memyCount, onNewMemy }: SidebarProps) {
  const pathname = usePathname()

  return (
    <aside className="w-[248px] shrink-0 bg-surface-card border-r border-border-subtle
                      flex flex-col h-screen sticky top-0 overflow-y-auto">
      {/* Logo */}
      <div className="flex items-center gap-2 px-5 h-16 shrink-0">
        <div className="w-8 h-8 rounded-xl bg-brand flex items-center justify-center shadow-coral">
          <span className="font-display font-bold text-on-brand text-lg leading-none">m</span>
        </div>
        <span className="font-display font-bold text-h3 text-text-strong">memy</span>
        <span className="w-1.5 h-1.5 rounded-full bg-brand mb-0.5 self-end" />
      </div>

      {/* New memy CTA */}
      <div className="px-4 pb-4 shrink-0">
        <button
          onClick={onNewMemy}
          className="w-full h-[49px] rounded-md bg-brand text-on-brand font-ui font-bold
                     text-body-sm flex items-center justify-center gap-2 shadow-coral
                     hover:bg-brand-strong active:scale-[0.97] transition-all duration-140"
        >
          <Plus className="w-4 h-4" />
          New memy
        </button>
      </div>

      {/* Nav */}
      <nav className="px-3 space-y-1 shrink-0">
        {navItems.map(({ href, label, icon: Icon }) => {
          const active = pathname === href
          return (
            <Link
              key={href}
              href={href}
              className={cn(
                'flex items-center gap-3 px-3 h-10 rounded-md text-body-sm font-ui transition-colors duration-140',
                active
                  ? 'bg-brand-wash text-brand-strong font-semibold'
                  : 'text-text-body hover:bg-surface-sunken',
              )}
            >
              <Icon className={cn('w-4 h-4', active ? 'text-brand' : 'text-text-muted')} />
              {label}
            </Link>
          )
        })}
      </nav>

      {/* Collections list */}
      {collections.length > 0 && (
        <div className="mt-6 px-4 shrink-0">
          <p className="text-caption font-accent font-medium text-text-subtle uppercase tracking-wider mb-2 px-1">
            Collections
          </p>
          <div className="space-y-0.5">
            {collections.slice(0, 8).map(col => (
              <Link
                key={col.id}
                href={`/app/collections/${col.id}`}
                className="flex items-center gap-2 px-2 py-1.5 rounded-md text-body-sm font-ui
                           text-text-body hover:bg-surface-sunken transition-colors"
              >
                <span className="text-sm">📁</span>
                <span className="truncate flex-1">{col.name}</span>
                <span className="text-caption text-text-subtle">{col.memy_count}</span>
              </Link>
            ))}
          </div>
        </div>
      )}

      {/* Account footer */}
      <div className="mt-auto border-t border-border-subtle px-4 py-3 flex items-center gap-3 shrink-0">
        <div className="w-8 h-8 rounded-full bg-brand-wash flex items-center justify-center shrink-0">
          <span className="font-display font-bold text-sm text-brand">
            {profile?.name?.[0]?.toUpperCase() ?? '?'}
          </span>
        </div>
        <div className="flex-1 min-w-0">
          <p className="font-ui font-semibold text-field text-text-strong truncate">{profile?.name}</p>
          <p className="text-caption text-text-muted">{memyCount} memys</p>
        </div>
        <Link href="/app/profile">
          <Settings className="w-4 h-4 text-text-muted hover:text-text-body transition-colors" />
        </Link>
      </div>
    </aside>
  )
}
