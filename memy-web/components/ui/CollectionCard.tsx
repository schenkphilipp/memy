import Image from 'next/image'
import type { Collection } from '@/types/database'

interface CollectionCardProps {
  collection: Collection
  onClick: () => void
}

export default function CollectionCard({ collection, onClick }: CollectionCardProps) {
  const covers = collection.cover_urls.slice(0, 4)
  while (covers.length < 4) covers.push('')

  return (
    <div
      onClick={onClick}
      className="bg-surface-card rounded-lg shadow-sm cursor-pointer
                 hover:shadow-md transition-shadow duration-220 overflow-hidden"
    >
      {/* 2×2 mosaic */}
      <div className="grid grid-cols-2 gap-px h-[110px]">
        {covers.map((url, i) => (
          <div key={i} className="relative bg-surface-sunken overflow-hidden">
            {url && (
              <Image src={url} alt="" fill className="object-cover" sizes="120px" />
            )}
          </div>
        ))}
      </div>
      <div className="p-3">
        <p className="font-ui font-semibold text-body-sm text-text-strong">{collection.name}</p>
        <p className="text-[11px] text-text-muted font-ui mt-0.5">{collection.memy_count} memys</p>
      </div>
    </div>
  )
}
