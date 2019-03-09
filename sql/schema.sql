CREATE SCHEMA IF NOT EXISTS colors;

DROP TABLE IF EXISTS color;

CREATE TABLE color (
  id          serial primary key,
  color_id    varchar(100) not null,
  color_name  varchar(100) not null,
  details     varchar(1000) not null
);
