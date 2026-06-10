'use client'
import { cn } from '@/lib/utils'
import { type InputHTMLAttributes, forwardRef } from 'react'
import { LucideIcon } from 'lucide-react'

interface InputProps extends InputHTMLAttributes<HTMLInputElement> {
  label: string
  icon?: LucideIcon
  error?: string
}

const Input = forwardRef<HTMLInputElement, InputProps>(
  ({ label, icon: Icon, error, className, id, ...props }, ref) => {
    const inputId = id ?? label.toLowerCase().replace(/\s+/g, '-')
    return (
      <div className="flex flex-col gap-1 w-full">
        <label htmlFor={inputId} className="text-caption font-accent font-medium text-text-muted uppercase tracking-wider">
          {label}
        </label>
        <div className="relative flex items-center">
          {Icon && (
            <Icon className="absolute left-0 w-4 h-4 text-text-muted peer-focus:text-brand transition-colors" />
          )}
          <input
            ref={ref}
            id={inputId}
            className={cn(
              'peer w-full bg-transparent font-display text-field text-text-strong',
              'border-b border-neutral-400 pb-2',
              'focus:outline-none focus:border-brand',
              'placeholder:text-text-subtle',
              'transition-colors duration-220',
              Icon ? 'pl-6' : 'pl-0',
              error && 'border-danger',
              className,
            )}
            {...props}
          />
        </div>
        {error && <p className="text-caption text-danger">{error}</p>}
      </div>
    )
  }
)
Input.displayName = 'Input'
export default Input
