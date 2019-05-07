drop database if exists shiro;
create database shiro default character set utf8mb4 collate utf8mb4_unicode_ci;
use shiro;

drop table if exists shiro_users;
create table shiro_users(
  id bigint auto_increment,
  username varchar(100),
  password varchar(100),
  password_salt varchar(100),
  primary key(id)
) ENGINE=InnoDB;
create unique index idx_users_username on shiro_users(username);

drop table if exists shiro_user_roles;
create table shiro_user_roles(
  id bigint auto_increment,
  username varchar(100),
  role_name varchar(100),
  primary key(id)
) ENGINE=InnoDB;
create unique index idx_user_roles on shiro_user_roles(username, role_name);

drop table if exists shiro_roles_permissions;
create table shiro_roles_permissions(
  id bigint auto_increment,
  role_name varchar(100),
  permission varchar(100),
  primary key(id)
) ENGINE=InnoDB;
create unique index idx_roles_permissions on shiro_roles_permissions(role_name, permission);

insert into shiro_users(username,password)values('zhang','123');

-- 一个测试用的表，用完后删掉
drop table if exists Student;
CREATE TABLE Student(
   ID   INT NOT NULL AUTO_INCREMENT,
   NAME VARCHAR(20) NOT NULL,
   AGE  INT NOT NULL,
   PRIMARY KEY (ID)
);