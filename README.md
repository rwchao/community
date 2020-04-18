## 码匠社区

## 资料

[Spring Guides](https://spring.io/guides)  
[Spring Web Guide](https://spring.io/guides/gs/serving-web-content/)  
[elasticsearch 社区](https://elasticsearch.cn/)  
[Bootstrap](https://v3.bootcss.com/)  
[Github OAuth](https://developer.github.com/apps/building-oauth-apps/)  
[OkHttp](https://square.github.io/okhttp/)  
[菜鸟教程：MySQL](https://www.runoob.com/mysql/mysql-tutorial.html)  
[H2](https://www.h2database.com/html/main.html)  
[MyBatis](https://mybatis.org/spring-boot-starter/mybatis-spring-boot-autoconfigure/)  
[Spring Boot文档 database部分](https://docs.spring.io/spring-boot/docs/2.1.13.RELEASE/reference/html/boot-features-sql.html#boot-features-embedded-database-support)  
[Thymeleaf](https://www.thymeleaf.org/doc/tutorials/3.0/usingthymeleaf.html)  


## 工具
Git  
[Visual Paradigm](https://www.visual-paradigm.com/cn/download/community.jsp)  
[Flyway](https://flywaydb.org/getstarted/firststeps/maven)  
[Lombok](https://projectlombok.org/) (需要下载IDEA插件，暂时不使用此工具)
 
## 脚本
```sql
create table USER
(
	ID INT auto_increment,
	ACCOUNT_ID VARCHAR(100),
	NAME VARCHAR(50),
	TOKEN CHAR(36),
	GMT_CREATE BIGINT,
	GMT_MODIFIED BIGINT,
	constraint USER_PK
		primary key (ID)
);
```
```bash
mvn flyway:migrate
```