CREATE DATABASE "usermanagement";
GRANT CONNECT ON DATABASE "usermanagement" TO "postgres";
GRANT USAGE ON SCHEMA public TO "postgres";
GRANT CREATE, CONNECT ON DATABASE "usermanagement" TO "postgres";
GRANT SELECT, INSERT, UPDATE, DELETE ON ALL TABLES IN SCHEMA public TO "postgres";

CREATE TABLE public."user"
(
    id         BIGINT GENERATED BY DEFAULT AS IDENTITY NOT NULL,
    username   VARCHAR(255)                            NOT NULL,
    password   VARCHAR(255)                            NOT NULL,
    first_name VARCHAR(255)                            NOT NULL,
    last_name  VARCHAR(255)                            NOT NULL,
    role       VARCHAR(255)                            NOT NULL,
    created_at TIMESTAMP WITHOUT TIME ZONE             NOT NULL,
    updated_at TIMESTAMP WITHOUT TIME ZONE             NOT NULL,
    deleted_at TIMESTAMP WITHOUT TIME ZONE,
    CONSTRAINT pk_user PRIMARY KEY (id)
);


insert into public.user (id, created_at, deleted_at, first_name, last_name, password, updated_at, username, role) values (25, '2023-06-12 23:19:40.984083', null, 'dee', 'ddd', 'dvd', '2023-06-12 23:19:40.984160', 'pwpw', 'USER');
insert into public.user (id, created_at, deleted_at, first_name, last_name, password, updated_at, username, role) values (27, '2023-06-13 15:15:37.126140', null, 'dee', 'ddd', 'dvd', '2023-06-13 15:15:37.126334', 'qqqq', 'USER');
insert into public.user (id, created_at, deleted_at, first_name, last_name, password, updated_at, username, role) values (28, '2023-06-28 15:36:04.914829', null, 'Key', 'User', 'Key123', '2023-06-28 15:36:04.915021', 'keycloakuser1', 'USER');
insert into public.user (id, created_at, deleted_at, first_name, last_name, password, updated_at, username, role) values (29, '2023-06-28 15:54:15.402147', null, 'Key2', 'User2', 'Key123', '2023-06-28 15:54:15.402221', 'keycloakuser2', 'USER');
insert into public.user (id, created_at, deleted_at, first_name, last_name, password, updated_at, username, role) values (30, '2023-06-29 13:35:08.231369', null, 'Key3', 'User3', 'Key123', '2023-06-29 13:35:08.231418', 'keycloakuser3', 'USER');
insert into public.user (id, created_at, deleted_at, first_name, last_name, password, updated_at, username, role) values (31, '2023-06-29 13:48:39.315947', null, 'Key4', 'User4', 'Key123', '2023-06-29 13:48:39.315968', 'keycloakuser4', 'USER');
insert into public.user (id, created_at, deleted_at, first_name, last_name, password, updated_at, username, role) values (32, '2023-06-29 13:56:05.279861', null, 'Key5', 'Usersssss', 'Key123', '2023-06-29 13:56:05.279882', 'User5', 'USER');
insert into public.user (id, created_at, deleted_at, first_name, last_name, password, updated_at, username, role) values (33, '2023-06-29 13:58:13.238440', null, '6thUSer', 'Usersssss', 'Key123', '2023-06-29 13:58:13.238470', 'Keee', 'USER');
insert into public.user (id, created_at, deleted_at, first_name, last_name, password, updated_at, username, role) values (34, '2023-06-29 15:24:38.821661', null, '7thUSer', 'Usersssss', 'Key123', '2023-06-29 15:24:38.821689', 'referuser', 'USER');
insert into public.user (id, created_at, deleted_at, first_name, last_name, password, updated_at, username, role) values (35, '2023-06-29 15:34:17.405874', null, 'zz', 'zz', 'Key123', '2023-06-29 15:34:17.405899', 'zzUser', 'USER');
insert into public.user (id, created_at, deleted_at, first_name, last_name, password, updated_at, username, role) values (36, '2023-06-29 15:46:06.395921', null, 'zz', 'zz', 'Key123', '2023-06-29 15:46:06.395942', 'lastUSer', 'USER');
insert into public.user (id, created_at, deleted_at, first_name, last_name, password, updated_at, username, role) values (37, '2023-06-29 16:36:49.862982', null, 'keycloak', 'keycloak', 'Key123', '2023-06-29 16:36:49.863022', 'UserKeycloak', 'USER');
insert into public.user (id, created_at, deleted_at, first_name, last_name, password, updated_at, username, role) values (38, '2023-07-05 10:53:37.154599', null, 'User5', 'keycloakuser5', 'Key123', '2023-07-05 10:53:37.154674', 'USer5', 'USER');
insert into public.user (id, created_at, deleted_at, first_name, last_name, password, updated_at, username, role) values (39, '2023-07-05 18:25:33.000717', null, 'User6', 'keycloakuser6', 'Key123', '2023-07-05 18:25:33.000738', 'NewUSer', 'USER');
insert into public.user (id, created_at, deleted_at, first_name, last_name, password, updated_at, username, role) values (40, '2023-07-11 16:19:15.242372', null, 'User7', 'keycloakuser6', 'Key123', '2023-07-11 16:19:15.242593', 'NewUSer1', 'USER');
insert into public.user (id, created_at, deleted_at, first_name, last_name, password, updated_at, username, role) values (41, '2023-07-26 09:52:43.449389', null, 'qwert', 'breezy', '1234', '2023-07-26 09:52:43.449623', 'qqwes', 'USER');
insert into public.user (id, created_at, deleted_at, first_name, last_name, password, updated_at, username, role) values (42, '2023-09-08 13:02:27.486393', null, 'qwert', 'breezy', '1234', '2023-09-08 13:02:27.486440', 'cdcd', 'USER');


