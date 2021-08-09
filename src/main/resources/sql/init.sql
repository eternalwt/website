drop database if exists website;
create database website default character set utf8mb4 collate utf8mb4_unicode_ci;
set global time_zone='+8:00';
use website;

-- todo 字段注释补完善。并且有一些很简陋，没有default之类的
-- todo Integer display width is deprecated and will be removed in a future release：https://www.cnblogs.com/kukufan/p/12485609.html
-- todo 表上没有collate，要么2个都有，要么2个都没有（用数据库的)

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
    status tinyint(1) DEFAULT 0 COMMENT '状态：0正常，1锁定',
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

drop table if exists sys_department;
create table `sys_department`(
	`id` bigint(64) not null  auto_increment primary key comment '部门id',
	`dept_name` VARCHAR(200) not null DEFAULT '' comment '部门名称',
	`parent_id` bigint(64) not null DEFAULT 0  comment  '上级部门id' ,
	`level` int DEFAULT 0 comment '部门层级',
	`sort` int DEFAULT 0 comment '部门在当前层级下的顺序，由小到大',
	`remark` VARCHAR(200) DEFAULT '' comment '备注',
	`create_time` timestamp DEFAULT CURRENT_TIMESTAMP,
    `update_time` timestamp DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COMMENT='部门表';

drop table if exists sys_user_department;
create table `sys_user_department`(
    id bigint(64) auto_increment primary key,
    user_id bigint(64) not null,
    department_id bigint(64) not null,
    create_time timestamp DEFAULT CURRENT_TIMESTAMP,
    update_time timestamp DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COMMENT='用户部门关联表';

drop table if exists sys_position;
create table `sys_position`(
	`id` bigint(64) not null  auto_increment primary key comment '职务id',
	`position_name` VARCHAR(200) not null DEFAULT '' comment '职务名称',
	`parent_id` bigint(64) not null DEFAULT 0  comment  '上级职务id' ,
-- 	`level` int DEFAULT 0 comment '职务层级',
	`sort` int DEFAULT 0 comment '职务在当前层级下的顺序，由小到大',
	`remark` VARCHAR(200) DEFAULT '' comment '备注',
	`create_time` timestamp DEFAULT CURRENT_TIMESTAMP,
    `update_time` timestamp DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COMMENT='职位表';

drop table if exists sys_dictionary;
create table `sys_user_position`(
    id bigint(64) auto_increment primary key,
    user_id bigint(64) not null,
    position_id bigint(64) not null,
    create_time timestamp DEFAULT CURRENT_TIMESTAMP,
    update_time timestamp DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COMMENT='用户职位关联表';

drop table if exists sys_dictionary;
create table `sys_dictionary`(
    id bigint(64) auto_increment primary key,
    type varchar(64),
    key varchar(64),
    value varchar(64),
    parent_id bigint(64) not null,
    level int DEFAULT 0 comment '层级',
    create_time timestamp DEFAULT CURRENT_TIMESTAMP,
    update_time timestamp DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COMMENT='字典表';

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

-- 初始化数据
insert into auth_role(`role_name`) values("admin");
insert into auth_user(user_name, password, password_salt)
    values('admin','7262db0dd9c3865016dac3227a3fbfb4', '83061e526dff50fff56d4ff587d7f581');
insert into auth_user_role("user_id", "role_id") values(1,1);

insert into menu(menu_name, url, sort, in_use, role) values('控制台', '/home/main', 1, 1, '1');
insert into menu(menu_name, url, sort, in_use, `role`) values('添加用户', '/home/adduser', 2, 1, '1');
insert into menu(menu_name, url, sort, in_use, `role`) values('用户列表', '/home/userlist', 3, 1, '1');
insert into menu(menu_name, url, sort, in_use, `role`) values('添加角色', '/home/addrole', 4, 1, '1');
insert into menu(menu_name, url, sort, in_use, `role`) values('角色列表', '/home/rolelist', 5, 1, '1');
insert into menu(menu_name, url, sort, in_use, `role`) values('发表文章', '/home/addarticle', 6, 1, '1');
insert into menu(menu_name, url, sort, in_use, `role`) values('文章列表', '/home/articlelist', 7, 1, '1');
insert into menu(menu_name, url, sort, in_use, `role`) values('添加菜单', '/home/addmenu', 8, 1, '1');
insert into menu(menu_name, url, sort, in_use, `role`) values('权限配置', '/home/permassign', 9, 1, '1');
insert into menu(menu_name, url, sort, in_use, `role`) values('仪表盘', '/home/dashboard', 10, 1, '1');
insert into menu(menu_name, url, sort, in_use, `role`) values('material组件', '/home/components', 11, 1, '1');
insert into menu(menu_name, url, sort, in_use, `role`) values('第三方组件', '/home/3rdparty', 12, 1, '1');
insert into menu(menu_name, url, sort, in_use, `role`) values('消息中心', '/home/message', 13, 1, '1');
insert into menu(menu_name, url, sort, in_use, `role`) values('联系信息', '/home/contact', 14, 1, '1');
