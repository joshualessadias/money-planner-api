insert into app_user (created_at, last_updated_at, first_name, last_name, email, password)
values (getdate(), getdate(), N'Joshua', N'Dias', N'joshualessadias@gmail.com',
        N'$2a$10$KTbWooHOC8aBYyz5mcodLuf7wuWJPECPDLav49gBj5NKAtBhzWU6C');

insert into app_user_role (app_user_id, role_id)
values (1, 1),
       (1, 2);