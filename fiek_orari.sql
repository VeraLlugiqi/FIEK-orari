create database knk_orari;
use knk_orari;


create table user (
uid integer not null auto_increment,   # numri i karteles se profesorit apo administatorit
firstName varchar(50) not null,
lastName varchar(50) not null,
idNumber varchar(100) not null,
password varchar(250)not null,
salt varchar(250) not null,
primary key(uid));

create table subject (
id integer not null,
name varchar(80) not null,
primary key(id));



create table schedule (
sid integer,
timestamp time not null,
day varchar(20) not null,
classroom varchar(100) ,
available integer,
pid integer ,
primary key(sid),
foreign key (pid) references subject(id));


create table professor_subject (
  professor_id int not null,
  subject_id int not null,
  primary key (professor_id, subject_id),
  foreign key (professor_id) references user(uid),
 foreign key (subject_id) references subject(id)
);






insert into subject(id,name)
values 
(201, 'Arkitektura e kompjutereve'),
(202, 'Elektronika'),
(203,'Matematika Diskrete dhe Probabiliteti'),
(204,'POO'),
(205,'Baza e te Dhenave'),
(206, 'Programimi ne Ueb l'),
(207, 'Sisteme Operative'),
(208,'Siguria e te dhenave'),
(209,'Inxhinieria Kompjuterike'),
(210,'KNK'),
(211, 'Programimi ne Ueb ll'),
(212, 'Buxheti dhe analiza e shpezimeve');



insert into schedule(sid, timestamp, day, classroom, available, pid)
values
('1001','08:00', 'E hene', NULL,'0',NULL),
('1002','09:30', 'E hene', NULL,'0',NULL),
('1003','11:00', 'E hene', NULL,'0',NULL),
('1004','12:30', 'E hene', NULL,'0',NULL),
('1005','14:00', 'E hene', NULL,'0',NULL),
('1006','15:30', 'E hene', NULL,'0',NULL),
('1007','17:00', 'E hene', NULL,'0',NULL),

('1008','08:00', 'E marte', NULL,'0',NULL),
('1009','09:30', 'E marte', NULL,'0',NULL),
('1010','11:00', 'E marte', NULL,'0',NULL),
('1011','12:30', 'E marte', NULL,'0',NULL),
('1012','14:00', 'E marte', NULL,'0',NULL),
('1013','15:30', 'E marte', NULL,'0',NULL),
('1014','17:00', 'E marte', NULL,'0',NULL),

('1015','08:00', 'E merkure', NULL,'0',NULL),
('1016','09:30', 'E merkure', NULL,'0',NULL),
('1017','11:00', 'E merkure', NULL,'0',NULL),
('1018','12:30', 'E merkure', NULL,'0',NULL),
('1019','14:00', 'E merkure', NULL,'0',NULL),
('1020','15:30', 'E merkure', NULL,'0',NULL),
('1021','17:00', 'E merkure', NULL,'0',NULL),

('1022','08:00', 'E enjte', NULL,'0',NULL),
('1023','09:30', 'E enjte', NULL,'0',NULL),
('1024','11:00', 'E enjte', NULL,'0',NULL),
('1025','12:30', 'E enjte', NULL,'0',NULL),
('1026','14:00', 'E enjte', NULL,'0',NULL),
('1027','15:30', 'E enjte', NULL,'0',NULL),
('1028','17:00', 'E enjte', NULL,'0',NULL),

('1029','08:00', 'E premte', NULL,'0',NULL),
('1030','09:30', 'E premte', NULL,'0',NULL),
('1031','11:00', 'E premte', NULL,'0',NULL),
('1032','12:30', 'E premte', NULL,'0',NULL),
('1033','14:00', 'E premte', NULL,'0',NULL),
('1034','15:30', 'E premte', NULL,'0',NULL),
('1035','17:00', 'E premte', NULL,'0',NULL);