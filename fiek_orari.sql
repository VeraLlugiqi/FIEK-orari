create database knk_orari;
use knk_orari;

create table user (
uid integer,
firstname varchar(50),
lastname varchar(50),
email varchar(100),
dept varchar(50),
admin integer,
password varchar(250),
primary key(uid));


create table subject (
id integer,
name varchar(80),
primary key(id));




create table  schedule (
sid integer,
timestamp time,
day varchar(20),
classroom varchar(100),
available integer,
pid integer,
primary key(sid),
foreign key (pid) references subject(id));


insert into subject(id,name)
values 
(1, 'Arkitektura e kompjutereve'),
(2, 'Elektronika'),
(3,'Matematika Diskrete dhe Probabiliteti'),
(4,'POO'),
(5,'Baza e te Dhenave'),
(6, 'Programimi ne Ueb l'),
(7, 'Sisteme Operative'),
(8,'Siguria e te dhenave'),
(9,'Inxhinieria Kompjuterike'),
(10,'KNK'),
(11, 'Programimi ne Ueb ll'),
(12, 'Buxheti dhe analiza e shpezimeve');

