'use client'
import { cn } from '@/lib/utils'
import { type ButtonHTMLAttributes, forwardRef } from 'react'

interface ButtonProps extends ButtonHTMLAttributes<HTMLButtonElement> {
  variant?: 'primary' | 'secondary' | 'ghost'
  size?: 'sm' | 'md' | 'lg'
  fullWidth?: boolean
}

const Button = forwardRef<HTMLButtonElement, ButtonProps>(
  ({ variant = 'primary', size = 'md', fullWidth, className, children, ...props }, ref) => {
    return (
      <button
        ref={ref}
        className={cn(
          // Base
          'inline-flex items-center justify-center font-ui font-bold rounded-md',
          'transition-all duration-140 ease-out-memy',
          'active:scale-[0.97] focus-visible:outline-none focus-visible:shadow-focus',
          'disabled:opacity-50 disabled:cursor-not-allowed',
          // Sizes
          size === 'sm' && 'h-9 px-4 text-sm',
          size === 'md' && 'h-[49px] px-6 text-body-sm',
          size === 'lg' && 'h-[49px] px-8 text-label',
          // Variants
          variant === 'primary'   && 'bg-brand text-on-brand shadow-coral hover:bg-brand-strong',
          variant === 'secondary' && 'bg-surface-card text-brand border border-brand hover:bg-brand-wash',
          variant === 'ghost'     && 'bg-transparent text-brand hover:bg-brand-wash',
          // Width
          fullWidth && 'w-full',
          className,
        )}
        {...props}
      >
        {children}
      </button>
    )
  }
)
Button.displayName = 'Button'
export default Button
