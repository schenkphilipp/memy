'use client'
import { useState, useRef, FormEvent } from 'react'
import { X, ImagePlus, Trash2 } from 'lucide-react'
import Image from 'next/image'
import { createClient } from '@/lib/supabase/client'
import Input from './Input'
import Button from './Button'
import type { Collection } from '@/types/database'

interface AddCollectionModalProps {
  onClose: () => void
  onAdded: (collection: Collection) => void
}

export default function AddCollectionModal({ onClose, onAdded }: AddCollectionModalProps) {
  const [name, setName]               = useState('')
  const [coverFile, setCoverFile]     = useState<File | null>(null)
  const [coverPreview, setCoverPreview] = useState<string | null>(null)
  const [error, setError]             = useState<string | null>(null)
  const [loading, setLoading]         = useState(false)
  const fileInputRef                  = useRef<HTMLInputElement>(null)

  function handleFileChange(file: File | null) {
    if (!file) return
    setCoverFile(file)
    setCoverPreview(URL.createObjectURL(file))
  }

  function removeCover() {
    setCoverFile(null)
    if (coverPreview) URL.revokeObjectURL(coverPreview)
    setCoverPreview(null)
    if (fileInputRef.current) fileInputRef.current.value = ''
  }

  async function uploadCover(userId: string): Promise<string | null> {
    if (!coverFile) return null
    const supabase = createClient()
    const ext  = coverFile.name.split('.').pop()
    const path = `${userId}/${Date.now()}.${ext}`
    const { error } = await supabase.storage.from('memy-photos').upload(path, coverFile)
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

    let coverUrl: string | null = null
    try {
      coverUrl = await uploadCover(user.id)
    } catch (err) {
      setError(err instanceof Error ? err.message : 'Photo upload failed.')
      setLoading(false)
      return
    }

    const { data, error: dbError } = await supabase
      .from('collections')
      .insert({
        user_id:    user.id,
        name:       name.trim(),
        cover_urls: coverUrl ? [coverUrl] : ([] as string[]),
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
      <div className="fixed inset-0 z-40 flex items-center justify-center p-4">
        <div className="bg-surface-card rounded-2xl shadow-lg w-full max-w-sm flex flex-col animate-scale-in">
          <div className="flex items-center justify-between px-6 pt-6 pb-4 border-b border-border-subtle">
            <h2 className="font-display font-bold text-h3 text-text-strong">New collection</h2>
            <button
              onClick={onClose}
              className="w-8 h-8 rounded-full flex items-center justify-center hover:bg-surface-sunken transition-colors"
              aria-label="Close"
            >
              <X className="w-4 h-4 text-text-muted" />
            </button>
          </div>

          <form onSubmit={handleSubmit} className="flex flex-col gap-5 px-6 py-6">
            <Input
              label="Name"
              placeholder="e.g. Italy 2025, Good eats, Reading list"
              value={name}
              onChange={e => setName(e.target.value)}
              required
            />

            {/* Cover photo */}
            <div className="flex flex-col gap-1">
              <span className="text-caption font-accent font-medium text-text-muted uppercase tracking-wider">
                Cover photo
              </span>
              {coverPreview ? (
                <div className="relative w-full rounded-xl overflow-hidden" style={{ paddingBottom: '56.25%' }}>
                  <Image src={coverPreview} alt="Cover preview" fill className="object-cover" sizes="384px" />
                  <button
                    type="button"
                    onClick={removeCover}
                    className="absolute top-2 right-2 w-8 h-8 rounded-full bg-black/50 flex items-center justify-center text-white hover:bg-black/70 transition-colors"
                    aria-label="Remove cover"
                  >
                    <Trash2 className="w-4 h-4" />
                  </button>
                </div>
              ) : (
                <button
                  type="button"
                  onClick={() => fileInputRef.current?.click()}
                  className="w-full h-28 rounded-xl border-2 border-dashed border-border-subtle
                             flex flex-col items-center justify-center gap-2
                             text-text-muted hover:border-brand hover:text-brand
                             transition-colors duration-140"
                >
                  <ImagePlus className="w-6 h-6" />
                  <span className="font-ui text-caption">Click to add a cover photo</span>
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

            {error && <p className="text-caption text-danger">{error}</p>}

            <div className="flex gap-3 pt-2">
              <Button type="button" variant="secondary" onClick={onClose} fullWidth>
                Cancel
              </Button>
              <Button type="submit" variant="primary" disabled={loading} fullWidth>
                {loading ? 'Creating…' : 'Create'}
              </Button>
            </div>
          </form>
        </div>
      </div>
    </>
  )
}
