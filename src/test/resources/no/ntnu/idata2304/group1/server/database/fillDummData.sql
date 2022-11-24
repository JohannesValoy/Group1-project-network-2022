--- Some test rooms ---
INSERT INTO rooms (RoomName) VALUES 
('C220'),
("C231"),
("C450"),
("C451"),
("C453"),
("C452"),
("C454"),
("C455"),
("C456");

--- A bunch of sensor nodes that is supposed to send data to the server ---
--- the key is a api key that is used to identify the sensor node so it ineeds to be uniqe for each node---

INSERT INTO NODES(Nodename,key,roomID,type) VALUES 
('Pete','abcdefhijklm',1,'Temperature'),
('Rasp91','123456789000',2,'Temperature'),
('Ard3',"dskfsafsjfs7",3,'Temperature'),
("Rasp21", "ioffsagapfap",4,"Temperature")
;

-- Some dummy data as logs for the rooms --
INSERT INTO LOGS (roomID,timestamp,reading,nodeID) VALUES 
(1,'2022-01-10 00:00:00',20,1),
(2,'2022-01-10 00:00:00',20,2),
(3,'2022-01-10 00:00:00',20,3),
(4,'2022-01-10 00:00:30',21,4),
(1,'2022-01-10 00:01:00',18,1),
(2,'2022-01-10 00:01:00',20,2),
(3,'2022-01-10 00:01:00',22,3),
(4,'2022-01-10 00:01:00',23,4);