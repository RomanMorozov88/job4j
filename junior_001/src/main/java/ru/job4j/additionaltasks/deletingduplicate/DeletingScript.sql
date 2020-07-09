create database citiesDB;

create table cities (
	id serial primary key,
	name varchar(50)
);

insert into cities (name) values ('Москва');
insert into cities (name) values ('Москва');
insert into cities (name) values ('Санкт-Петербург');
insert into cities (name) values ('Москва');
insert into cities (name) values ('Казань');

delete from cities as F
where F.id not in (select min from (
	select min(id), name from cities group by(name)
	) as S);