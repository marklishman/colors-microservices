CREATE SCHEMA IF NOT EXISTS green;


DROP TABLE IF EXISTS green.notes;
DROP TABLE IF EXISTS green.user;

CREATE TABLE green.user
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

CREATE TABLE green.notes
(
    nte_id SERIAL PRIMARY KEY,
    usr_id INTEGER NOT NULL REFERENCES green.user,
    nte_text VARCHAR(4000) NOT NULL
)
