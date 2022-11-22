DROP TABLE IF EXISTS grp_list CASCADE;
DROP TABLE IF EXISTS task CASCADE;
DROP TABLE IF EXISTS authorities CASCADE;
DROP TABLE IF EXISTS users CASCADE;
DROP TABLE IF EXISTS users;
DROP TABLE IF EXISTS grp CASCADE;
DROP TABLE IF EXISTS user_role;

CREATE TABLE IF NOT EXISTS grp
(
    group_id bigserial PRIMARY KEY,
    group_num varchar(30) NOT NULL,
    group_description varchar(128)
);

CREATE TABLE IF NOT EXISTS users
(
    id bigserial PRIMARY KEY,
    username varchar(128) NOT NULL unique,
    password varchar(512) NOT NULL,
    first_name varchar(128),
    last_name varchar(128),
    middle_name varchar(128),
    group_id bigint,

    enabled boolean not null,

    CONSTRAINT fk_group
        FOREIGN KEY(group_id)
            REFERENCES grp(group_id)
            ON DELETE SET NULL
);

CREATE TABLE IF NOT EXISTS task
(
    task_id bigserial PRIMARY KEY,
    task_answer varchar(256),
    task_description varchar(512)
);