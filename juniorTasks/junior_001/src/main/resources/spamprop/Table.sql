-- DB name: spammer

create table users
(
    user_id    SERIAL primary key,
    user_name  CHARACTER VARYING(100),
    user_email CHARACTER VARYING(100)
);