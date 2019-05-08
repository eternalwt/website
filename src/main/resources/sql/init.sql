drop database if exists shiro;
create database shiro default character set utf8mb4 collate utf8mb4_unicode_ci;
use shiro;

drop table if exists shiro_user;
create table shiro_user(
  id bigint auto_increment,
  username varchar(100),
  password varchar(100),
  password_salt varchar(100),
  primary key(id)
) ENGINE=InnoDB;
create unique index idx_user_username on shiro_user(username); -- todo 不要这样写

drop table if exists shiro_user_role;
create table shiro_user_role(
  id bigint auto_increment,
  username varchar(100),
  role_name varchar(100),
  primary key(id)
) ENGINE=InnoDB;
create unique index idx_user_role on shiro_user_role(username, role_name);

drop table if exists shiro_role_permission;
create table shiro_role_permission(
  id bigint auto_increment,
  role_name varchar(100),
  permission varchar(100),
  primary key(id)
) ENGINE=InnoDB;
create unique index idx_role_permission on shiro_role_permission(role_name, permission);

insert into shiro_user(username,password)values('zhang','123');

-- 一个测试用的表，用完后删掉
drop table if exists student;
create table student(
   id int not null auto_increment,
   name varchar(20) not null,
   age int not null,
   primary key(id)
) ENGINE=InnoDB;