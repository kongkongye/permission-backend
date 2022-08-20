create schema permission collate utf8mb4_unicode_ci;

create table if not exists biz
(
    id          bigint auto_increment
        primary key,
    create_user bigint       null,
    update_user bigint       null,
    create_time bigint       null,
    update_time bigint       null,
    version     bigint       not null,
    dir_code    varchar(64)  null,
    code        varchar(64)  not null,
    name        varchar(64)  not null,
    note        varchar(255) null,
    constraint code
        unique (code)
);

create table if not exists biz_dir
(
    id          bigint auto_increment
        primary key,
    create_user bigint       null,
    update_user bigint       null,
    create_time bigint       null,
    update_time bigint       null,
    version     bigint       not null,
    code        varchar(64)  not null,
    name        varchar(64)  not null,
    parent      varchar(64)  null,
    note        varchar(255) null,
    constraint code
        unique (code)
);

create table if not exists biz_per_type
(
    id            int auto_increment
        primary key,
    create_time   bigint           null,
    update_time   bigint           null,
    create_user   bigint           null,
    update_user   bigint           null,
    version       bigint default 0 not null,
    biz_code      varchar(255)     not null,
    per_type_code varchar(255)     not null
);

create table if not exists dept
(
    id          bigint auto_increment
        primary key,
    create_user bigint           null,
    update_user bigint           null,
    create_time bigint           null,
    update_time bigint           null,
    version     bigint default 0 not null,
    code        varchar(64)      not null,
    name        varchar(64)      not null,
    parent      varchar(64)      null,
    note        varchar(255)     null,
    constraint code
        unique (code)
);

create table if not exists per_bind
(
    id          bigint auto_increment
        primary key,
    create_user bigint      null,
    update_user bigint      null,
    create_time bigint      null,
    update_time bigint      null,
    version     bigint      not null,
    biz_code    varchar(64) null,
    bind_type   varchar(64) not null,
    bind_code   varchar(64) not null,
    type_code   varchar(64) null,
    per_code    varchar(64) not null,
    constraint code
        unique (biz_code, bind_type, bind_code, type_code, per_code)
);

create table if not exists per_type
(
    id          bigint auto_increment
        primary key,
    create_user bigint       null,
    update_user bigint       null,
    create_time bigint       null,
    update_time bigint       null,
    version     bigint       not null,
    code        varchar(64)  not null,
    name        varchar(64)  not null,
    note        varchar(255) null,
    filter      varchar(64)  null,
    constraint code
        unique (code)
);

create table if not exists per_value
(
    id          bigint auto_increment
        primary key,
    create_user bigint           null,
    update_user bigint           null,
    create_time bigint           null,
    update_time bigint           null,
    version     bigint default 0 not null,
    type_code   varchar(64)      not null,
    code        varchar(64)      not null,
    name        varchar(64)      not null,
    parent      varchar(64)      null,
    note        varchar(255)     null,
    filter_code varchar(64)      null,
    constraint code
        unique (code)
);

create index idx_type
    on per_value (type_code);

create table if not exists role
(
    id          bigint auto_increment
        primary key,
    create_user bigint           null,
    update_user bigint           null,
    create_time bigint           null,
    update_time bigint           null,
    version     bigint default 0 not null,
    dept_code   varchar(64)      null,
    code        varchar(64)      not null,
    name        varchar(64)      not null,
    note        varchar(255)     null,
    constraint code
        unique (code)
);

create table if not exists user
(
    id               bigint auto_increment
        primary key,
    create_user      bigint           null,
    update_user      bigint           null,
    create_time      bigint           null,
    update_time      bigint           null,
    version          bigint default 0 not null,
    code             varchar(64)      not null,
    nickname         varchar(64)      null,
    name             varchar(64)      null,
    password         varchar(255)     null,
    password_encoder varchar(64)      null,
    note             varchar(255)     null,
    disabled         bit              null,
    constraint code
        unique (code),
    constraint name
        unique (name)
);

create table if not exists user_dept
(
    id          bigint auto_increment
        primary key,
    create_user bigint      null,
    update_user bigint      null,
    create_time bigint      null,
    update_time bigint      null,
    version     bigint      not null,
    user_code   varchar(64) not null,
    dept_code   varchar(64) not null,
    constraint uk
        unique (user_code, dept_code)
);

create table if not exists user_role
(
    id          bigint auto_increment
        primary key,
    create_user bigint           null,
    update_user bigint           null,
    create_time bigint           null,
    update_time bigint           null,
    version     bigint default 0 not null,
    user_code   varchar(64)      not null,
    role_code   varchar(64)      not null,
    constraint uk
        unique (user_code, role_code)
);

INSERT INTO user (create_user, update_user, create_time, update_time, version, code, nickname, name, password,
                  password_encoder, note, disabled)
VALUES (null, null, null, 1657096602565, 2, 'admin', 'admin', 'admin', 'admin', 'plain', '管理员', null);
