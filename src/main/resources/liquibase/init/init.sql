CREATE TABLE userAuth (
user_id SERIAL PRIMARY KEY,
email VARCHAR(32),
firstName VARCHAR(16),
lastName VARCHAR(16),
phone VARCHAR(16),
pass VARCHAR(64),
username VARCHAR(32),
image_id INTEGER,
user_role VARCHAR(6)
);
CREATE TABLE Ad (
ad_id SERIAL PRIMARY KEY,
author_id  bigint NOT NUll,
image_id INTEGER,
title VARCHAR(32),
description VARCHAR(1000),
price INTEGER
);
CREATE TABLE comment(
comment_id SERIAL PRIMARY KEY,
author_id INTEGER,
ad_id INTEGER,
comment_date timestamp,
comment_text VARCHAR(1000)
);
CREATE TABLE image(
image_id SERIAL PRIMARY KEY,
image oid
);
