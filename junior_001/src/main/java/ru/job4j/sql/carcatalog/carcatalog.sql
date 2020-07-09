create database car_catalog;

create table engine (
	id serial primary key,
	name_engine varchar(50)
);
create table carbody (
	id serial primary key,
	name_carbody varchar(50)
);
create table transmission (
	id serial primary key,
	name_transmission varchar(50)
);
create table car (
	id serial primary key,
	name_car varchar(50),
	id_engine integer references engine(id),
	id_carbody integer references carbody(id),
	id_transmission integer references transmission(id)
);
--Заполняем таблицу engine.
insert into engine(name_engine) values('v2');
insert into engine(name_engine) values('v4');
insert into engine(name_engine) values('v6');
insert into engine(name_engine) values('v8');
--Заполняем таблицу carbody.
insert into carbody(name_carbody) values('sedan');
insert into carbody(name_carbody) values('truck');
insert into carbody(name_carbody) values('coupe');
insert into carbody(name_carbody) values('van');
--Заполняем таблицу transmission.
insert into transmission(name_transmission) values('AT');
insert into transmission(name_transmission) values('MT');
insert into transmission(name_transmission) values('CVT');
--Заполняем таблицу car.
insert into car(name_car, id_engine, id_carbody, id_transmission) values('First', 2, 1, 2);
insert into car(name_car, id_engine, id_carbody, id_transmission) values('Second', 3, 4, 1);
--Получаем список автомобилей с установленными в них деталями.
select CAR.name_car, ENGINE.name_engine, CARBODY.name_carbody, TRANSMISSION.name_transmission
from car as CAR
left outer join engine as ENGINE on CAR.id_engine = ENGINE.id
left outer join carbody as CARBODY on CAR.id_carbody = CARBODY.id
left outer join transmission as TRANSMISSION on CAR.id_transmission = TRANSMISSION.id;
--Получаем список неиспользуемых двигателей.
select ENGINE.name_engine from engine as ENGINE
left outer join car as CAR on CAR.id_engine = ENGINE.id where CAR.id is null;
--Получаем список неиспользуемых кузовов.
select CARBODY.name_carbody from carbody as CARBODY
left outer join car as CAR on CAR.id_carbody = CARBODY.id where CAR.id is null;
--Получаем список неиспользуемых коробок передач.
select TRANSMISSION.name_transmission from transmission as TRANSMISSION
left outer join car as CAR on CAR.id_transmission = TRANSMISSION.id where CAR.id is null;