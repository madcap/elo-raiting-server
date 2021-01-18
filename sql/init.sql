
create database rating;

--create user ratingapi with password 'PASSWORD_HERE';
-- for local docker image no password is specified
create user ratingapi;

grant connect on database postgres to ratingapi;

create schema if not exists rating;

grant usage on schema rating to ratingapi;

create table if not exists rating.players (
        id uuid primary key,
        domain varchar(128),
        name varchar(128),
        description varchar(1024),
        rating numeric(12,3) default 1500.0,
        wins integer default 0,
        losses integer default 0
);

grant select, insert, update, delete on rating.players to ratingapi;

insert into rating.players (id, domain, name, description) values ('2bf9d469-87ba-464d-89eb-0e875b02a38b', 'test', 'Joey Test', '1st player for testing purposes');
insert into rating.players (id, domain, name, description) values ('a715a75f-82fc-4dde-af4d-20992bb5de35', 'test', 'Jane Test', '2nd player for testing purposes');
insert into rating.players (id, domain, name, description) values ('5ef8a010-35bf-4e1f-86ae-68009f894264', 'test', 'John Test', '3rd player for testing purposes');
insert into rating.players (id, domain, name, description) values ('fc017516-dccc-4bfb-9055-1703d2c9ad42', 'test', 'Judy Test', '4th player for testing purposes');

create table if not exists rating.matches (
        id uuid primary key,
        domain varchar(128),
        player_id_1 varchar(128),
        player_id_2 varchar(128)
);

grant select, insert, update, delete on rating.matches to ratingapi;

insert into rating.matches (id, domain, player_id_1, player_id_2) values (
        'd3315fca-3b7a-48bc-9db8-de5b9fe908ef',
        'test',
        '2bf9d469-87ba-464d-89eb-0e875b02a38b',
        'a715a75f-82fc-4dde-af4d-20992bb5de35'
);