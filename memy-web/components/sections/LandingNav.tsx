'use client'
import Link from 'next/link'
import Button from '@/components/ui/Button'

export default function LandingNav() {
  return (
    <nav className="sticky top-0 z-50 h-[72px] bg-[rgba(255,252,252,0.82)] backdrop-blur-md
                    border-b border-border-subtle flex items-center">
      <div className="max-w-[1160px] mx-auto px-8 flex items-center w-full gap-8">
        {/* Logo */}
        <Link href="/" className="flex items-center gap-2 mr-4">
          <div className="w-9 h-9 rounded-[10px] bg-brand flex items-center justify-center shadow-coral">
            <span className="font-display font-bold text-on-brand text-xl leading-none">m</span>
          </div>
          <span className="font-display font-bold text-h3 text-text-strong">memy</span>
          <span className="w-1.5 h-1.5 rounded-full bg-brand mb-0.5 self-end" />
        </Link>

        {/* Links */}
        <div className="hidden md:flex items-center gap-6 font-ui text-body-sm text-text-body">
          <Link href="#features" className="hover:text-text-strong transition-colors">Features</Link>
          <Link href="#how-it-works" className="hover:text-text-strong transition-colors">How it works</Link>
          <Link href="#stories" className="hover:text-text-strong transition-colors">Stories</Link>
        </div>

        <div className="flex items-center gap-3 ml-auto">
          <Link href="/auth/login">
            <button className="font-ui text-body-sm text-text-body hover:text-text-strong transition-colors px-3 py-2">
              Log in
            </button>
          </Link>
          <Link href="/auth/signup">
            <Button size="sm">Get the app</Button>
          </Link>
        </div>
      </div>
    </nav>
  )
}
