create table app_users (
    id uuid primary key,
    created_at timestamptz not null,
    updated_at timestamptz not null,
    email varchar(255) not null unique,
    password_hash varchar(255) not null,
    display_name varchar(80) not null,
    role varchar(20) not null
);

create table refresh_tokens (
    id uuid primary key,
    created_at timestamptz not null,
    updated_at timestamptz not null,
    user_id uuid not null references app_users(id) on delete cascade,
    token_hash varchar(64) not null unique,
    expires_at timestamptz not null,
    revoked boolean not null default false
);

create table genres (
    id uuid primary key,
    created_at timestamptz not null,
    updated_at timestamptz not null,
    name varchar(80) not null unique
);

create table artists (
    id uuid primary key,
    created_at timestamptz not null,
    updated_at timestamptz not null,
    name varchar(160) not null,
    biography varchar(2000)
);

create table albums (
    id uuid primary key,
    created_at timestamptz not null,
    updated_at timestamptz not null,
    title varchar(180) not null,
    artist_id uuid not null references artists(id),
    release_date date,
    cover_key varchar(512)
);

create table tracks (
    id uuid primary key,
    created_at timestamptz not null,
    updated_at timestamptz not null,
    title varchar(180) not null,
    album_id uuid not null references albums(id),
    genre_id uuid references genres(id),
    duration_seconds integer not null,
    media_key varchar(512) not null,
    mime_type varchar(100) not null
);

create table favorite_tracks (
    id uuid primary key,
    created_at timestamptz not null,
    updated_at timestamptz not null,
    user_id uuid not null references app_users(id) on delete cascade,
    track_id uuid not null references tracks(id) on delete cascade,
    unique (user_id, track_id)
);

create table listening_events (
    id uuid primary key,
    created_at timestamptz not null,
    updated_at timestamptz not null,
    user_id uuid not null references app_users(id) on delete cascade,
    track_id uuid not null references tracks(id) on delete cascade,
    listened_at timestamptz not null
);

create table playlists (
    id uuid primary key,
    created_at timestamptz not null,
    updated_at timestamptz not null,
    owner_id uuid not null references app_users(id) on delete cascade,
    name varchar(120) not null,
    description varchar(1000),
    is_public boolean not null default false
);

create table playlist_tracks (
    id uuid primary key,
    created_at timestamptz not null,
    updated_at timestamptz not null,
    playlist_id uuid not null references playlists(id) on delete cascade,
    track_id uuid not null references tracks(id) on delete cascade,
    position integer not null,
    unique (playlist_id, track_id)
);

create index idx_tracks_title on tracks(title);
create index idx_listening_events_user_time on listening_events(user_id, listened_at desc);
