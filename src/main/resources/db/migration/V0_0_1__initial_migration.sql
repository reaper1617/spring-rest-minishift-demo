create schema if not exists test;

create table if not exists test.users
(
    version  int     not null,
    id       serial primary key,
    name varchar not null
);