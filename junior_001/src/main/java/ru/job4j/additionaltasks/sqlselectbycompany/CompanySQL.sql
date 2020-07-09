CREATE DATABASE company;

CREATE TABLE company(
    id integer NOT NULL,
    name character varying,
    CONSTRAINT company_pkey PRIMARY KEY (id)
);

CREATE TABLE person(
    id integer NOT NULL,
    name character varying,
    company_id integer,
    CONSTRAINT person_pkey PRIMARY KEY (id)
);

--names of all persons that are NOT in the company with id = 5
select name from person where company_id <> 5;

--company name for each person
select person.name, company.name
from person
inner join company on person.company_id = company.id;

--Select the name of the company with the maximum number of persons + number of persons in this company
select CMPNAME, TOTAL from (
select company.name as CMPNAME, count(company_id) as TOTAL from person
left outer join company on company.id = person.company_id
group by(company.name)
) as SUB1 where TOTAL = (
select max(TOTAL) from (
select company_id, count(company_id) as TOTAL from person
group by(company_id)
) as SUB2
);