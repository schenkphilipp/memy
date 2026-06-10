-- ============================================================
-- Memy — initial schema
-- ============================================================

-- ── Extensions ───────────────────────────────────────────────
create extension if not exists "uuid-ossp";

-- ── Profiles (extends auth.users) ────────────────────────────
create table if not exists public.profiles (
  id          uuid references auth.users on delete cascade primary key,
  email       text unique not null,
  name        text not null default '',
  phone       text,
  avatar_url  text,
  created_at  timestamptz default now() not null
);

alter table public.profiles enable row level security;

create policy "Users can read their own profile"
  on public.profiles for select
  using (auth.uid() = id);

create policy "Users can update their own profile"
  on public.profiles for update
  using (auth.uid() = id);

-- Auto-create profile on sign-up
create or replace function public.handle_new_user()
returns trigger language plpgsql security definer set search_path = public as $$
begin
  insert into public.profiles (id, email, name)
  values (
    new.id,
    new.email,
    coalesce(new.raw_user_meta_data->>'name', split_part(new.email, '@', 1))
  );
  return new;
end;
$$;

drop trigger if exists on_auth_user_created on auth.users;
create trigger on_auth_user_created
  after insert on auth.users
  for each row execute procedure public.handle_new_user();

-- ── Memys ─────────────────────────────────────────────────────
create table if not exists public.memys (
  id               uuid default uuid_generate_v4() primary key,
  user_id          uuid references public.profiles(id) on delete cascade not null,
  name             text not null,
  description      text,
  url              text,
  tags             text[]    default '{}',
  photo_url        text,
  date             date      not null default current_date,
  mood             text,
  location_lat     double precision,
  location_lng     double precision,
  location_label   text,
  collection_ids   uuid[]    default '{}',
  created_at       timestamptz default now() not null
);

alter table public.memys enable row level security;

create policy "Users can CRUD their own memys"
  on public.memys for all
  using (auth.uid() = user_id)
  with check (auth.uid() = user_id);

create index idx_memys_user_id   on public.memys(user_id);
create index idx_memys_created_at on public.memys(created_at desc);
create index idx_memys_tags      on public.memys using gin(tags);

-- Full-text search on name + description + tags
alter table public.memys
  add column if not exists fts tsvector
    generated always as (
      to_tsvector('english',
        coalesce(name, '') || ' ' ||
        coalesce(description, '') || ' ' ||
        coalesce(array_to_string(tags, ' '), '') || ' ' ||
        coalesce(location_label, '')
      )
    ) stored;

create index idx_memys_fts on public.memys using gin(fts);

-- ── Collections ───────────────────────────────────────────────
create table if not exists public.collections (
  id          uuid default uuid_generate_v4() primary key,
  user_id     uuid references public.profiles(id) on delete cascade not null,
  name        text not null,
  cover_urls  text[]  default '{}',
  memy_count  integer default 0,
  created_at  timestamptz default now() not null
);

alter table public.collections enable row level security;

create policy "Users can CRUD their own collections"
  on public.collections for all
  using (auth.uid() = user_id)
  with check (auth.uid() = user_id);

create index idx_collections_user_id on public.collections(user_id);

-- ── Storage bucket for memy photos ───────────────────────────
insert into storage.buckets (id, name, public)
values ('memy-photos', 'memy-photos', true)
on conflict do nothing;

create policy "Authenticated users can upload photos"
  on storage.objects for insert
  to authenticated
  with check (bucket_id = 'memy-photos' and auth.uid()::text = (storage.foldername(name))[1]);

create policy "Anyone can view memy photos"
  on storage.objects for select
  using (bucket_id = 'memy-photos');

create policy "Users can delete their own photos"
  on storage.objects for delete
  to authenticated
  using (bucket_id = 'memy-photos' and auth.uid()::text = (storage.foldername(name))[1]);
