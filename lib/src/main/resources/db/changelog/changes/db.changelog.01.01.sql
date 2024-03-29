--liquibase formatted sql

--changeset task#4:1

CREATE TABLE IF NOT EXISTS game (
    id uuid PRIMARY KEY,
    stack bigint NOT NULL,
    buy_in bigint NOT NULL,
    bounty bigint,
    game_type varchar(20) NOT NULL
);

CREATE TABLE IF NOT EXISTS person (
	id uuid PRIMARY KEY,
	first_name varchar(100),
	last_name varchar(100)
);

CREATE TABLE IF NOT EXISTS prize_pool (
	game_id uuid UNIQUE REFERENCES game(id),
	schema jsonb NOT NULL
);

CREATE TABLE IF NOT EXISTS entries (
	game_id uuid REFERENCES game(id),
	person_id uuid REFERENCES person(id),
	amount bigint NOT NULL,
	created_at timestamp with time zone NOT NULL
);

CREATE TABLE IF NOT EXISTS bounty (
	game_id uuid REFERENCES game(id),
	from_person uuid REFERENCES person(id),
	to_person uuid REFERENCES person(id),
    created_at timestamp with time zone NOT NULL
);

CREATE TABLE IF NOT EXISTS finale_places (
	game_id uuid REFERENCES game(id),
	person_id uuid REFERENCES person(id),
	position int NOT NULL,
	UNIQUE(game_id, person_id),
	UNIQUE(game_id, position)
);