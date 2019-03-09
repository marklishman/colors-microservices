CREATE SCHEMA IF NOT EXISTS green;

DROP TABLE IF EXISTS details;

CREATE TABLE details (
  id                serial primary key,
  uuid              varchar(100) not null unique,
  correlation_id    varchar(100) not null unique,
  details           varchar(1000) not null
);
