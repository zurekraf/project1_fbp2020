INSERT INTO product (name, price ) VALUES
("ala", 44),
("makota", 33),
("kot", 22),
("maala", 11);

INSERT INTO application_user (password, username) VALUES
("$2a$04$SrnKUTXBAre7Awf8dX098elHmeciAr21yR8aXj6yMY8LbX/HptTbS", "admin"),
("$2a$04$mqAh2ORb/OhM5LgIQa0vLOFaQI6D3PKmCL52z3jTebjY9GDTEo5oa", "user"),
("$2a$04$0i5/XQwZKgKFQznfy4S4/e1x.ammu60gBQQQahFp5wr45ZI1RaTfm", "ken");

INSERT INTO role (name) VALUES
("ADMIN"),
("USER"),
("TEST");

INSERT INTO user_roles (user_id, role_id) VALUES
(1, 1),
(1, 2),
(2, 2),
(3, 3);

INSERT INTO defender (first_name, last_name, id_number, phone_number, office_address) VALUES
("Ala", "Makota", 111, "7444488", "15Av 12B"),
("Kot", "Maala", 222, "7444477", "12Av 11F");

INSERT INTO accused (first_name, last_name, age) VALUES
("Ernest", "Evilman", 33),
("Rupert", "Rupertovic", 34);

INSERT INTO court_case (case_code, accused_id, defender_id) VALUES
("99A", 1, 1),
("99B", 2, 2);

INSERT INTO charge (title, sub_title) VALUES
("Arson", "Arson of occupied property"),
("Conspiracy", "Conspiracy for theft");

INSERT INTO courtcase_charges (courtcase_id, charge_id) VALUES
(1, 1),
(1, 2),
(2, 1);

INSERT INTO sentence (description, possibility_of_parole, time_frame) VALUES
("Severe punishment for felony", false, 10.0);

INSERT INTO hearing (courtroom, is_public, hearing_date, courtcase_id) VALUES
("12B", true, '2011-12-18 13:17:17', 1),
("12B", true, '2012-12-18 13:17:17', 1),
("12A", true, '2013-12-18 13:17:17', 1),
("9H", false, '2011-12-18 13:17:17', 2);

