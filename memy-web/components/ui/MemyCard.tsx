import Image from 'next/image'
import { MapPin } from 'lucide-react'
import { TagChip } from './TagChip'
import { formatDate } from '@/lib/utils'
import type { Memy } from '@/types/database'

interface MemyCardProps {
  memy: Memy
  onClick: () => void
}

export default function MemyCard({ memy, onClick }: MemyCardProps) {
  return (
    <div
      onClick={onClick}
      className="group bg-surface-card rounded-lg shadow-sm cursor-pointer
                 hover:shadow-md transition-shadow duration-220 overflow-hidden animate-fade-rise-sm"
    >
      {/* Photo */}
      <div className="relative w-full bg-surface-sunken overflow-hidden"
           style={{ paddingBottom: '65%' }}>
        {memy.photo_url ? (
          <Image
            src={memy.photo_url}
            alt={memy.name}
            fill
            className="object-cover group-hover:scale-[1.02] transition-transform duration-360"
            sizes="(max-width: 768px) 100vw, (max-width: 1100px) 50vw, 33vw"
          />
        ) : (
          <div className="absolute inset-0 bg-surface-sunken" />
        )}
        {/* Mood chip */}
        {memy.mood && (
          <span className="absolute top-2 right-2 w-7 h-7 rounded-full
                           bg-white/85 flex items-center justify-center text-sm shadow-xs">
            {memy.mood}
          </span>
        )}
      </div>

      {/* Content */}
      <div className="p-3 space-y-1.5">
        <p className="font-ui font-semibold text-field text-text-strong leading-snug line-clamp-2">
          {memy.name}
        </p>
        <div className="flex items-center gap-1 text-[11.5px] text-text-muted font-ui">
          {memy.location_label && (
            <>
              <MapPin className="w-3 h-3 shrink-0" />
              <span>{memy.location_label}</span>
              <span>·</span>
            </>
          )}
          <span>{formatDate(memy.date)}</span>
        </div>
        {memy.tags.length > 0 && (
          <div className="flex flex-wrap gap-1 pt-0.5">
            {memy.tags.slice(0, 2).map(tag => (
              <TagChip key={tag} tag={tag} />
            ))}
          </div>
        )}
      </div>
    </div>
  )
}
