'use client'
import { useState } from 'react'
import Link from 'next/link'
import { useRouter } from 'next/navigation'
import { Mail, Lock, Eye, EyeOff } from 'lucide-react'
import { createClient } from '@/lib/supabase/client'
import Button from '@/components/ui/Button'
import Input from '@/components/ui/Input'

export default function LoginPage() {
  const router = useRouter()
  const supabase = createClient()
  const [email, setEmail]       = useState('')
  const [password, setPassword] = useState('')
  const [showPass, setShowPass] = useState(false)
  const [error, setError]       = useState('')
  const [loading, setLoading]   = useState(false)

  async function handleLogin(e: React.FormEvent) {
    e.preventDefault()
    setLoading(true); setError('')
    const { error } = await supabase.auth.signInWithPassword({ email, password })
    if (error) { setError(error.message); setLoading(false); return }
    router.push('/app/home')
    router.refresh()
  }

  return (
    <div className="min-h-screen bg-surface-page flex flex-col">
      {/* Wave header */}
      <div className="relative bg-brand wave-bottom texture-overlay h-[220px] flex-shrink-0">
        <Link href="/" className="absolute top-8 left-8 flex items-center gap-2">
          <div className="w-8 h-8 rounded-[10px] bg-on-brand/20 flex items-center justify-center">
            <span className="font-display font-bold text-on-brand text-base">m</span>
          </div>
          <span className="font-display font-bold text-on-brand text-h3">memy</span>
        </Link>
      </div>

      {/* Form */}
      <div className="flex-1 px-6 py-8 max-w-md mx-auto w-full">
        <div className="mb-7">
          <h1 className="font-display font-medium text-title text-text-strong leading-tight">Sign in</h1>
          <div className="w-[74px] h-[3px] rounded-full bg-brand mt-2" />
        </div>

        <form onSubmit={handleLogin} className="flex flex-col gap-5">
          <Input
            label="Email"
            type="email"
            icon={Mail}
            placeholder="demo@email.com"
            value={email}
            onChange={e => setEmail(e.target.value)}
            required
          />
          <div className="relative">
            <Input
              label="Password"
              type={showPass ? 'text' : 'password'}
              icon={Lock}
              value={password}
              onChange={e => setPassword(e.target.value)}
              required
            />
            <button
              type="button"
              onClick={() => setShowPass(v => !v)}
              className="absolute right-0 bottom-2 text-text-muted hover:text-text-body transition-colors"
            >
              {showPass ? <EyeOff className="w-4 h-4" /> : <Eye className="w-4 h-4" />}
            </button>
          </div>

          <div className="flex items-center justify-between">
            <label className="flex items-center gap-2 cursor-pointer">
              <input type="checkbox" className="accent-brand w-4 h-4" />
              <span className="font-ui text-body-sm text-text-body">Remember Me</span>
            </label>
            <button type="button" className="font-ui text-body-sm text-brand hover:underline">
              Forgot Password?
            </button>
          </div>

          {error && (
            <p className="text-caption text-danger bg-red-50 rounded-md px-3 py-2">{error}</p>
          )}

          <Button type="submit" fullWidth disabled={loading} className="mt-2">
            {loading ? 'Signing in…' : 'Login'}
          </Button>
        </form>

        <p className="text-center font-ui text-body-sm text-text-muted mt-6">
          Don&apos;t have an Account?{' '}
          <Link href="/auth/signup" className="text-brand font-semibold hover:underline">Sign up</Link>
        </p>
      </div>
    </div>
  )
}
