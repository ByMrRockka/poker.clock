--liquibase formatted sql

--changeset task#4:1
insert into person (id, first_name, last_name, nick_name)
values  ('13b4108e-2dfa-4fea-8b7b-277e1c87d2d8', 'Grisha', 'Anikii', 'kinger'),
        ('72775968-3da6-469e-8a61-60104eacdb3a', 'Kate', null, 'queen'),
        ('e2691144-3b1b-4841-9693-fad7af25bba9', null, 'Portugal', 'jackas'),
        ('58ae9984-1ebc-4621-ba0e-a577c69283ef', null, null, 'tenten');


insert into game (id, stack, buy_in, bounty, game_type)
values  ('fa3d03c4-f411-4852-810f-c0cc2f5b8c84', 1000, 15, 0, 'CASH'),
        ('4a411a12-2386-4dce-b579-d806c91d6d17', 1500, 30, 0, 'BOUNTY'),
        ('3e255b72-db57-4cc8-9c07-56991a8ab67a', 30000, 30, 30, 'TOURNAMENT'),
        ('b759ac52-1496-463f-b0d8-982deeac085c', 10000, 15, 25, 'TOURNAMENT');

insert into chat_persons (person_id, chat_id)
values  ('13b4108e-2dfa-4fea-8b7b-277e1c87d2d8', 123),
        ('72775968-3da6-469e-8a61-60104eacdb3a', 123),
        ('e2691144-3b1b-4841-9693-fad7af25bba9', 123),
        ('58ae9984-1ebc-4621-ba0e-a577c69283ef', 123);

insert into chat_games (game_id, chat_id, created_at, message_id)
values  ('4a411a12-2386-4dce-b579-d806c91d6d17', 123, NOW(), 1),
        ('b759ac52-1496-463f-b0d8-982deeac085c', 123, NOW(), 2);

insert into entries (game_id, person_id, amount, created_at)
values  ('4a411a12-2386-4dce-b579-d806c91d6d17', '13b4108e-2dfa-4fea-8b7b-277e1c87d2d8', 30000, NOW()),
        ('4a411a12-2386-4dce-b579-d806c91d6d17', '72775968-3da6-469e-8a61-60104eacdb3a', 30000, NOW());

-- calculate tournament tests
insert into chat_games (game_id, chat_id, created_at, message_id)
values  ('3e255b72-db57-4cc8-9c07-56991a8ab67a', 123, NOW(), 3);

insert into entries (game_id, person_id, amount, created_at)
values  ('3e255b72-db57-4cc8-9c07-56991a8ab67a', '13b4108e-2dfa-4fea-8b7b-277e1c87d2d8', 30, NOW()),
        ('3e255b72-db57-4cc8-9c07-56991a8ab67a', '72775968-3da6-469e-8a61-60104eacdb3a', 30, NOW()),
        ('3e255b72-db57-4cc8-9c07-56991a8ab67a', 'e2691144-3b1b-4841-9693-fad7af25bba9', 30, NOW()),
        ('3e255b72-db57-4cc8-9c07-56991a8ab67a', '58ae9984-1ebc-4621-ba0e-a577c69283ef', 30, NOW());

insert into prize_pool (game_id, schema)
values  ('3e255b72-db57-4cc8-9c07-56991a8ab67a', '{"1": 70,"2": 30}'::jsonb);

insert into finale_places (game_id, position, person_id)
values  ('3e255b72-db57-4cc8-9c07-56991a8ab67a', 1, '13b4108e-2dfa-4fea-8b7b-277e1c87d2d8'),
        ('3e255b72-db57-4cc8-9c07-56991a8ab67a', 2, '72775968-3da6-469e-8a61-60104eacdb3a');

-- calculate cash tests
insert into game (id, stack, buy_in, bounty, game_type)
values  ('123934e5-2cf0-46c8-bc73-9a8ed6696e63', 1000, 30, 0, 'CASH');

insert into chat_games (game_id, chat_id, created_at, message_id)
values  ('123934e5-2cf0-46c8-bc73-9a8ed6696e63', 123, NOW(), 4);

insert into entries (game_id, person_id, amount, created_at)
values  ('123934e5-2cf0-46c8-bc73-9a8ed6696e63', '13b4108e-2dfa-4fea-8b7b-277e1c87d2d8', 30, NOW()),
        ('123934e5-2cf0-46c8-bc73-9a8ed6696e63', '72775968-3da6-469e-8a61-60104eacdb3a', 30, NOW()),
        ('123934e5-2cf0-46c8-bc73-9a8ed6696e63', 'e2691144-3b1b-4841-9693-fad7af25bba9', 30, NOW()),
        ('123934e5-2cf0-46c8-bc73-9a8ed6696e63', '58ae9984-1ebc-4621-ba0e-a577c69283ef', 30, NOW()),
        ('123934e5-2cf0-46c8-bc73-9a8ed6696e63', 'e2691144-3b1b-4841-9693-fad7af25bba9', 30, NOW());

insert into withdrawal (game_id, person_id, amount, created_at)
values  ('123934e5-2cf0-46c8-bc73-9a8ed6696e63', '13b4108e-2dfa-4fea-8b7b-277e1c87d2d8', 5, NOW()),
        ('123934e5-2cf0-46c8-bc73-9a8ed6696e63', '13b4108e-2dfa-4fea-8b7b-277e1c87d2d8', 15, NOW()),
        ('123934e5-2cf0-46c8-bc73-9a8ed6696e63', '13b4108e-2dfa-4fea-8b7b-277e1c87d2d8', 15, NOW()),
        ('123934e5-2cf0-46c8-bc73-9a8ed6696e63', '72775968-3da6-469e-8a61-60104eacdb3a', 15, NOW()),
        ('123934e5-2cf0-46c8-bc73-9a8ed6696e63', '72775968-3da6-469e-8a61-60104eacdb3a', 10, NOW()),
        ('123934e5-2cf0-46c8-bc73-9a8ed6696e63', 'e2691144-3b1b-4841-9693-fad7af25bba9', 15, NOW()),
        ('123934e5-2cf0-46c8-bc73-9a8ed6696e63', 'e2691144-3b1b-4841-9693-fad7af25bba9', 15, NOW()),
        ('123934e5-2cf0-46c8-bc73-9a8ed6696e63', 'e2691144-3b1b-4841-9693-fad7af25bba9', 15, NOW()),
        ('123934e5-2cf0-46c8-bc73-9a8ed6696e63', '58ae9984-1ebc-4621-ba0e-a577c69283ef', 30, NOW()),
        ('123934e5-2cf0-46c8-bc73-9a8ed6696e63', '58ae9984-1ebc-4621-ba0e-a577c69283ef', 15, NOW());


insert into game (id, stack, buy_in, bounty, game_type)
values  ('51d973b6-cde3-4bbb-b67b-7555243dbc15', 1000, 30, 0, 'CASH');

insert into chat_games (game_id, chat_id, created_at, message_id)
values  ('51d973b6-cde3-4bbb-b67b-7555243dbc15', 123, NOW(), 5);
