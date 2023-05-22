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


CREATE TABLE schedule (
  sid VARCHAR(4),
  timestamp VARCHAR(20) NOT NULL,
  day VARCHAR(20) NOT NULL,
  PRIMARY KEY (sid)
);


CREATE TABLE class (
  cid INT AUTO_INCREMENT,
  classname VARCHAR(20),
  uid INTEGER,
  sid varchar(4),
  PRIMARY KEY (cid),
  FOREIGN KEY (uid) REFERENCES user (uid),
  foreign key(sid) references schedule(sid)
);

create table schedule_class(
scid int auto_increment,
cid int,
sid varchar(4),
available int,
primary key(scid),
foreign key(cid) references class(cid),
foreign key(sid) references schedule(sid)
);

INSERT INTO schedule_class (sid, cid, available)
SELECT schedule.sid, class.cid, 0
FROM schedule, class;


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



create table subject (
id integer not null auto_increment,
name varchar(80) not null,
semestri int not null,
primary key(id)
);

create table professor_subject (
id int not null auto_increment,
  professor_id int not null,
  subject_id int not null,
  availableProfessorSubject int,
  primary key (id),
  foreign key (professor_id) references user(uid),
 foreign key (subject_id) references subject(id)
);




