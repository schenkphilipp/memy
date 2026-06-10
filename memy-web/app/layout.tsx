import type { Metadata } from 'next'
import './globals.css'

export const metadata: Metadata = {
  title: 'memy — capture, organize and revisit your memories',
  description: 'Save places, notes, links and photos in a tap. Organise into collections, see them on your personal map.',
  openGraph: {
    title: 'memy',
    description: 'Capture, organize and revisit the memories you love.',
    type: 'website',
  },
}

export default function RootLayout({ children }: { children: React.ReactNode }) {
  return (
    <html lang="en">
      <body>{children}</body>
    </html>
  )
}
