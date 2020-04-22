alter table COMMENT alter column PARENT_ID bigint not null;

alter table QUESTION alter column ID bigint auto_increment;
alter table QUESTION alter column CREATOR bigint;

alter table `USER` alter column ID bigint auto_increment;
