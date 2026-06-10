import LandingNav from '@/components/sections/LandingNav'
import HeroSection from '@/components/sections/HeroSection'
import ValueProps from '@/components/sections/ValueProps'
import FeatureShowcase from '@/components/sections/FeatureShowcase'
import { CTABand, Footer } from '@/components/sections/CTABand'

export default function HomePage() {
  return (
    <div className="min-h-screen bg-surface-page">
      <LandingNav />
      <main>
        <HeroSection />
        <ValueProps />
        <FeatureShowcase />
        <CTABand />
      </main>
      <Footer />
    </div>
  )
}
