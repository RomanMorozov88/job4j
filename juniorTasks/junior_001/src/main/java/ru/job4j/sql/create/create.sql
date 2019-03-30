create database requests;

create table roles (
	id serial primary key,
	name_roles varchar(50)
);
create table rules (
	id serial primary key,
	name_rules varchar(50)
);
create table rulesForRoles (
	roles_id integer references roles(id),
	rules_id integer references rules(id)
);
create table users (
	id serial primary key,
	name_users varchar(50),
	roles_id integer references roles(id)
);
create table category (
	id serial primary key,
	name_category varchar(50)
);
create table states (
	id serial primary key,
	name_states varchar(50)
);
create table items (
	id serial primary key,
	name_items varchar(50),
	users_id integer references users(id),
	category_id integer references category(id),
	states_id integer references states(id)
);
create table attachs (
	id serial primary key,
	name_attachs varchar(50),
	items_id integer references items(id)
);
create table commentaries (
	id serial primary key,
	name_commentaries  varchar(50),
	items_id integer references items(id)
);

insert into rules (id, name_rules) values
	(1, 'add'), (2, 'delete'),
	(3, 'change_item'), (4, 'change_status'),
	(5, 'add_comment'), (6, 'add_attach'),
	(7, 'ban');
insert into roles (id, name_roles) values
	(1, 'Admin'),
	(2, 'User');
insert into rulesForRoles (roles_id, rules_id) values
	(1, 1), (1, 2), (1, 3), (1, 4),
	(1, 5), (1, 6), (1, 7), (2, 1),
	(2, 2), (2, 3), (2, 5), (2, 6);