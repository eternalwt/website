drop database if exists website;
create database website default character set utf8mb4 collate utf8mb4_unicode_ci;
set global time_zone='+8:00';
use website;

drop table if exists auth_user;
create table auth_user(
  id bigint auto_increment primary key,
  user_name varchar(64),
  password varchar(128),
  password_salt varchar(128),
  create_time timestamp DEFAULT CURRENT_TIMESTAMP,
  update_time timestamp DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COMMENT='用户（账号）表';

drop table if exists auth_role;
create table auth_role(
    id bigint auto_increment primary key,
    role_name varchar(64),
    description varchar(256),
    create_time timestamp DEFAULT CURRENT_TIMESTAMP,
    update_time timestamp DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COMMENT='角色表';

drop table if exists auth_permission;
create table auth_permission(
    id bigint auto_increment primary key,
    permission_name varchar(64),
    description varchar(256),
    create_time timestamp DEFAULT CURRENT_TIMESTAMP,
    update_time timestamp DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COMMENT='权限表';

drop table if exists auth_user_role;
create table auth_user_role(
  id bigint auto_increment primary key,
  user_id bigint not null,
  role_id bigint not null,
  create_time timestamp DEFAULT CURRENT_TIMESTAMP,
  update_time timestamp DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COMMENT='用户角色关联表';

drop table if exists auth_role_permission;
create table auth_role_permission(
  id bigint auto_increment primary key,
  role_id bigint not null,
  permission_id bigint not null,
  create_time timestamp DEFAULT CURRENT_TIMESTAMP,
  update_time timestamp DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COMMENT='角色权限关联表';

insert into auth_user(user_name, password)values('zhang','123');
-- insert into shiro_user_role(username, role_name) values('zhang', 'admin');

drop table if exists menu;
create table menu(
     id bigint auto_increment primary key,
     menu_name varchar(64),
     number varchar(64), -- 编号
     url varchar(128),
     icon varchar(256),
     parent_id bigint,
     sort int,
     in_use tinyint,
     open_way tinyint,
     role varchar(256),
     permission varchar(512),
     permission_id bigint not null,
     create_time timestamp DEFAULT CURRENT_TIMESTAMP,
     update_time timestamp DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COMMENT='菜单表';