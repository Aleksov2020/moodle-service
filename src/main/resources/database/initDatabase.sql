DROP TABLE IF EXISTS grp_list;
DROP TABLE IF EXISTS usr;
DROP TABLE IF EXISTS grp;
DROP TABLE IF EXISTS task;

CREATE TABLE IF NOT EXISTS usr
(
    usr_id bigserial PRIMARY KEY,
    username varchar(128) NOT NULL,
    first_name varchar(128) NOT NULL,
    last_name varchar(128),
    middle_name varchar(128),
    group_id bigint
);

CREATE TABLE IF NOT EXISTS grp
(
    group_id bigserial PRIMARY KEY,
    group_num varchar(30) NOT NULL,
    group_description varchar(128)
);

CREATE TABLE IF NOT EXISTS grp_list
(
    group_list_id bigserial  PRIMARY KEY,
    group_id bigserial,
    usr_id bigserial,

    CONSTRAINT fk_group
        FOREIGN KEY(group_id)
            REFERENCES grp(group_id)
            ON DELETE SET NULL,

    CONSTRAINT fk_usr
        FOREIGN KEY(usr_id)
            REFERENCES usr(usr_id)
            ON DELETE SET NULL
);

CREATE TABLE IF NOT EXISTS task
(
    task_id bigserial PRIMARY KEY,
    task_answer varchar(256),
    task_description varchar(512)
);