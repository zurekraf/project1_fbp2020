
INSERT INTO application_user (password, username) VALUES
("$2a$04$SrnKUTXBAre7Awf8dX098elHmeciAr21yR8aXj6yMY8LbX/HptTbS", "admin"),
("$2a$04$0i5/XQwZKgKFQznfy4S4/e1x.ammu60gBQQQahFp5wr45ZI1RaTfm", "ken"),
("$2a$10$ZoOKNKGg/VhdQSI2h.bW.eBT.14IwTHgA/6iluNcqCYWMDzSNSSrW", "barbie"),
("$2a$10$NA2Lb9ogoVc8g0CgptA1d.iX7g.P4VRbsnXc/mpCHavby6tD1tvUO", "judge1"),
("$2a$10$Gzref6k2CTGYqEnHc5JBzOHUnB1oZ95dqaCr62ZNhVXKaWE0le7GK", "prosecutor1");

INSERT INTO role (name) VALUES
("ADMIN"),
("JUDGE"),
("PROSECUTOR"),
("DEFENDER");

INSERT INTO defender (first_name, last_name, id_number, phone_number, office_address, applicationuser_id) VALUES
("Barbara", "Roberts", 111, "7444488", "15Av 12B", 3),
("Kenneth", "Carson", 222, "7444477", "12Av 11F", 2);

INSERT INTO user_roles (user_id, role_id) VALUES
(1, 1),
(2, 4),
(3, 4),
(4, 2),
(5, 3);

INSERT INTO accused (first_name, last_name, age) VALUES
("Ernest", "Evilman", 33),
("Rupert", "Rupertovic", 34),
("Ala", "Makota", 27),
("Kot", "Maala", 57);

INSERT INTO charge (title, sub_title) VALUES
("Arson", "Arson of occupied property"),
("Conspiracy", "Conspiracy for theft"),
("Burglary", "Burglary with the intent to commit a crime inside"),
("Pyramid Schemes", "Chain referral sales"),
("Cyberbullying", "Bullying through the use of technology or any electronic communication"),
("Domestic Violence", "Physical abuse"),
("Extortion", "Extortion"),
("White Collar Crime", "Mortgage fraud");

INSERT INTO court_case (case_code, accused_id, defender_id) VALUES
("99A", 1, 1),
("99B", 2, 2);

INSERT INTO courtcase_charges (courtcase_id, charge_id) VALUES
(1, 1),
(1, 2),
(2, 1);

INSERT INTO hearing (courtroom, is_public, hearing_date, courtcase_id) VALUES
("12B", true, '2011-12-18 13:17:17', 1),
("12B", true, '2012-12-18 13:17:17', 1),
("12A", true, '2013-12-18 13:17:17', 1),
("9H", false, '2011-12-18 13:17:17', 2);
