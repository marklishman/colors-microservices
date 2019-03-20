CREATE SCHEMA IF NOT EXISTS green;

DROP TABLE IF EXISTS "data";
DROP TABLE IF EXISTS "item";
DROP TABLE IF EXISTS "category";
DROP TABLE IF EXISTS "group";

CREATE TABLE "group"
(
  grp_id   SERIAL PRIMARY KEY,
  grp_name VARCHAR(100)  NOT NULL UNIQUE,
  grp_desc VARCHAR(1000) NOT NULL
);


CREATE TABLE "category"
(
  cat_id   SERIAL PRIMARY KEY,
  cat_name VARCHAR(100)  NOT NULL UNIQUE,
  cat_desc VARCHAR(1000) NOT NULL
);


CREATE TABLE "item"
(
  itm_id             SERIAL PRIMARY KEY,
  grp_id             INTEGER       NOT NULL REFERENCES "group",
  itm_uuid           VARCHAR(100)  NOT NULL UNIQUE,
  itm_name           VARCHAR(100)  NOT NULL UNIQUE,
  itm_desc           VARCHAR(1000) NOT NULL,
  itm_correlation_id VARCHAR(100)  NOT NULL,
  itm_status         INTEGER       NOT NULL,
  itm_created_at     TIMESTAMP     NOT NULL
);


CREATE TABLE "data"
(
  dat_id             SERIAL PRIMARY KEY,
  itm_id             INTEGER       NOT NULL REFERENCES "item",
  cat_id             INTEGER       NOT NULL REFERENCES "category",
  dat_value          NUMERIC       NOT NULL,
  dat_created_at     TIMESTAMP     NOT NULL
);