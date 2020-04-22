create table comment
(
	id bigint auto_increment,
	parent_id int not null,
	type int not null,
	commentator int not null,
	gmt_create bigint not null,
	gmt_modified bigint not null,
	like_count bigint default 0,
	content text not null,
	constraint comment_pk
		primary key (id)
);

comment on column comment.parent_id is '所评论问题的id';

comment on column comment.type is '区分一级评论和二级评论';

comment on column comment.commentator is '评论人id';

comment on column comment.gmt_create is '创建时间';

comment on column comment.gmt_modified is '更新时间';

comment on column comment.like_count is '点赞数';

comment on column comment.content is '评论内容';

