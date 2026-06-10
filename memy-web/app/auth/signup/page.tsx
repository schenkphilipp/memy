'use client'
import { useState } from 'react'
import Link from 'next/link'
import { useRouter } from 'next/navigation'
import { Mail, Lock, Phone, User } from 'lucide-react'
import { createClient } from '@/lib/supabase/client'
import Button from '@/components/ui/Button'
import Input from '@/components/ui/Input'

export default function SignUpPage() {
  const router = useRouter()
  const supabase = createClient()
  const [name, setName]         = useState('')
  const [email, setEmail]       = useState('')
  const [phone, setPhone]       = useState('')
  const [password, setPassword] = useState('')
  const [confirm, setConfirm]   = useState('')
  const [error, setError]       = useState('')
  const [loading, setLoading]   = useState(false)

  async function handleSignUp(e: React.FormEvent) {
    e.preventDefault()
    if (password !== confirm) { setError('Passwords do not match'); return }
    setLoading(true); setError('')

    const { error } = await supabase.auth.signUp({
      email, password,
      options: { data: { name, phone } },
    })
    if (error) { setError(error.message); setLoading(false); return }
    router.push('/app/home')
    router.refresh()
  }

  return (
    <div className="min-h-screen bg-surface-page flex flex-col">
      <div className="relative bg-brand wave-bottom texture-overlay h-[160px] flex-shrink-0">
        <Link href="/" className="absolute top-8 left-8 flex items-center gap-2">
          <div className="w-8 h-8 rounded-[10px] bg-on-brand/20 flex items-center justify-center">
            <span className="font-display font-bold text-on-brand text-base">m</span>
          </div>
          <span className="font-display font-bold text-on-brand text-h3">memy</span>
        </Link>
      </div>

      <div className="flex-1 px-6 py-8 max-w-md mx-auto w-full">
        <div className="mb-7">
          <h1 className="font-display font-bold text-h1 text-text-strong">Create account</h1>
          <div className="w-[74px] h-[3px] rounded-full bg-brand mt-2" />
        </div>

        <form onSubmit={handleSignUp} className="flex flex-col gap-5">
          <Input label="Name"     type="text"     icon={User}  value={name}     onChange={e => setName(e.target.value)}     required />
          <Input label="Email"    type="email"    icon={Mail}  value={email}    onChange={e => setEmail(e.target.value)}    required />
          <Input label="Phone"    type="tel"      icon={Phone} value={phone}    onChange={e => setPhone(e.target.value)} />
          <Input label="Password" type="password" icon={Lock}  value={password} onChange={e => setPassword(e.target.value)} required />
          <Input label="Confirm password" type="password" icon={Lock} value={confirm} onChange={e => setConfirm(e.target.value)} required />

          {error && (
            <p className="text-caption text-danger bg-red-50 rounded-md px-3 py-2">{error}</p>
          )}

          <Button type="submit" fullWidth disabled={loading} className="mt-2">
            {loading ? 'Creating account…' : 'Create account'}
          </Button>
        </form>

        <p className="text-center font-ui text-body-sm text-text-muted mt-6">
          Already have an account?{' '}
          <Link href="/auth/login" className="text-brand font-semibold hover:underline">Login</Link>
        </p>
      </div>
    </div>
  )
}
