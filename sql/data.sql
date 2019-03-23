
DELETE FROM "data";
DELETE FROM "item";
DELETE FROM "group";
DELETE FROM "category";

INSERT INTO "group" (grp_name, grp_desc) VALUES ('Group One', 'Group one description');
INSERT INTO "group" (grp_name, grp_desc) VALUES ('Group Two', 'Group two description');
INSERT INTO "group" (grp_name, grp_desc) VALUES ('Group Three', 'Group three description');
INSERT INTO "group" (grp_name, grp_desc) VALUES ('Group Four', 'Group four description');

INSERT INTO "category" (cat_name, cat_desc) VALUES ('Category One', 'Category one description');
INSERT INTO "category" (cat_name, cat_desc) VALUES ('Category Two', 'Category two description');
INSERT INTO "category" (cat_name, cat_desc) VALUES ('Category Three', 'Category three description');
INSERT INTO "category" (cat_name, cat_desc) VALUES ('Category Four', 'Category four description');
INSERT INTO "category" (cat_name, cat_desc) VALUES ('Category Five', 'Category five description');
INSERT INTO "category" (cat_name, cat_desc) VALUES ('Category Six', 'Category six description');

INSERT INTO "item" (grp_id, itm_uuid, itm_name, itm_desc, itm_correlation_id, itm_status, itm_created_at)
VALUES( (SELECT grp_id FROM "group" WHERE grp_name = 'Group One'), 'afe9c3e1-415b-43e8-a27c-2b5249670bef', 'Item One', 'Item one description', 'e4b4a967-3758-4479-9a26-7ed5608f978a', 1, now() );

INSERT INTO "item" (grp_id, itm_uuid, itm_name, itm_desc, itm_correlation_id, itm_status, itm_created_at)
VALUES( (SELECT grp_id FROM "group" WHERE grp_name = 'Group One'), 'a577be2d-85e5-4891-9ed0-f27d2c3b6b1d', 'Item Two', 'Item two description', '128a7512-0b92-4f49-8f61-15dabbd757b8', 2, now() );

INSERT INTO "item" (grp_id, itm_uuid, itm_name, itm_desc, itm_correlation_id, itm_status, itm_created_at)
VALUES( (SELECT grp_id FROM "group" WHERE grp_name = 'Group Two'), '85bf245e-a941-4a18-8d4b-15c3d0aebd23', 'Item Three', 'Item three description', 'e4b4a967-3758-4479-9a26-7ed5608f978a', 1, now() );

INSERT INTO "item" (grp_id, itm_uuid, itm_name, itm_desc, itm_correlation_id, itm_status, itm_created_at)
VALUES( (SELECT grp_id FROM "group" WHERE grp_name = 'Group Two'), '4b30d7c8-2f17-49da-bff9-3a04364c5a08', 'Item Four', 'Item four description', '128a7512-0b92-4f49-8f61-15dabbd757b8', 3, now() );

INSERT INTO "item" (grp_id, itm_uuid, itm_name, itm_desc, itm_correlation_id, itm_status, itm_created_at)
VALUES( (SELECT grp_id FROM "group" WHERE grp_name = 'Group Two'), 'e9104002-6b3e-4123-8e24-6392d40a87b5', 'Item Five', 'Item five description', 'e4b4a967-3758-4479-9a26-7ed5608f978a', 4, now() );

INSERT INTO "item" (grp_id, itm_uuid, itm_name, itm_desc, itm_correlation_id, itm_status, itm_created_at)
VALUES( (SELECT grp_id FROM "group" WHERE grp_name = 'Group Two'), 'c97a9ab9-a588-4fc6-972e-5f29d720b09f', 'Item Six', 'Item six description', '402387ed-3843-46ce-b291-e6153595b45d', 4, now() );

INSERT INTO "item" (grp_id, itm_uuid, itm_name, itm_desc, itm_correlation_id, itm_status, itm_created_at)
VALUES( (SELECT grp_id FROM "group" WHERE grp_name = 'Group Three'), 'fb8d5122-dfcd-4509-a99e-222e862a1658', 'Item Seven', 'Item seven description', 'e4b4a967-3758-4479-9a26-7ed5608f978a', 3, now() );

INSERT INTO "item" (grp_id, itm_uuid, itm_name, itm_desc, itm_correlation_id, itm_status, itm_created_at)
VALUES( (SELECT grp_id FROM "group" WHERE grp_name = 'Group Four'), '5f0e1caf-0554-412e-8d49-b7a42f11219f', 'Item Eight', 'Item eight description', '8b72494d-c1c7-490d-b4e2-521e6056333b', 1, now() );


INSERT INTO "data" (itm_id, cat_id, dat_value, dat_created_at)
VALUES( (SELECT itm_id FROM "item" WHERE itm_name = 'Item One'), (SELECT cat_id FROM "category" WHERE cat_name = 'Category One'), 67.43, now());

INSERT INTO "data" (itm_id, cat_id, dat_value, dat_created_at)
VALUES( (SELECT itm_id FROM "item" WHERE itm_name = 'Item One'), (SELECT cat_id FROM "category" WHERE cat_name = 'Category Two'), 12.05, now());

INSERT INTO "data" (itm_id, cat_id, dat_value, dat_created_at)
VALUES( (SELECT itm_id FROM "item" WHERE itm_name = 'Item One'), (SELECT cat_id FROM "category" WHERE cat_name = 'Category Three'), 99.54, now());

