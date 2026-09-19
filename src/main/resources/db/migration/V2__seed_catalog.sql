insert into genres (id, created_at, updated_at, name) values
  ('d09d6f74-6e1a-4ec3-81a8-0156cf001001', now(), now(), 'Electronic'),
  ('d09d6f74-6e1a-4ec3-81a8-0156cf001002', now(), now(), 'Ambient');

insert into artists (id, created_at, updated_at, name, biography) values
  ('a09d6f74-6e1a-4ec3-81a8-0156cf001001', now(), now(), 'Sweetwave Studio', 'Demo artist for local development.');

insert into albums (id, created_at, updated_at, title, artist_id, release_date) values
  ('b09d6f74-6e1a-4ec3-81a8-0156cf001001', now(), now(), 'First Signal', 'a09d6f74-6e1a-4ec3-81a8-0156cf001001', '2026-01-15');

insert into tracks (id, created_at, updated_at, title, album_id, genre_id, duration_seconds, media_key, mime_type) values
  ('c09d6f74-6e1a-4ec3-81a8-0156cf001001', now(), now(), 'Demo Wave', 'b09d6f74-6e1a-4ec3-81a8-0156cf001001', 'd09d6f74-6e1a-4ec3-81a8-0156cf001001', 3, 'samples/demo-wave.wav', 'audio/wav');
