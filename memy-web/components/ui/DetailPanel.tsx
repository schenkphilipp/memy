'use client'
import Image from 'next/image'
import { X, Link, ExternalLink, MapPin, Calendar, Smile, Edit, Share2 } from 'lucide-react'
import { TagChip } from './TagChip'
import { formatDateLong } from '@/lib/utils'
import type { Memy } from '@/types/database'

interface DetailPanelProps {
  memy: Memy
  onClose: () => void
}

export default function DetailPanel({ memy, onClose }: DetailPanelProps) {
  return (
    <>
      {/* Scrim */}
      <div
        className="fixed inset-0 bg-black/20 z-30 animate-fade-rise-sm"
        onClick={onClose}
      />
      {/* Panel */}
      <aside className="fixed right-0 top-0 bottom-0 w-[460px] bg-surface-card shadow-lg z-40
                        flex flex-col overflow-y-auto animate-scale-in
                        max-[1099px]:w-1/2 max-[767px]:w-full max-[767px]:top-auto max-[767px]:h-[90vh] max-[767px]:rounded-t-2xl">

        {/* Hero photo */}
        <div className="relative w-full shrink-0" style={{ paddingBottom: '56.25%' }}>
          {memy.photo_url ? (
            <Image src={memy.photo_url} alt={memy.name} fill className="object-cover" sizes="460px" />
          ) : (
            <div className="absolute inset-0 bg-surface-sunken" />
          )}
          {/* Close glass button */}
          <button
            onClick={onClose}
            className="absolute top-4 right-4 w-9 h-9 rounded-full bg-black/35
                       flex items-center justify-center text-white
                       hover:bg-black/50 transition-colors"
            aria-label="Close"
          >
            <X className="w-4 h-4" />
          </button>
        </div>

        {/* Body */}
        <div className="flex flex-col p-6 gap-4 flex-1">
          <h2 className="font-display font-bold text-h1 text-text-strong leading-tight">
            {memy.name}
          </h2>

          {/* Meta row */}
          <div className="flex flex-wrap gap-4">
            <MetaItem icon={Calendar} text={formatDateLong(memy.date)} />
            {memy.location_label && <MetaItem icon={MapPin} text={memy.location_label} />}
            {memy.mood && <MetaItem icon={Smile} text={`${memy.mood}`} />}
          </div>

          {/* Description */}
          {memy.description && (
            <p className="font-ui text-body-sm text-text-body leading-relaxed">
              {memy.description}
            </p>
          )}

          {/* Link card */}
          {memy.url && (
            <a
              href={memy.url.startsWith('http') ? memy.url : `https://${memy.url}`}
              target="_blank"
              rel="noopener noreferrer"
              className="flex items-center gap-3 px-4 py-3 bg-surface-sunken rounded-md group"
            >
              <Link className="w-4 h-4 text-brand shrink-0" />
              <span className="font-ui text-field text-brand flex-1 truncate group-hover:underline">
                {memy.url}
              </span>
              <ExternalLink className="w-4 h-4 text-text-muted shrink-0" />
            </a>
          )}

          {/* Tags */}
          {memy.tags.length > 0 && (
            <div className="flex flex-wrap gap-2">
              {memy.tags.map(tag => <TagChip key={tag} tag={tag} />)}
            </div>
          )}

          {/* Map placeholder */}
          {memy.location_label && (
            <div className="w-full h-36 bg-surface-sunken rounded-md flex items-center justify-center text-text-muted">
              <div className="flex flex-col items-center gap-2">
                <MapPin className="w-6 h-6 text-brand" />
                <span className="text-caption">{memy.location_label}</span>
              </div>
            </div>
          )}

          {/* Actions */}
          <div className="flex gap-3 pt-2 mt-auto">
            <button className="flex items-center gap-2 px-4 py-2 rounded-md border border-border-default
                               text-field font-ui text-text-body hover:bg-surface-sunken transition-colors">
              <Edit className="w-4 h-4" /> Edit
            </button>
            <button className="flex items-center gap-2 px-4 py-2 rounded-md border border-border-default
                               text-field font-ui text-text-body hover:bg-surface-sunken transition-colors">
              <Share2 className="w-4 h-4" /> Share
            </button>
          </div>
        </div>
      </aside>
    </>
  )
}

function MetaItem({ icon: Icon, text }: { icon: typeof Calendar; text: string }) {
  return (
    <div className="flex items-center gap-1.5 text-text-muted">
      <Icon className="w-[15px] h-[15px] shrink-0" />
      <span className="font-ui text-caption">{text}</span>
    </div>
  )
}