INSERT INTO "data" (itm_id, cat_id, dat_value, dat_created_at)
VALUES( (SELECT itm_id FROM "item" WHERE itm_name = 'Item One'), (SELECT cat_id FROM "category" WHERE cat_name = 'Category Four'), 128.43, now());

INSERT INTO "data" (itm_id, cat_id, dat_value, dat_created_at)
VALUES( (SELECT itm_id FROM "item" WHERE itm_name = 'Item One'), (SELECT cat_id FROM "category" WHERE cat_name = 'Category Five'), 12.54, now());

INSERT INTO "data" (itm_id, cat_id, dat_value, dat_created_at)
VALUES( (SELECT itm_id FROM "item" WHERE itm_name = 'Item Two'), (SELECT cat_id FROM "category" WHERE cat_name = 'Category Six'), 44.76, now());

INSERT INTO "data" (itm_id, cat_id, dat_value, dat_created_at)
VALUES( (SELECT itm_id FROM "item" WHERE itm_name = 'Item Two'), null, 83.65, now());

INSERT INTO "data" (itm_id, cat_id, dat_value, dat_created_at)
VALUES( (SELECT itm_id FROM "item" WHERE itm_name = 'Item Two'), (SELECT cat_id FROM "category" WHERE cat_name = 'Category Two'), 62.78, now());

INSERT INTO "data" (itm_id, cat_id, dat_value, dat_created_at)
VALUES( (SELECT itm_id FROM "item" WHERE itm_name = 'Item Two'), (SELECT cat_id FROM "category" WHERE cat_name = 'Category Three'), 1.45, now());

INSERT INTO "data" (itm_id, cat_id, dat_value, dat_created_at)
VALUES( (SELECT itm_id FROM "item" WHERE itm_name = 'Item Two'), (SELECT cat_id FROM "category" WHERE cat_name = 'Category Four'), 76/04, now());

INSERT INTO "data" (itm_id, cat_id, dat_value, dat_created_at)
VALUES( (SELECT itm_id FROM "item" WHERE itm_name = 'Item Two'), null, 27.65, now());

INSERT INTO "data" (itm_id, cat_id, dat_value, dat_created_at)
VALUES( (SELECT itm_id FROM "item" WHERE itm_name = 'Item Three'), (SELECT cat_id FROM "category" WHERE cat_name = 'Category Six'), 19.98, now());

INSERT INTO "data" (itm_id, cat_id, dat_value, dat_created_at)
VALUES( (SELECT itm_id FROM "item" WHERE itm_name = 'Item Three'), (SELECT cat_id FROM "category" WHERE cat_name = 'Category One'), 9.86, now());

INSERT INTO "data" (itm_id, cat_id, dat_value, dat_created_at)
VALUES( (SELECT itm_id FROM "item" WHERE itm_name = 'Item Three'), (SELECT cat_id FROM "category" WHERE cat_name = 'Category Two'), 32.54, now());

INSERT INTO "data" (itm_id, cat_id, dat_value, dat_created_at)
VALUES( (SELECT itm_id FROM "item" WHERE itm_name = 'Item Four'), (SELECT cat_id FROM "category" WHERE cat_name = 'Category Three'), 111.43, now());

INSERT INTO "data" (itm_id, cat_id, dat_value, dat_created_at)
VALUES( (SELECT itm_id FROM "item" WHERE itm_name = 'Item Four'), null, 7.43, now());

INSERT INTO "data" (itm_id, cat_id, dat_value, dat_created_at)
VALUES( (SELECT itm_id FROM "item" WHERE itm_name = 'Item Five'), (SELECT cat_id FROM "category" WHERE cat_name = 'Category Five'), 77.03, now());

INSERT INTO "data" (itm_id, cat_id, dat_value, dat_created_at)
VALUES( (SELECT itm_id FROM "item" WHERE itm_name = 'Item Six'), (SELECT cat_id FROM "category" WHERE cat_name = 'Category Six'), 4.65, now());

INSERT INTO "data" (itm_id, cat_id, dat_value, dat_created_at)
VALUES( (SELECT itm_id FROM "item" WHERE itm_name = 'Item Six'), (SELECT cat_id FROM "category" WHERE cat_name = 'Category One'), 45.32, now());

INSERT INTO "data" (itm_id, cat_id, dat_value, dat_created_at)
VALUES( (SELECT itm_id FROM "item" WHERE itm_name = 'Item Six'), null, 78.98, now());

INSERT INTO "data" (itm_id, cat_id, dat_value, dat_created_at)
VALUES( (SELECT itm_id FROM "item" WHERE itm_name = 'Item Seven'), (SELECT cat_id FROM "category" WHERE cat_name = 'Category Three'), 32.45, now());

INSERT INTO "data" (itm_id, cat_id, dat_value, dat_created_at)
VALUES( (SELECT itm_id FROM "item" WHERE itm_name = 'Item Eight'), (SELECT cat_id FROM "category" WHERE cat_name = 'Category Four'), 17.94, now());

INSERT INTO "data" (itm_id, cat_id, dat_value, dat_created_at)
VALUES( (SELECT itm_id FROM "item" WHERE itm_name = 'Item Eight'), (SELECT cat_id FROM "category" WHERE cat_name = 'Category Five'), 65.34, now());

INSERT INTO "data" (itm_id, cat_id, dat_value, dat_created_at)
VALUES( (SELECT itm_id FROM "item" WHERE itm_name = 'Item Eight'), (SELECT cat_id FROM "category" WHERE cat_name = 'Category Six'), 89.43, now());
