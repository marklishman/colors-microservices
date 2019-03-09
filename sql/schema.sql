CREATE SCHEMA IF NOT EXISTS colors;

DROP TABLE IF EXISTS color;

CREATE TABLE color (
  id                serial primary key,
  correlation_id    varchar(100) not null,
  color_name        varchar(100) not null,
  color_instance    varchar(100) not null,
  details           varchar(1000) not null,
  unique (correlation_id, color_name, color_instance)
);
