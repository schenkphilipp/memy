export type Json = string | number | boolean | null | { [key: string]: Json } | Json[]

export interface Database {
  public: {
    Tables: {
      profiles: {
        Row: {
          id: string
          email: string
          name: string
          phone: string | null
          avatar_url: string | null
          created_at: string
        }
        Insert: {
          id: string
          email: string
          name: string
          phone?: string | null
          avatar_url?: string | null
          created_at?: string
        }
        Update: {
          name?: string
          phone?: string | null
          avatar_url?: string | null
        }
      }
      memys: {
        Row: {
          id: string
          user_id: string
          name: string
          description: string | null
          url: string | null
          tags: string[]
          photo_url: string | null
          date: string
          mood: string | null
          location_lat: number | null
          location_lng: number | null
          location_label: string | null
          collection_ids: string[]
          created_at: string
        }
        Insert: {
          id?: string
          user_id: string
          name: string
          description?: string | null
          url?: string | null
          tags?: string[]
          photo_url?: string | null
          date: string
          mood?: string | null
          location_lat?: number | null
          location_lng?: number | null
          location_label?: string | null
          collection_ids?: string[]
          created_at?: string
        }
        Update: {
          name?: string
          description?: string | null
          url?: string | null
          tags?: string[]
          photo_url?: string | null
          date?: string
          mood?: string | null
          location_lat?: number | null
          location_lng?: number | null
          location_label?: string | null
          collection_ids?: string[]
        }
      }
      collections: {
        Row: {
          id: string
          user_id: string
          name: string
          cover_urls: string[]
          memy_count: number
          created_at: string
        }
        Insert: {
          id?: string
          user_id: string
          name: string
          cover_urls?: string[]
          memy_count?: number
          created_at?: string
        }
        Update: {
          name?: string
          cover_urls?: string[]
          memy_count?: number
        }
      }
    }
  }
}

// Convenience types
export type Profile = Database['public']['Tables']['profiles']['Row']
export type Memy = Database['public']['Tables']['memys']['Row']
export type Collection = Database['public']['Tables']['collections']['Row']

export type MemyInsert = Database['public']['Tables']['memys']['Insert']
export type CollectionInsert = Database['public']['Tables']['collections']['Insert']
