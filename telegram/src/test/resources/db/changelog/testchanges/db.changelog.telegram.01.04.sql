--liquibase formatted sql

--changeset task#13:1
-- calculate bounty game tests
insert into game (id, stack, buy_in, bounty, game_type)
values  ('10e8b03e-aaf9-407b-8ad4-f126a0852f91', 1000, 30, 30, 'BOUNTY');

insert into chat_games (game_id, chat_id, created_at, message_id)
values  ('10e8b03e-aaf9-407b-8ad4-f126a0852f91', 123, NOW(), 6);

insert into entries (game_id, person_id, amount, created_at)
values  ('10e8b03e-aaf9-407b-8ad4-f126a0852f91', '13b4108e-2dfa-4fea-8b7b-277e1c87d2d8', 30, NOW()),
        ('10e8b03e-aaf9-407b-8ad4-f126a0852f91', '72775968-3da6-469e-8a61-60104eacdb3a', 30, NOW()),
        ('10e8b03e-aaf9-407b-8ad4-f126a0852f91', 'e2691144-3b1b-4841-9693-fad7af25bba9', 30, NOW()),
        ('10e8b03e-aaf9-407b-8ad4-f126a0852f91', 'e2691144-3b1b-4841-9693-fad7af25bba9', 30, NOW()),
        ('10e8b03e-aaf9-407b-8ad4-f126a0852f91', '58ae9984-1ebc-4621-ba0e-a577c69283ef', 30, NOW());

insert into bounty (game_id, amount, created_at,
             from_person, to_person)
values  ('10e8b03e-aaf9-407b-8ad4-f126a0852f91', 30, NOW(),
            '13b4108e-2dfa-4fea-8b7b-277e1c87d2d8','58ae9984-1ebc-4621-ba0e-a577c69283ef'),
        ('10e8b03e-aaf9-407b-8ad4-f126a0852f91', 30, NOW(),
            '72775968-3da6-469e-8a61-60104eacdb3a','58ae9984-1ebc-4621-ba0e-a577c69283ef'),
        ('10e8b03e-aaf9-407b-8ad4-f126a0852f91', 30, NOW(),
            'e2691144-3b1b-4841-9693-fad7af25bba9','13b4108e-2dfa-4fea-8b7b-277e1c87d2d8'),
        ('10e8b03e-aaf9-407b-8ad4-f126a0852f91', 30, NOW(),
            'e2691144-3b1b-4841-9693-fad7af25bba9','72775968-3da6-469e-8a61-60104eacdb3a');

insert into prize_pool (game_id, schema)
values  ('10e8b03e-aaf9-407b-8ad4-f126a0852f91', '{"1": 70,"2": 30}'::jsonb);

insert into finale_places (game_id, position, person_id)
values  ('10e8b03e-aaf9-407b-8ad4-f126a0852f91', 1, '58ae9984-1ebc-4621-ba0e-a577c69283ef'),
        ('10e8b03e-aaf9-407b-8ad4-f126a0852f91', 2, '72775968-3da6-469e-8a61-60104eacdb3a');
