create database knk_orari;
use knk_orari;


create table user (
uid integer not null auto_increment,   # numri i karteles se profesorit apo administatorit
firstName varchar(50) not null,
lastName varchar(50) not null,
idNumber varchar(100) not null,
password varchar(250),
salt varchar(250),
primary key(uid)
);

create table class (
cid int auto_increment,
classname varchar(20),
available integer,
primary key(cid)
);




create table subject (
id integer not null auto_increment,
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
id int not null auto_increment,
  professor_id int not null,
  subject_id int not null,
  primary key (id),
  foreign key (professor_id) references user(uid),
 foreign key (subject_id) references subject(id)
);

# te tabela fillimi dropdown Lenda 
#paraqitja e lendeve te profesoreve 
select s.name from subject s
inner join professor_subject ps on s.id = ps.subject_id
inner join user u
on u.uid = ps.professor_id
where u.firstName = "Blerim";




select * from user;

insert into user( firstName,lastName,idNumber,password,salt)
values
('Isak','Shabani','121514',null,null),
('Lule','Ahmeti','121920',null,null),
('Dhurate','Hyseni','121001',null,null),
('Artan','Mazrekaj','122020',null,null),
('Blerim','Rexha','122121',null,null),
('Valon','Raca','120890',null,null),
('Milaim','Zabeli','120670',null,null),
('Qefsere','Gjonbalaj','121515',null,null),
('Bahri','Prebeza','121678',null,null),
('Qamil','Kabashi','121517',null,null),
('Blend','Arifaj','121623',null,null),
('Dalina','Vranovci','121212',null,null),
('Synim','Selimi','121819',null,null),
('Mergim','Hoti','122020',null,null),
('Arbnor','Halili','122254',null,null),
('Ramadan','Plakolli','122301',null,null);



select * from professor_subject;

insert into professor_subject(professor_id, subject_id)
values 
( '1', '15'),   #Isaku
( '1', '21'),
( '2', '24'),   #Lule
( '3', '17'),  #Dhurata
( '3', '22'),
( '4', '11'),  #Artan
( '4', '18'),
( '5', '9'),  #Blerim
( '5', '19'),
('5','20'),
( '6', '12'),  #Valon
( '7', '13'),  #Milaim
( '8', '14'),  #Qefsere
( '9', '23'),  #Bahri
( '10', '13'), #Qamil
( '11', '17'), #Blendi
( '11', '15'),
( '11', '21'),
( '12', '17'), #Dalina
( '12', '18'),
( '12', '24'),
( '13', '12'),  #Synim
( '14', '19'),  #Mergim
( '15', '20'),   #Arbnor
( '16', '14');  #Ramadan





select * from subject;

insert into subject(name)
values 
( 'Matematika l'),
( 'MAtematika ll'),
(  'Fizika l'),
( 'Fizika ll'),
( 'Bazat e inxhinierise elektrike l'),
( 'Bazat e inxhinierise elektrike ll'),
( 'Programimi ne C++'),
( 'Algoritme dhe Struktura e te dhenave'),
( 'Shkathtesi Komunikuese'),
( 'Praktikum ne matematike'),
( 'Carçe digjitle'),
('Arkitektura e kompjutereve'),
('Elektronika'),
('Matematika Diskrete dhe Probabiliteti'),
('POO'),
('Baza e te Dhenave'),
('Programimi ne Ueb l'),
('Sisteme Operative'),
('Siguria e te dhenave'),
('Inxhinieria Kompjuterike'),
('KNK'),
('Programimi ne Ueb ll'),
('Buxheti dhe analiza e shpezimeve'),
('Bazat e te dhenave');


insert into class(classname)
values
("A408"),
("A411"),
("201"),
("310"),
("311"),
("611"),
("621"),
("626"),
('615'),
("629"),
("636");


select * from schedule;
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


#---------------------------------------------------------------------------------------
#TABELA SCHEDULE E UPDATEUAR!!!

drop table schedule;
create table schedule (
sid varchar(4),
timestamp varchar(20) not null,
day varchar(20) not null,
available integer,
cid integer,
primary key(sid)
foreign key(cid) references class(cid) auto_increment;

insert into schedule(sid, timestamp, day, available)
values
('1001','08:00', 'E hene','0'),
('1002','09:30', 'E hene', '0'),
('1003','11:00', 'E hene','0'),
('1004','12:30', 'E hene','0'),
('1005','14:00', 'E hene','0'),
('1006','15:30', 'E hene','0'),
('1007','17:00', 'E hene','0'),

('1008','08:00', 'E marte','0'),
('1009','09:30', 'E marte','0'),
('1010','11:00', 'E marte','0'),
('1011','12:30', 'E marte','0'),
('1012','14:00', 'E marte','0'),
('1013','15:30', 'E marte','0'),
('1014','17:00', 'E marte','0'),

('1015','08:00', 'E merkure','0'),
('1016','09:30', 'E merkure','0'),
('1017','11:00', 'E merkure','0'),
('1018','12:30', 'E merkure','0'),
('1019','14:00', 'E merkure','0'),
('1020','15:30', 'E merkure','0'),
('1021','17:00', 'E merkure','0'),

('1022','08:00', 'E enjte','0'),
('1023','09:30', 'E enjte','0'),
('1024','11:00', 'E enjte','0'),
('1025','12:30', 'E enjte','0'),
('1026','14:00', 'E enjte','0'),
('1027','15:30', 'E enjte','0'),
('1028','17:00', 'E enjte','0'),

('1029','08:00', 'E premte','0'),
('1030','09:30', 'E premte','0'),
('1031','11:00', 'E premte','0'),
('1032','12:30', 'E premte','0'),
('1033','14:00', 'E premte','0'),
('1034','15:30', 'E premte','0'),
('1035','17:00', 'E premte','0');



#tabela e re
create table orariZgjedhur(
oid integer auto_increment,
sid varchar(4),
idNumber varchar(100),
salla varchar(10),
lenda varchar(100),
timestamp varchar(20) not null,
day varchar(20) not null,
available integer,
primary key(oid),
foreign key(sid) references schedule(sid),
foreign key(cid) references class(cid)
);
create table orariZgjedhur(
oid integer auto_increment,
sid varchar(4),
userId varchar(100),
ps integer
primary key(oid),
foreign key(sid) references schedule(sid)
foreign key(userID) references user(uid)
foreign key(ps) references profesor_subject(id)
);




#---------------------------------------------------------------------
use knk_orari;
drop table class;

create table class (
cid int auto_increment,
classname varchar(20),
available integer,
primary key(cid)
);

insert into class(classname, available)
values
("A408", '0'),
("A411", '0'),
("201", '0'),
("310", '0'),
("311", '0'),
("611", '0'),
("621", '0'),
("626", '0'),
('615', '0'),
("629", '0'),
("636", '0');



