create table if not exists wms_cli_basedata.dictionary
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
	model_code varchar(255) not null comment '模块',
	dict_key varchar(255) not null,
	dict_value varchar(255) not null
)
comment '数据字典';