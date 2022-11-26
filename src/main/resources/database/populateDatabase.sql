insert into roles values (1, 'ROLE_USER');
insert into roles values (2, 'ROLE_ADMIN');

insert into users (enabled, password, username, is_password_changed) values (true, '$2a$12$MbTGKT/lBwqZUbGIoXJlWO6Pf0ffchcYYtO/aUviWuKxUz8ugnYUu', 'admin', false);

insert into user_roles values (1, 2);

/*
login : admin
password : 1424
*/

