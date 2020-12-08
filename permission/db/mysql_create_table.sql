create schema if not exists wms_cli_permission collate utf8mb4_0900_ai_ci;

create table if not exists dept
(
    insert_id varchar(255) not null comment '新增人',
    insert_ymd timestamp default CURRENT_TIMESTAMP null comment '新增日期',
    update_ymd timestamp default CURRENT_TIMESTAMP null on update CURRENT_TIMESTAMP comment '更新日期',
    update_id varchar(255) not null comment '更新人',
    remark varchar(255) null,
    remark1 varchar(255) null comment '备用字段',
    remark2 varchar(255) null comment '备用字段',
    remark3 varchar(255) null comment '备用字段',
    remark4 varchar(255) null comment '备用字段',
    remark5 varchar(255) null comment '备用字段',
    seq int null,
    effective int default 1 null comment '是否有效',
    id bigint auto_increment comment 'id
'
        primary key,
    name varchar(255) not null,
    parent_id bigint null comment 'parent id'
)
    comment '部门';

create table if not exists menu
(
    insert_id varchar(255) not null comment '新增人',
    insert_ymd timestamp default CURRENT_TIMESTAMP null comment '新增日期',
    update_ymd timestamp default CURRENT_TIMESTAMP null on update CURRENT_TIMESTAMP comment '更新日期',
    update_id varchar(255) not null comment '更新人',
    remark varchar(255) null,
    remark1 varchar(255) null comment '备用字段',
    remark2 varchar(255) null comment '备用字段',
    remark3 varchar(255) null comment '备用字段',
    remark4 varchar(255) null comment '备用字段',
    remark5 varchar(255) null comment '备用字段',
    seq int null,
    effective int default 1 null comment '是否有效',
    id bigint auto_increment comment 'id'
        primary key,
    name varchar(255) not null comment 'name',
    level int not null comment 'menu level  one model/two menu /three btn',
    parent_id bigint null,
    ico varchar(255) null,
    path varchar(255) null comment 'router',
    posi varchar(1) null comment 'vue file where is ',
    type int not null comment 'one bs,two cs'
)
    comment '菜单';

create table if not exists operator
(
    insert_id varchar(255) not null comment '新增人',
    insert_ymd timestamp default CURRENT_TIMESTAMP null comment '新增日期',
    update_ymd timestamp default CURRENT_TIMESTAMP null on update CURRENT_TIMESTAMP comment '更新日期',
    update_id varchar(255) not null comment '更新人',
    remark varchar(255) null,
    remark1 varchar(255) null comment '备用字段',
    remark2 varchar(255) null comment '备用字段',
    remark3 varchar(255) null comment '备用字段',
    remark4 varchar(255) null comment '备用字段',
    remark5 varchar(255) null comment '备用字段',
    seq int null,
    effective int default 1 null comment '是否有效',
    id bigint auto_increment comment 'id'
        primary key,
    nick_name varchar(255) not null comment 'ni cheng',
    passwd varchar(255) not null comment 'password',
    email varchar(255) null,
    gender int not null,
    birthday varchar(255) null,
    position varchar(100) null,
    dept_id bigint null,
    code varchar(255) null,
    status int default 1 not null
)
    comment '操作人';

create table if not exists role
(
    insert_id varchar(255) not null comment '新增人',
    insert_ymd timestamp default CURRENT_TIMESTAMP null comment '新增日期',
    update_ymd timestamp default CURRENT_TIMESTAMP null on update CURRENT_TIMESTAMP comment '更新日期',
    update_id varchar(255) not null comment '更新人',
    remark varchar(255) null,
    remark1 varchar(255) null comment '备用字段',
    remark2 varchar(255) null comment '备用字段',
    remark3 varchar(255) null comment '备用字段',
    remark4 varchar(255) null comment '备用字段',
    remark5 varchar(255) null comment '备用字段',
    seq int null,
    effective int default 1 null comment '是否有效',
    id bigint auto_increment
        primary key,
    name varchar(255) not null,
    `desc` varchar(255) null
)
    comment '角色表';

create table if not exists role_menu
(
    insert_id varchar(255) not null comment '新增人',
    insert_ymd timestamp default CURRENT_TIMESTAMP null comment '新增日期',
    update_ymd timestamp default CURRENT_TIMESTAMP null on update CURRENT_TIMESTAMP comment '更新日期',
    update_id varchar(255) not null comment '更新人',
    remark varchar(255) null,
    remark1 varchar(255) null comment '备用字段',
    remark2 varchar(255) null comment '备用字段',
    remark3 varchar(255) null comment '备用字段',
    remark4 varchar(255) null comment '备用字段',
    remark5 varchar(255) null comment '备用字段',
    seq int null,
    effective int default 1 null comment '是否有效',
    id bigint auto_increment
        primary key,
    role_id bigint not null,
    menu_id bigint not null
)
    comment '角色菜单';

create index role_menu_id_uindex
    on role_menu (id);

create table if not exists user_role
(
    insert_id varchar(255) not null comment '新增人',
    insert_ymd timestamp default CURRENT_TIMESTAMP null comment '新增日期',
    update_ymd timestamp default CURRENT_TIMESTAMP null on update CURRENT_TIMESTAMP comment '更新日期',
    update_id varchar(255) not null comment '更新人',
    remark varchar(255) null,
    remark1 varchar(255) null comment '备用字段',
    remark2 varchar(255) null comment '备用字段',
    remark3 varchar(255) null comment '备用字段',
    remark4 varchar(255) null comment '备用字段',
    remark5 varchar(255) null comment '备用字段',
    seq int null,
    effective int default 1 null comment '是否有效',
    id bigint auto_increment
        primary key,
    user_id bigint not null,
    role_id bigint not null
)
    comment '用户角色';

