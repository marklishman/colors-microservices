CREATE SCHEMA IF NOT EXISTS green_test;


DROP TABLE IF EXISTS green_test.user;
DROP SEQUENCE IF EXISTS green_test.user_usr_id_seq;

CREATE TABLE green_test.user
(
    usr_id           SERIAL PRIMARY KEY,
    usr_first_name   VARCHAR(200) NOT NULL,
    usr_last_name    VARCHAR(200) NOT NULL,
    usr_user_name    VARCHAR(100) NOT NULL UNIQUE,
    usr_email        VARCHAR(500) NOT NULL UNIQUE,
    usr_phone_number VARCHAR(100) NOT NULL,
    usr_age          INTEGER NOT NULL,
    usr_website      VARCHAR(500)
);
