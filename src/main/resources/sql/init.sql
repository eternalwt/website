drop database if exists website;
create database website default character set utf8mb4 collate utf8mb4_unicode_ci;
set global time_zone='+8:00';
use website;

drop table if exists auth_user;
create table auth_user(
  id bigint(64) auto_increment primary key,
  user_name varchar(64),
  password varchar(128),
  password_salt varchar(128),
  avatar varchar(256),
  phone varchar(32),
  email varchar(128),
  qq varchar(32),
  locked tinyint(1) DEFAULT 0 COMMENT '是否锁定',
  create_time timestamp DEFAULT CURRENT_TIMESTAMP,
  update_time timestamp DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COMMENT='用户（账号）表';

drop table if exists auth_role;
create table auth_role(
    id bigint(64) auto_increment primary key,
    role_name varchar(64),
    description varchar(256),
    create_time timestamp DEFAULT CURRENT_TIMESTAMP,
    update_time timestamp DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COMMENT='角色表';

drop table if exists auth_permission;
create table auth_permission(
    id bigint(64) auto_increment primary key,
    permission_name varchar(64),
    description varchar(256),
    create_time timestamp DEFAULT CURRENT_TIMESTAMP,
    update_time timestamp DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COMMENT='权限表';

drop table if exists auth_user_role;
create table auth_user_role(
  id bigint(64) auto_increment primary key,
  user_id bigint(64) not null,
  role_id bigint(64) not null,
  create_time timestamp DEFAULT CURRENT_TIMESTAMP,
  update_time timestamp DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COMMENT='用户角色关联表';

drop table if exists auth_role_permission;
create table auth_role_permission(
  id bigint(64) auto_increment primary key,
  role_id bigint(64) not null,
  permission_id bigint(64) not null,
  create_time timestamp DEFAULT CURRENT_TIMESTAMP,
  update_time timestamp DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COMMENT='角色权限关联表';

insert into auth_user(user_name, password)values('zhang','123');
-- insert into shiro_user_role(username, role_name) values('zhang', 'admin');

drop table if exists menu;
create table menu(
     id bigint(64) auto_increment primary key,
     menu_name varchar(64) COMMENT '菜单名称',
     number varchar(64) COMMENT '菜单编号',
     url varchar(128) COMMENT '菜单路径',
     icon varchar(256) COMMENT '菜单图标',
     parent_id bigint(64) COMMENT '父菜单id',
     sort int COMMENT '排序',
     in_use tinyint COMMENT '是否启用',
     open_way tinyint COMMENT '打开方式',
     role varchar(256) COMMENT '有权限的角色',
     user varchar(512) COMMENT '有权限的用户',
     create_time timestamp DEFAULT CURRENT_TIMESTAMP,
     update_time timestamp DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COMMENT='菜单表';

drop table if exists article;
create table article(
     id bigint(64) auto_increment primary key,
     title varchar(128) COMMENT '标题',
     content text COMMENT '内容',
     column_id bigint(64) COMMENT '栏目id',
     published tinyint(1) COMMENT '发布/编辑状态',
     audited tinyint(1) COMMENT '审核状态',
     read_count int COMMENT '查看次数',
     creator_id varchar(256) COMMENT '创建者id',
     create_time timestamp DEFAULT CURRENT_TIMESTAMP,
     updator_id varchar(256) COMMENT '更新者id',
     update_time timestamp DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COMMENT='文章表';

-- 栏目表