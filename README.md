# Springboot-MultiDatasource-Mybatis
oracle과 mybatis mybatis 연동


```oracle
create USER test IDENTIFIED by 1234; -- 사용자이름 superuser DB이름 supreruser
grant create session to test;
grant create table to test;
grant create sequence to test;
grant unlimited tablespace to test;


  
  ----접속 후-------

create table panama
(
  id number not null,
  productId  number ,
  username VARCHAR2(100));
  
create SEQUENCE panama_seq  -- 시퀀스이름은 무조건 이런식으로 정한다고 생각하자
increment by 1  --1씩 증가
start with 1; -- 1부터

```





```mysql


create user 'test' @'%' IDENTIFIED by '1234';
GRANT all PRIVILEGES on *.* to 'test'@'%';
create DATABASE test;

-------접속 후-----------


use test;


create table product(
	id int auto_increment primary key,
	name varchar(50),
    code varchar(50)
);


create table panama(
	id int auto_increment primary key,
	productId int,
    username varchar(50)
);

insert into panama(productId, username) values(1, '홍길동');
insert into panama(productId, username) values(2, '홍길동');
insert into panama(productId, username) values(3, '강호동');


```



