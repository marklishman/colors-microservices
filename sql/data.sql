
DELETE FROM "data";
DELETE FROM "item";
DELETE FROM "group";
DELETE FROM "category";
DELETE FROM "visitor";
DELETE FROM "country";
DELETE FROM "person";

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

INSERT INTO person (psn_first_name, psn_last_name, psn_age) VALUES ('Bob', 'Jones', 31);
INSERT INTO person (psn_first_name, psn_last_name, psn_age) VALUES ('Sarah', 'Jones', 27);
INSERT INTO person (psn_first_name, psn_last_name, psn_age) VALUES ('Roger', 'Smith', 24);
INSERT INTO person (psn_first_name, psn_last_name, psn_age) VALUES ('Sally', 'Smith', 32);

INSERT INTO country (cty_name, cty_code) VALUES ('Afghanistan', 'AF');
INSERT INTO country (cty_name, cty_code) VALUES ('Ã…land Islands', 'AX');
INSERT INTO country (cty_name, cty_code) VALUES ('Albania', 'AL');
INSERT INTO country (cty_name, cty_code) VALUES ('Algeria', 'DZ');
INSERT INTO country (cty_name, cty_code) VALUES ('American Samoa', 'AS');
INSERT INTO country (cty_name, cty_code) VALUES ('Andorra', 'AD');
INSERT INTO country (cty_name, cty_code) VALUES ('Angola', 'AO');
INSERT INTO country (cty_name, cty_code) VALUES ('Anguilla', 'AI');
INSERT INTO country (cty_name, cty_code) VALUES ('Antarctica', 'AQ');
INSERT INTO country (cty_name, cty_code) VALUES ('Antigua and Barbuda', 'AG');
INSERT INTO country (cty_name, cty_code) VALUES ('Argentina', 'AR');
INSERT INTO country (cty_name, cty_code) VALUES ('Armenia', 'AM');
INSERT INTO country (cty_name, cty_code) VALUES ('Aruba', 'AW');
INSERT INTO country (cty_name, cty_code) VALUES ('Australia', 'AU');
INSERT INTO country (cty_name, cty_code) VALUES ('Austria', 'AT');
INSERT INTO country (cty_name, cty_code) VALUES ('Azerbaijan', 'AZ');
INSERT INTO country (cty_name, cty_code) VALUES ('Bahamas', 'BS');
INSERT INTO country (cty_name, cty_code) VALUES ('Bahrain', 'BH');
INSERT INTO country (cty_name, cty_code) VALUES ('Bangladesh', 'BD');
INSERT INTO country (cty_name, cty_code) VALUES ('Barbados', 'BB');
INSERT INTO country (cty_name, cty_code) VALUES ('Belarus', 'BY');
INSERT INTO country (cty_name, cty_code) VALUES ('Belgium', 'BE');
INSERT INTO country (cty_name, cty_code) VALUES ('Belize', 'BZ');
INSERT INTO country (cty_name, cty_code) VALUES ('Benin', 'BJ');
INSERT INTO country (cty_name, cty_code) VALUES ('Bermuda', 'BM');
INSERT INTO country (cty_name, cty_code) VALUES ('Bhutan', 'BT');
INSERT INTO country (cty_name, cty_code) VALUES ('Bosnia and Herzegovina', 'BA');
INSERT INTO country (cty_name, cty_code) VALUES ('Botswana', 'BW');
INSERT INTO country (cty_name, cty_code) VALUES ('Bouvet Island', 'BV');
INSERT INTO country (cty_name, cty_code) VALUES ('Brazil', 'BR');
INSERT INTO country (cty_name, cty_code) VALUES ('British Indian Ocean Territory', 'IO');
INSERT INTO country (cty_name, cty_code) VALUES ('Brunei Darussalam', 'BN');
INSERT INTO country (cty_name, cty_code) VALUES ('Bulgaria', 'BG');
INSERT INTO country (cty_name, cty_code) VALUES ('Burkina Faso', 'BF');
INSERT INTO country (cty_name, cty_code) VALUES ('Burundi', 'BI');
INSERT INTO country (cty_name, cty_code) VALUES ('Cambodia', 'KH');
INSERT INTO country (cty_name, cty_code) VALUES ('Cameroon', 'CM');
INSERT INTO country (cty_name, cty_code) VALUES ('Canada', 'CA');
INSERT INTO country (cty_name, cty_code) VALUES ('Cape Verde', 'CV');
INSERT INTO country (cty_name, cty_code) VALUES ('Cayman Islands', 'KY');
INSERT INTO country (cty_name, cty_code) VALUES ('Central African Republic', 'CF');
INSERT INTO country (cty_name, cty_code) VALUES ('Chad', 'TD');
INSERT INTO country (cty_name, cty_code) VALUES ('Chile', 'CL');
INSERT INTO country (cty_name, cty_code) VALUES ('China', 'CN');
INSERT INTO country (cty_name, cty_code) VALUES ('Christmas Island', 'CX');
INSERT INTO country (cty_name, cty_code) VALUES ('Cocos (Keeling) Islands', 'CC');
INSERT INTO country (cty_name, cty_code) VALUES ('Colombia', 'CO');
INSERT INTO country (cty_name, cty_code) VALUES ('Comoros', 'KM');
INSERT INTO country (cty_name, cty_code) VALUES ('Congo', 'CG');
INSERT INTO country (cty_name, cty_code) VALUES ('The Democratic Republic of the Congo', 'CD');
INSERT INTO country (cty_name, cty_code) VALUES ('Cook Islands', 'CK');
INSERT INTO country (cty_name, cty_code) VALUES ('Costa Rica', 'CR');
INSERT INTO country (cty_name, cty_code) VALUES ('Croatia', 'HR');
INSERT INTO country (cty_name, cty_code) VALUES ('Cuba', 'CU');
INSERT INTO country (cty_name, cty_code) VALUES ('CuraÃ§ao', 'CW');
INSERT INTO country (cty_name, cty_code) VALUES ('Cyprus', 'CY');
INSERT INTO country (cty_name, cty_code) VALUES ('Czech Republic', 'CZ');
INSERT INTO country (cty_name, cty_code) VALUES ('Denmark', 'DK');
INSERT INTO country (cty_name, cty_code) VALUES ('Djibouti', 'DJ');
INSERT INTO country (cty_name, cty_code) VALUES ('Dominica', 'DM');
INSERT INTO country (cty_name, cty_code) VALUES ('Dominican Republic', 'DO');
INSERT INTO country (cty_name, cty_code) VALUES ('Ecuador', 'EC');
INSERT INTO country (cty_name, cty_code) VALUES ('Egypt', 'EG');
INSERT INTO country (cty_name, cty_code) VALUES ('El Salvador', 'SV');
INSERT INTO country (cty_name, cty_code) VALUES ('Equatorial Guinea', 'GQ');
INSERT INTO country (cty_name, cty_code) VALUES ('Eritrea', 'ER');
INSERT INTO country (cty_name, cty_code) VALUES ('Estonia', 'EE');
INSERT INTO country (cty_name, cty_code) VALUES ('Ethiopia', 'ET');
INSERT INTO country (cty_name, cty_code) VALUES ('Falkland Islands (Malvinas)', 'FK');
INSERT INTO country (cty_name, cty_code) VALUES ('Faroe Islands', 'FO');
INSERT INTO country (cty_name, cty_code) VALUES ('Fiji', 'FJ');
INSERT INTO country (cty_name, cty_code) VALUES ('Finland', 'FI');
INSERT INTO country (cty_name, cty_code) VALUES ('France', 'FR');
INSERT INTO country (cty_name, cty_code) VALUES ('French Guiana', 'GF');
INSERT INTO country (cty_name, cty_code) VALUES ('French Polynesia', 'PF');
INSERT INTO country (cty_name, cty_code) VALUES ('French Southern Territories', 'TF');
INSERT INTO country (cty_name, cty_code) VALUES ('Gabon', 'GA');
INSERT INTO country (cty_name, cty_code) VALUES ('Gambia', 'GM');
INSERT INTO country (cty_name, cty_code) VALUES ('Georgia', 'GE');
INSERT INTO country (cty_name, cty_code) VALUES ('Germany', 'DE');
INSERT INTO country (cty_name, cty_code) VALUES ('Ghana', 'GH');
INSERT INTO country (cty_name, cty_code) VALUES ('Gibraltar', 'GI');
INSERT INTO country (cty_name, cty_code) VALUES ('Greece', 'GR');
INSERT INTO country (cty_name, cty_code) VALUES ('Greenland', 'GL');
INSERT INTO country (cty_name, cty_code) VALUES ('Grenada', 'GD');
INSERT INTO country (cty_name, cty_code) VALUES ('Guadeloupe', 'GP');
INSERT INTO country (cty_name, cty_code) VALUES ('Guam', 'GU');
INSERT INTO country (cty_name, cty_code) VALUES ('Guatemala', 'GT');
INSERT INTO country (cty_name, cty_code) VALUES ('Guernsey', 'GG');
INSERT INTO country (cty_name, cty_code) VALUES ('Guinea', 'GN');
INSERT INTO country (cty_name, cty_code) VALUES ('Guinea-Bissau', 'GW');
INSERT INTO country (cty_name, cty_code) VALUES ('Guyana', 'GY');
INSERT INTO country (cty_name, cty_code) VALUES ('Haiti', 'HT');
INSERT INTO country (cty_name, cty_code) VALUES ('Heard Island and McDonald Islands', 'HM');
INSERT INTO country (cty_name, cty_code) VALUES ('Holy See (Vatican City State)', 'VA');
INSERT INTO country (cty_name, cty_code) VALUES ('Honduras', 'HN');
INSERT INTO country (cty_name, cty_code) VALUES ('Hong Kong', 'HK');
INSERT INTO country (cty_name, cty_code) VALUES ('Hungary', 'HU');
INSERT INTO country (cty_name, cty_code) VALUES ('Iceland', 'IS');
INSERT INTO country (cty_name, cty_code) VALUES ('India', 'IN');
INSERT INTO country (cty_name, cty_code) VALUES ('Indonesia', 'ID');
INSERT INTO country (cty_name, cty_code) VALUES ('Islamic Republic of Iran', 'IR');
INSERT INTO country (cty_name, cty_code) VALUES ('Iraq', 'IQ');
INSERT INTO country (cty_name, cty_code) VALUES ('Ireland', 'IE');
INSERT INTO country (cty_name, cty_code) VALUES ('Isle of Man', 'IM');
INSERT INTO country (cty_name, cty_code) VALUES ('Israel', 'IL');
INSERT INTO country (cty_name, cty_code) VALUES ('Italy', 'IT');
INSERT INTO country (cty_name, cty_code) VALUES ('Jamaica', 'JM');
INSERT INTO country (cty_name, cty_code) VALUES ('Japan', 'JP');
INSERT INTO country (cty_name, cty_code) VALUES ('Jersey', 'JE');
INSERT INTO country (cty_name, cty_code) VALUES ('Jordan', 'JO');
INSERT INTO country (cty_name, cty_code) VALUES ('Kazakhstan', 'KZ');
INSERT INTO country (cty_name, cty_code) VALUES ('Kenya', 'KE');
INSERT INTO country (cty_name, cty_code) VALUES ('Kiribati', 'KI');
INSERT INTO country (cty_name, cty_code) VALUES ('Democratic Peoples Republic of Korea', 'KP');
INSERT INTO country (cty_name, cty_code) VALUES ('Republic of Korea', 'KR');
INSERT INTO country (cty_name, cty_code) VALUES ('Kuwait', 'KW');
INSERT INTO country (cty_name, cty_code) VALUES ('Kyrgyzstan', 'KG');
INSERT INTO country (cty_name, cty_code) VALUES ('Lao Peoples Democratic Republic', 'LA');
INSERT INTO country (cty_name, cty_code) VALUES ('Latvia', 'LV');
INSERT INTO country (cty_name, cty_code) VALUES ('Lebanon', 'LB');
INSERT INTO country (cty_name, cty_code) VALUES ('Lesotho', 'LS');
INSERT INTO country (cty_name, cty_code) VALUES ('Liberia', 'LR');
INSERT INTO country (cty_name, cty_code) VALUES ('Libya', 'LY');
INSERT INTO country (cty_name, cty_code) VALUES ('Liechtenstein', 'LI');
INSERT INTO country (cty_name, cty_code) VALUES ('Lithuania', 'LT');
INSERT INTO country (cty_name, cty_code) VALUES ('Luxembourg', 'LU');
INSERT INTO country (cty_name, cty_code) VALUES ('Macao', 'MO');
INSERT INTO country (cty_name, cty_code) VALUES ('Macedonia', 'MK');
INSERT INTO country (cty_name, cty_code) VALUES ('Madagascar', 'MG');
INSERT INTO country (cty_name, cty_code) VALUES ('Malawi', 'MW');
INSERT INTO country (cty_name, cty_code) VALUES ('Malaysia', 'MY');
INSERT INTO country (cty_name, cty_code) VALUES ('Maldives', 'MV');
INSERT INTO country (cty_name, cty_code) VALUES ('Mali', 'ML');
INSERT INTO country (cty_name, cty_code) VALUES ('Malta', 'MT');
INSERT INTO country (cty_name, cty_code) VALUES ('Marshall Islands', 'MH');
INSERT INTO country (cty_name, cty_code) VALUES ('Martinique', 'MQ');
INSERT INTO country (cty_name, cty_code) VALUES ('Mauritania', 'MR');
INSERT INTO country (cty_name, cty_code) VALUES ('Mauritius', 'MU');
INSERT INTO country (cty_name, cty_code) VALUES ('Mayotte', 'YT');
INSERT INTO country (cty_name, cty_code) VALUES ('Mexico', 'MX');
INSERT INTO country (cty_name, cty_code) VALUES ('Micronesia', 'FM');
INSERT INTO country (cty_name, cty_code) VALUES ('Moldova', 'MD');
INSERT INTO country (cty_name, cty_code) VALUES ('Monaco', 'MC');
INSERT INTO country (cty_name, cty_code) VALUES ('Mongolia', 'MN');
INSERT INTO country (cty_name, cty_code) VALUES ('Montenegro', 'ME');
INSERT INTO country (cty_name, cty_code) VALUES ('Montserrat', 'MS');
INSERT INTO country (cty_name, cty_code) VALUES ('Morocco', 'MA');
INSERT INTO country (cty_name, cty_code) VALUES ('Mozambique', 'MZ');
INSERT INTO country (cty_name, cty_code) VALUES ('Myanmar', 'MM');
INSERT INTO country (cty_name, cty_code) VALUES ('Namibia', 'NA');
INSERT INTO country (cty_name, cty_code) VALUES ('Nauru', 'NR');
INSERT INTO country (cty_name, cty_code) VALUES ('Nepal', 'NP');
INSERT INTO country (cty_name, cty_code) VALUES ('Netherlands', 'NL');
INSERT INTO country (cty_name, cty_code) VALUES ('New Caledonia', 'NC');
INSERT INTO country (cty_name, cty_code) VALUES ('New Zealand', 'NZ');
INSERT INTO country (cty_name, cty_code) VALUES ('Nicaragua', 'NI');
INSERT INTO country (cty_name, cty_code) VALUES ('Niger', 'NE');
INSERT INTO country (cty_name, cty_code) VALUES ('Nigeria', 'NG');
INSERT INTO country (cty_name, cty_code) VALUES ('Niue', 'NU');
INSERT INTO country (cty_name, cty_code) VALUES ('Norfolk Island', 'NF');
INSERT INTO country (cty_name, cty_code) VALUES ('Northern Mariana Islands', 'MP');
INSERT INTO country (cty_name, cty_code) VALUES ('Norway', 'NO');
INSERT INTO country (cty_name, cty_code) VALUES ('Oman', 'OM');
INSERT INTO country (cty_name, cty_code) VALUES ('Pakistan', 'PK');
INSERT INTO country (cty_name, cty_code) VALUES ('Palau', 'PW');
INSERT INTO country (cty_name, cty_code) VALUES ('Palestine', 'PS');
INSERT INTO country (cty_name, cty_code) VALUES ('Panama', 'PA');
INSERT INTO country (cty_name, cty_code) VALUES ('Papua New Guinea', 'PG');
INSERT INTO country (cty_name, cty_code) VALUES ('Paraguay', 'PY');
INSERT INTO country (cty_name, cty_code) VALUES ('Peru', 'PE');
INSERT INTO country (cty_name, cty_code) VALUES ('Philippines', 'PH');
INSERT INTO country (cty_name, cty_code) VALUES ('Pitcairn', 'PN');
INSERT INTO country (cty_name, cty_code) VALUES ('Poland', 'PL');
INSERT INTO country (cty_name, cty_code) VALUES ('Portugal', 'PT');
INSERT INTO country (cty_name, cty_code) VALUES ('Puerto Rico', 'PR');
INSERT INTO country (cty_name, cty_code) VALUES ('Qatar', 'QA');
INSERT INTO country (cty_name, cty_code) VALUES ('RÃ©union', 'RE');
INSERT INTO country (cty_name, cty_code) VALUES ('Romania', 'RO');
INSERT INTO country (cty_name, cty_code) VALUES ('Russian Federation', 'RU');
INSERT INTO country (cty_name, cty_code) VALUES ('Rwanda', 'RW');
INSERT INTO country (cty_name, cty_code) VALUES ('Saint BarthÃ©lemy', 'BL');
INSERT INTO country (cty_name, cty_code) VALUES ('Saint Helena', 'SH');
INSERT INTO country (cty_name, cty_code) VALUES ('Saint Kitts and Nevis', 'KN');
INSERT INTO country (cty_name, cty_code) VALUES ('Saint Lucia', 'LC');
INSERT INTO country (cty_name, cty_code) VALUES ('Saint Martin (French part)', 'MF');
INSERT INTO country (cty_name, cty_code) VALUES ('Saint Pierre and Miquelon', 'PM');
INSERT INTO country (cty_name, cty_code) VALUES ('Saint Vincent and the Grenadines', 'VC');
INSERT INTO country (cty_name, cty_code) VALUES ('Samoa', 'WS');
INSERT INTO country (cty_name, cty_code) VALUES ('San Marino', 'SM');
INSERT INTO country (cty_name, cty_code) VALUES ('Sao Tome and Principe', 'ST');
INSERT INTO country (cty_name, cty_code) VALUES ('Saudi Arabia', 'SA');
INSERT INTO country (cty_name, cty_code) VALUES ('Senegal', 'SN');
INSERT INTO country (cty_name, cty_code) VALUES ('Serbia', 'RS');
INSERT INTO country (cty_name, cty_code) VALUES ('Seychelles', 'SC');
INSERT INTO country (cty_name, cty_code) VALUES ('Sierra Leone', 'SL');
INSERT INTO country (cty_name, cty_code) VALUES ('Singapore', 'SG');
INSERT INTO country (cty_name, cty_code) VALUES ('Sint Maarten (Dutch part)', 'SX');
INSERT INTO country (cty_name, cty_code) VALUES ('Slovakia', 'SK');
INSERT INTO country (cty_name, cty_code) VALUES ('Slovenia', 'SI');
INSERT INTO country (cty_name, cty_code) VALUES ('Solomon Islands', 'SB');
INSERT INTO country (cty_name, cty_code) VALUES ('Somalia', 'SO');
INSERT INTO country (cty_name, cty_code) VALUES ('South Africa', 'ZA');
INSERT INTO country (cty_name, cty_code) VALUES ('South Georgia and the South Sandwich Islands', 'GS');
INSERT INTO country (cty_name, cty_code) VALUES ('South Sudan', 'SS');
INSERT INTO country (cty_name, cty_code) VALUES ('Spain', 'ES');
INSERT INTO country (cty_name, cty_code) VALUES ('Sri Lanka', 'LK');
INSERT INTO country (cty_name, cty_code) VALUES ('Sudan', 'SD');
INSERT INTO country (cty_name, cty_code) VALUES ('Suriname', 'SR');
INSERT INTO country (cty_name, cty_code) VALUES ('Svalbard and Jan Mayen', 'SJ');
INSERT INTO country (cty_name, cty_code) VALUES ('Swaziland', 'SZ');
INSERT INTO country (cty_name, cty_code) VALUES ('Sweden', 'SE');
INSERT INTO country (cty_name, cty_code) VALUES ('Switzerland', 'CH');
INSERT INTO country (cty_name, cty_code) VALUES ('Syrian Arab Republic', 'SY');
INSERT INTO country (cty_name, cty_code) VALUES ('Taiwan', 'TW');
INSERT INTO country (cty_name, cty_code) VALUES ('Tajikistan', 'TJ');
INSERT INTO country (cty_name, cty_code) VALUES ('Tanzania', 'TZ');
INSERT INTO country (cty_name, cty_code) VALUES ('Thailand', 'TH');
INSERT INTO country (cty_name, cty_code) VALUES ('Timor-Leste', 'TL');
INSERT INTO country (cty_name, cty_code) VALUES ('Togo', 'TG');
INSERT INTO country (cty_name, cty_code) VALUES ('Tokelau', 'TK');
INSERT INTO country (cty_name, cty_code) VALUES ('Tonga', 'TO');
INSERT INTO country (cty_name, cty_code) VALUES ('Trinidad and Tobago', 'TT');
INSERT INTO country (cty_name, cty_code) VALUES ('Tunisia', 'TN');
INSERT INTO country (cty_name, cty_code) VALUES ('Turkey', 'TR');
INSERT INTO country (cty_name, cty_code) VALUES ('Turkmenistan', 'TM');
INSERT INTO country (cty_name, cty_code) VALUES ('Turks and Caicos Islands', 'TC');
INSERT INTO country (cty_name, cty_code) VALUES ('Tuvalu', 'TV');
INSERT INTO country (cty_name, cty_code) VALUES ('Uganda', 'UG');
INSERT INTO country (cty_name, cty_code) VALUES ('Ukraine', 'UA');
INSERT INTO country (cty_name, cty_code) VALUES ('United Arab Emirates', 'AE');
INSERT INTO country (cty_name, cty_code) VALUES ('United Kingdom', 'GB');
INSERT INTO country (cty_name, cty_code) VALUES ('United States', 'US');
INSERT INTO country (cty_name, cty_code) VALUES ('United States Minor Outlying Islands', 'UM');
INSERT INTO country (cty_name, cty_code) VALUES ('Uruguay', 'UY');
INSERT INTO country (cty_name, cty_code) VALUES ('Uzbekistan', 'UZ');
INSERT INTO country (cty_name, cty_code) VALUES ('Vanuatu', 'VU');
INSERT INTO country (cty_name, cty_code) VALUES ('Venezuela', 'VE');
INSERT INTO country (cty_name, cty_code) VALUES ('Viet Nam', 'VN');
INSERT INTO country (cty_name, cty_code) VALUES ('Virgin Islands', 'VG');
INSERT INTO country (cty_name, cty_code) VALUES ('Wallis and Futuna', 'WF');
INSERT INTO country (cty_name, cty_code) VALUES ('Western Sahara', 'EH');
INSERT INTO country (cty_name, cty_code) VALUES ('Yemen', 'YE');
INSERT INTO country (cty_name, cty_code) VALUES ('Zambia', 'ZM');
INSERT INTO country (cty_name, cty_code) VALUES ('Zimbabwe', 'ZW');

INSERT INTO visitor (psn_id, cty_id) VALUES (1, 20)
