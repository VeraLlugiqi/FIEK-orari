INSERT INTO USER( firstName,lastName,idNumber,password,salt)
VALUES
('Blend','Arifaj','121623',null,null),
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
('Dalina','Vranovci','121212',null,null),
('Synim','Selimi','121819',null,null),
('Mergim','Hoti','122020',null,null),
('Arbnor','Halili','122254',null,null),
('Ramadan','Plakolli','122301',null,null);


INSERT INTO subject(name)
VALUES
('Matematika l'),
('Matematika ll'),
('Fizika l'),
('Fizika ll'),
('Bazat e inxhinierise elektrike l'),
('Bazat e inxhinierise elektrike ll'),
('Programimi ne C++'),
('Algoritme dhe Struktura e te dhenave'),
('Shkathtesi Komunikuese'),
('Praktikum ne matematike'),
('Qarqe digjitale'),
('Arkitektura e kompjutereve'),
('Elektronika'),
('Matematika Diskrete dhe Probabiliteti'),
('POO'),
('Baza e te Dhenave'),
('Programimi ne Ueb l'),
('Sisteme Operative'),
('Siguria e te dhenave'),
('Inxhinieria Softuerike'),
('KNK'),
('Programimi ne Ueb ll'),
('Buxheti dhe analiza e shpezimeve'),
('Bazat e te dhenave');

INSERT INTO professor_subject(professor_id, subject_id, availableProfessorSubject)
VALUES
('1', '15', 0),
('1', '21', 0),
('2', '23', 0),
('3', '16', 0),
('3', '21', 0),
('4', '17', 0),
('5', '9', 0),
('5', '19', 0),
('5', '20', 0),
('6', '11', 0),
('7', '12', 0),
('8', '14', 0),
('9', '10', 0),
('10', '14', 0),
('11', '16', 0),
('11', '18', 0),
('11', '22', 0),
('12', '16', 0),
('12', '23', 0),
('12', '18', 0),
('13', '12', 0),
('14', '19', 0),
('15', '20', 0),
('16', '13', 0);


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

#Merr te gjitha kombinimet nga schedule dhe class tabelat
INSERT INTO schedule_class (sid, cid, available)
SELECT schedule.sid, class.cid, 0
FROM schedule, class;

