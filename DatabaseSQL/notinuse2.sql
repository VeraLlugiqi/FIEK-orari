create database knk_orari;
use knk_orari;


INSERT INTO USER( firstName,lastName,idNumber,password,salt)
VALUES
('Blend','Arifaj','121623',null,null),
('Isak','Shabani','121514',null,null),
('Adrian','Ymeri','121920',null,null),
('Dhurate','Hyseni','121001',null,null),
('Artan','Mazrekaj','122020',null,null),
('Blerim','Rexha','122121',null,null),
('Valon','Raca','120890',null,null),
('Avni','Rexhepi','120670',null,null),
('Vjosa','Shatri','121515',null,null),
('Bahri','Prebeza','121678',null,null),
('Valon','Veliu','121517',null,null),
('Dalina','Vranovci','121212',null,null),
('Synim','Selimi','121819',null,null),
('Mergim','Hoti','122020',null,null),
('Arbnor','Halili','122254',null,null),
('Marjan','Dema','122301',null,null);


INSERT INTO schedule(sid, timestamp, day)
VALUES
('1001','08:00', 'E hene'),
('1002','09:30', 'E hene'),
('1003','11:00', 'E hene'),
('1004','12:30', 'E hene'),
('1005','14:00', 'E hene'),
('1006','15:30', 'E hene'),
('1007','17:00', 'E hene'),

('1008','08:00', 'E marte'),
('1009','09:30', 'E marte'),
('1010','11:00', 'E marte'),
('1011','12:30', 'E marte'),
('1012','14:00', 'E marte'),
('1013','15:30', 'E marte'),
('1014','17:00', 'E marte'),

('1015','08:00', 'E merkure'),
('1016','09:30', 'E merkure'),
('1017','11:00', 'E merkure'),
('1018','12:30', 'E merkure'),
('1019','14:00', 'E merkure'),
('1020','15:30', 'E merkure'),
('1021','17:00', 'E merkure'),

('1022','08:00', 'E enjte'),
('1023','09:30', 'E enjte'),
('1024','11:00', 'E enjte'),
('1025','12:30', 'E enjte'),
('1026','14:00', 'E enjte'),
('1027','15:30', 'E enjte'),
('1028','17:00', 'E enjte'),

('1029','08:00', 'E premte'),
('1030','09:30', 'E premte'),
('1031','11:00', 'E premte'),
('1032','12:30', 'E premte'),
('1033','14:00', 'E premte'),
('1034','15:30', 'E premte'),
('1035','17:00', 'E premte');


INSERT INTO class(classname)
VALUES
("A408"),
("A411"),
("201"),
("310"),
("311"),
("611"),
("621"),
("626"),
("615"),
("629"),
("636");



INSERT INTO schedule_class (sid, cid, available)
SELECT schedule.sid, class.cid, 0
FROM schedule, class;

INSERT INTO subject(name,semestri )
VALUES
('Matematika ll',2),
('Fizika ll',2),
('Bazat e inxhinierise elektrike ll',2),
('Algoritmet dhe Strukturat e te dhenave',2),
('Qarqe digjitale',2),
('Sisteme Operative',4),
('Siguria e te dhenave',4),
('Inxhinieria Softuerike',4),
('KNK',4),
('Programimi ne Ueb ll',4),
('Buxheti dhe analiza e shpezimeve',4),
('Sistemet e shperndara', 6),
('Siguria ne Internet',6),
('Parallel Computing',6),
('Cloud Computing',6),
('Big Data',6),
('Biometrics & Forensics',6);



INSERT INTO professor_subject(professor_id, subject_id, availableProfessorSubject)
VALUES
('1', '9', 0),
('1', '12', 0),
('2', '9', 0),
('3', '4', 0),
('3', '17', 0),
('4', '11', 0),
('4', '12', 0),
('5', '5', 0),
('5', '6', 0),
('5', '15', 0),
('6', '7', 0),
('6', '8', 0),
('6', '13', 0),
('7', '14', 0),
('8', '4', 0),
('8', '16', 0),
('9', '3', 0),
('10', '11', 0),
('11', '2', 0),
('12', '5', 0),
('12', '6', 0),
('12', '10', 0),
('12', '16', 0),
('13', '5', 0),
('13','14',0),
('14','7',0),
('14','13',0),
('15','8',0),
('16','1',0);



select * from professor_subject inner join orarizgjedhur on professor_subject.professor_id = orarizgjedhur.idNumber
inner join subject on professor_subject.subject_id = subject.id
where subject.semestri = 4 and availableOrariZgjedhur = 1;

