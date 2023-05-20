CREATE DATABASE knk_orari;
use knk_orari;

CREATE TABLE class (
  cid INT AUTO_INCREMENT,
  classname VARCHAR(20),
  availableClass INTEGER,
  uid INTEGER,
  PRIMARY KEY (cid),
  FOREIGN KEY (uid) REFERENCES user (uid)
);

CREATE TABLE schedule (
  sid VARCHAR(4),
  timestamp VARCHAR(20) NOT NULL,
  day VARCHAR(20) NOT NULL,
  availableSchedule INTEGER,
  PRIMARY KEY (sid)
);

CREATE TABLE schedule_class (
  sid VARCHAR(4),
  cid INT,
  PRIMARY KEY (sid, cid),
  FOREIGN KEY (sid) REFERENCES schedule (sid),
  FOREIGN KEY (cid) REFERENCES class (cid)
);


create table subject (
id integer not null auto_increment,
name varchar(80) not null,
availableSubject integer,
primary key(id));

create table professor_subject (
id int not null auto_increment,
  professor_id int not null,
  subject_id int not null,
  primary key (id),
  foreign key (professor_id) references user(uid),
 foreign key (subject_id) references subject(id)
);

create table orarizgjedhur(
oid int AUTO_INCREMENT,
sid varchar(4) ,
idNumber varchar(100) ,
salla varchar(10) ,
lenda varchar(100) ,
timestamp varchar(20) ,
day varchar(20) ,
availableOrariZgjedhur int,
primary key(oid),
foreign key(sid) references schedule(sid)
);

create table user (
uid integer not null auto_increment,   # numri i karteles se profesorit apo administatorit
firstName varchar(50) not null,
lastName varchar(50) not null,
idNumber varchar(100) not null,
password varchar(250),
salt varchar(250),
primary key(uid)
);



#--------------------VALUES---------------------------

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

insert into subject(name, availableSubject)
values
( 'Matematika l', 0),
( 'MAtematika ll', 0),
(  'Fizika l', 0),
( 'Fizika ll', 0),
( 'Bazat e inxhinierise elektrike l', 0),
( 'Bazat e inxhinierise elektrike ll', 0),
( 'Programimi ne C++', 0),
( 'Algoritme dhe Struktura e te dhenave', 0),
( 'Shkathtesi Komunikuese', 0),
( 'Praktikum ne matematike', 0),
( 'Carçe digjitle', 0),
('Arkitektura e kompjutereve', 0),
('Elektronika', 0),
('Matematika Diskrete dhe Probabiliteti', 0),
('POO', 0),
('Baza e te Dhenave', 0),
('Programimi ne Ueb l', 0),
('Sisteme Operative', 0),
('Siguria e te dhenave', 0),
('Inxhinieria Kompjuterike', 0),
('KNK', 0),
('Programimi ne Ueb ll', 0),
('Buxheti dhe analiza e shpezimeve', 0),
('Bazat e te dhenave', 0);

insert into class(classname, availableClass)
values
("A408", "0"),
("A411", 0),
("201", 0),
("310", 0),
("311", 0),
("611", 0),
("621", 0),
("626", 0),
('615', 0),
("629", 0),
("636", 0);

insert into schedule(sid, timestamp, day, availableSchedule)
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


insert into professor_subject(professor_id, subject_id)
values
( '1', '15'),
( '1', '21'),
( '2', '23'),
( '3', '16'),
( '3', '21'),
( '4', '17'),
( '5', '9'),
( '5', '19'),
('5','20'),
( '6', '11'),
( '7', '12'),
( '8', '14'),
( '9', '10'),
( '10', '14'),
( '11', '16'),
( '11', '18'),
( '11', '22'),
( '12', '16'),
( '12', '23'),
( '12', '18'),
( '13', '12'),
( '14', '19'),
( '15', '20'),
( '16', '13');