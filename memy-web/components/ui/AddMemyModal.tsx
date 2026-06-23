'use client'
import { useState, useEffect, useRef, FormEvent } from 'react'
import { X, ImagePlus, Trash2, MapPin } from 'lucide-react'
import Image from 'next/image'
import { createClient } from '@/lib/supabase/client'
import Input from './Input'
import Button from './Button'
import type { Memy, Collection } from '@/types/database'

interface InitialLocation { lat: number; lng: number; label: string }

interface AddMemyModalProps {
  onClose: () => void
  onAdded: (memy: Memy) => void
  initialLocation?: InitialLocation
}

export default function AddMemyModal({ onClose, onAdded, initialLocation }: AddMemyModalProps) {
  const [name, setName]               = useState('')
  const [description, setDescription] = useState('')
  const [url, setUrl]                 = useState('')
  const [tags, setTags]               = useState('')
  const [date, setDate]               = useState(new Date().toISOString().slice(0, 10))
  const [photoFile, setPhotoFile]         = useState<File | null>(null)
  const [photoPreview, setPhotoPreview]   = useState<string | null>(null)
  const [collections, setCollections]     = useState<Collection[]>([])
  const [selectedIds, setSelectedIds]     = useState<string[]>([])
  const [location, setLocation]           = useState<InitialLocation | null>(initialLocation ?? null)
  const [error, setError]                 = useState<string | null>(null)
  const [loading, setLoading]             = useState(false)
  const fileInputRef                      = useRef<HTMLInputElement>(null)

  useEffect(() => {
    const supabase = createClient()
    supabase.auth.getUser().then(({ data: { user } }) => {
      if (!user) return
      supabase
        .from('collections')
        .select('id, name')
        .eq('user_id', user.id)
        .order('name')
        .then(({ data }) => { if (data) setCollections(data as Collection[]) })
    })
  }, [])

  function toggleCollection(id: string) {
    setSelectedIds(prev => prev.includes(id) ? prev.filter(x => x !== id) : [...prev, id])
  }

  function handleFileChange(file: File | null) {
    if (!file) return
    setPhotoFile(file)
    setPhotoPreview(URL.createObjectURL(file))
  }

  function removePhoto() {
    setPhotoFile(null)
    if (photoPreview) URL.revokeObjectURL(photoPreview)
    setPhotoPreview(null)
    if (fileInputRef.current) fileInputRef.current.value = ''
  }

  async function uploadPhoto(userId: string): Promise<string | null> {
    if (!photoFile) return null
    const supabase = createClient()
    const ext  = photoFile.name.split('.').pop()
    const path = `${userId}/${Date.now()}.${ext}`
    const { error } = await supabase.storage.from('memy-photos').upload(path, photoFile)
    if (error) throw new Error(error.message)
    const { data } = supabase.storage.from('memy-photos').getPublicUrl(path)
    return data.publicUrl
  }

  async function handleSubmit(e: FormEvent) {
    e.preventDefault()
    if (!name.trim()) { setError('Name is required.'); return }

    setLoading(true)
    setError(null)

    const supabase = createClient()
    const { data: { user } } = await supabase.auth.getUser()
    if (!user) { setError('Not signed in.'); setLoading(false); return }

    let photoUrl: string | null = null
    try {
      photoUrl = await uploadPhoto(user.id)
    } catch (err) {
      setError(err instanceof Error ? err.message : 'Photo upload failed.')
      setLoading(false)
      return
    }

    const tagList = tags.split(',').map(t => t.trim()).filter(Boolean)

    const { data, error: dbError } = await supabase
      .from('memys')
      .insert({
        user_id:     user.id,
        name:        name.trim(),
        description: description.trim() || null,
        url:         url.trim() || null,
        tags:        tagList,
        date,
        photo_url:      photoUrl,
        collection_ids: selectedIds,
        location_lat:   location?.lat ?? null,
        location_lng:   location?.lng ?? null,
        location_label: location?.label ?? null,
      })
      .select()
      .single()

    setLoading(false)

    if (dbError) { setError(dbError.message); return }
    onAdded(data)
    onClose()
  }

  return (
    <>
      <div className="fixed inset-0 bg-black/20 z-30" onClick={onClose} />
      <div className="fixed inset-0 z-40 flex items-center justify-center p-4 overflow-y-auto">
        <div className="bg-surface-card rounded-2xl shadow-lg w-full max-w-md flex flex-col animate-scale-in my-auto">
          <div className="flex items-center justify-between px-6 pt-6 pb-4 border-b border-border-subtle">
            <h2 className="font-display font-bold text-h3 text-text-strong">Add a memy</h2>
            <button
              onClick={onClose}
              className="w-8 h-8 rounded-full flex items-center justify-center hover:bg-surface-sunken transition-colors"
              aria-label="Close"
            >
              <X className="w-4 h-4 text-text-muted" />
            </button>
          </div>

          <form onSubmit={handleSubmit} className="flex flex-col gap-5 px-6 py-6">

            {/* Photo upload */}
            <div className="flex flex-col gap-1">
              <span className="text-caption font-accent font-medium text-text-muted uppercase tracking-wider">
                Photo
              </span>
              {photoPreview ? (
                <div className="relative w-full rounded-xl overflow-hidden" style={{ paddingBottom: '56.25%' }}>
                  <Image src={photoPreview} alt="Preview" fill className="object-cover" sizes="448px" />
                  <button
                    type="button"
                    onClick={removePhoto}
                    className="absolute top-2 right-2 w-8 h-8 rounded-full bg-black/50 flex items-center justify-center text-white hover:bg-black/70 transition-colors"
                    aria-label="Remove photo"
                  >
                    <Trash2 className="w-4 h-4" />
                  </button>
                </div>
              ) : (
                <button
                  type="button"
                  onClick={() => fileInputRef.current?.click()}
                  className="w-full h-32 rounded-xl border-2 border-dashed border-border-subtle
                             flex flex-col items-center justify-center gap-2
                             text-text-muted hover:border-brand hover:text-brand
                             transition-colors duration-140"
                >
                  <ImagePlus className="w-6 h-6" />
                  <span className="font-ui text-caption">Click to add a photo</span>
                </button>
              )}
              <input
                ref={fileInputRef}
                type="file"
                accept="image/*"
                className="hidden"
                onChange={e => handleFileChange(e.target.files?.[0] ?? null)}
              />
            </div>

            <Input
              label="Name"
              placeholder="e.g. Sunset at Piazzale Michelangelo"
              value={name}
              onChange={e => setName(e.target.value)}
              required
            />
            <div className="flex flex-col gap-1 w-full">
              <label className="text-caption font-accent font-medium text-text-muted uppercase tracking-wider">
                Description
              </label>
              <textarea
                placeholder="What made this worth remembering?"
                value={description}
                onChange={e => setDescription(e.target.value)}
                rows={3}
                className="w-full bg-transparent font-display text-field text-text-strong border-b border-neutral-400 pb-2 focus:outline-none focus:border-brand placeholder:text-text-subtle transition-colors duration-220 resize-none"
              />
            </div>
            <Input
              label="Link"
              placeholder="https://..."
              type="url"
              value={url}
              onChange={e => setUrl(e.target.value)}
            />
            <Input
              label="Tags"
              placeholder="food, travel, italy"
              value={tags}
              onChange={e => setTags(e.target.value)}
            />
            <Input
              label="Date"
              type="date"
              value={date}
              onChange={e => setDate(e.target.value)}
            />

            {/* Location */}
            <div className="flex flex-col gap-1">
              <span className="text-caption font-accent font-medium text-text-muted uppercase tracking-wider">Location</span>
              {location ? (
                <div className="flex items-center gap-2 px-3 py-2 bg-surface-sunken rounded-md">
                  <MapPin className="w-4 h-4 text-brand shrink-0" />
                  <span className="font-ui text-body-sm text-text-body flex-1 truncate">{location.label}</span>
                  <button type="button" onClick={() => setLocation(null)} aria-label="Remove location">
                    <X className="w-4 h-4 text-text-muted hover:text-danger transition-colors" />
                  </button>
                </div>
              ) : (
                <p className="font-ui text-caption text-text-subtle">No location selected.</p>
              )}
            </div>

            {/* Collection picker */}
            {collections.length > 0 && (
              <div className="flex flex-col gap-2">
                <span className="text-caption font-accent font-medium text-text-muted uppercase tracking-wider">
                  Add to collection
                </span>
                <div className="flex flex-wrap gap-2">
                  {collections.map(col => {
                    const active = selectedIds.includes(col.id)
                    return (
                      <button
                        key={col.id}
                        type="button"
                        onClick={() => toggleCollection(col.id)}
                        className={`px-3 py-1 rounded-full text-caption font-ui border transition-colors duration-140
                          ${active
                            ? 'bg-brand text-on-brand border-brand'
                            : 'bg-transparent text-text-body border-border-default hover:border-brand hover:text-brand'
                          }`}
                      >
                        {col.name}
                      </button>
                    )
                  })}
                </div>
              </div>
            )}

            {error && <p className="text-caption text-danger">{error}</p>}

            <div className="flex gap-3 pt-2">
              <Button type="button" variant="secondary" onClick={onClose} fullWidth>
                Cancel
              </Button>
              <Button type="submit" variant="primary" disabled={loading} fullWidth>
                {loading ? 'Saving…' : 'Save memy'}
              </Button>
            </div>
          </form>
        </div>
      </div>
    </>
  )
}
