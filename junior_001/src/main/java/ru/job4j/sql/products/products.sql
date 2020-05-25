create database products;

create table products_type (
	id serial primary key,
	name_type varchar(50)
);
create table product (
	id serial primary key,
	name_product varchar(50),
	products_type_id integer references products_type(id),
	expired_date date,
	price float,
	amount int
);

insert into product(name_product, products_type_id, expired_date, price, amount)
values('сыр пармезан', 1, '2019-06-01', 10.50, 4);
insert into product(name_product, products_type_id, expired_date, price, amount)
values('сыр чеддер', 1, '2019-06-15', 15.20, 5);

insert into product(name_product, products_type_id, expired_date, price, amount)
values('молоко 2,5%', 2, '2019-05-05', 8.55, 3);
insert into product(name_product, products_type_id, expired_date, price, amount)
values('молоко 3,2%', 2, '2019-04-30', 9.45, 7);

insert into product(name_product, products_type_id, expired_date, price, amount)
values('ванильное мороженное', 3, '2019-06-25', 5.34, 2);
insert into product(name_product, products_type_id, expired_date, price, amount)
values('фруктовое мороженное', 3, '2019-06-04', 6.80, 6);

--Написать запрос получение всех продуктов с типом "СЫР".
select name_product, price, expired_date from product where products_type_id = 1;

--Написать запрос получения всех продуктов, у кого в имени есть слово "мороженное".
select name_product, price, expired_date from product where name_product like '%мороженное%';

--Написать запрос, который выводит все продукты, срок годности которых заканчивается в следующем месяце.
select name_product, expired_date from product
where (date_part('month', expired_date) - date_part('month', current_date)) = 1;

--Дополнительно- запрос, который выводит все продукты, срок годности которых истекает менее чем через месяц.
select name_product, expired_date from product
where (expired_date - current_date) < 30;

--Написать запрос, который выводит самый дорогой продукт.
select name_product, price from product where price = (select max(price) from product);

--Написать запрос, который выводит количество всех продуктов определенного типа. В данном случаем- молоко.
select PT.name_type, sum(amount)
from product as PR
inner join products_type as PT on PR.products_type_id = PT.id
where PR.products_type_id = 2
group by(PT.name_type);

--Написать запрос получение всех продуктов с типом "СЫР" и "МОЛОКО".
select name_product, price, expired_date from product where products_type_id = 1 or products_type_id = 2;

--Написать запрос, который выводит тип продуктов, которых осталось меньше 10 штук.
select PTNT, TOTAL
from (
select PT.name_type as PTNT, sum(amount) as TOTAL
from product as PR
inner join products_type as PT on PR.products_type_id = PT.id
group by (PT.name_type)
) as SUB
where TOTAL < 10;

--Вывести все продукты и их тип.
select pr.name_product, pt.name_type from product as pr inner join products_type as pt on pr.products_type_id = pt.id;