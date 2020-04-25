alter table NOTIFICATION alter column NOTIFIER rename to NOTIFIER_ID;

alter table NOTIFICATION alter column RECEIVER rename to RECEIVER_ID;

alter table NOTIFICATION alter column TARGET_ID rename to COMMENT_ID;

alter table NOTIFICATION alter column COMMENT_ID set null;

alter table NOTIFICATION
	add QUESTION_ID bigint not null;

