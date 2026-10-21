--13 JdbcTemplate 사용하기
alter session set "_ORACLE_SCRIPT"=true;

CREATE USER study IDENTIFIED BY 1234;
GRANT CONNECT, RESOURCE TO study; 
ALTER USER study DEFAULT TABLESPACE users QUOTA UNLIMITED ON users;

drop table myuser;

create table myuser (
    id varchar2(10),
    name varchar2(10)
);

insert into myuser values ('test1', '홍길동1');
insert into myuser values ('test2', '홍길동2');
insert into myuser values ('test3', '홍길동3');

commit;

-- 14 간단한 게시판 만들기
create table simple_bbs (
    id number(4) primary key,
    writer varchar2(100),
    title varchar2(100),
    content varchar2(100)
);

create sequence simple_bbs_seq;

-- 19 MyBatis로 JOIN 조회 결과 출력하기
drop table emp;
drop table dept;

CREATE TABLE dept (
    deptno NUMBER(2) PRIMARY KEY,
    dname VARCHAR2(30) NOT NULL
);

CREATE TABLE emp (
    empno NUMBER(4) PRIMARY KEY,
    ename VARCHAR2(30) NOT NULL,
    deptno NUMBER(2),
    CONSTRAINT fk_ch19_emp_dept
        FOREIGN KEY (deptno) REFERENCES dept(deptno)
);

INSERT INTO dept (deptno, dname) VALUES (10, 'ACCOUNTING');
INSERT INTO dept (deptno, dname) VALUES (20, 'RESEARCH');

INSERT INTO emp (empno, ename, deptno) VALUES (7782, 'CLARK', 10);
INSERT INTO emp (empno, ename, deptno) VALUES (7839, 'KING', 10);
INSERT INTO emp (empno, ename, deptno) VALUES (7934, 'MILLER', 10);
INSERT INTO emp (empno, ename, deptno) VALUES (7369, 'SMITH', 20);

COMMIT;

SELECT e.ename, d.deptno, d.dname
FROM emp e
JOIN dept d ON e.deptno = d.deptno
WHERE d.deptno = 10
ORDER BY e.empno;

--22 트랜잭션 미적용 에러 상황
drop table TRANSACTION1;
create table TRANSACTION1 (
    consumerid varchar2(20),
    amount number
);

drop table TRANSACTION2;
create table TRANSACTION2 (
    consumerid varchar2(20),
    amount number
);

drop table TRANSACTION3;
create table TRANSACTION3 (
    consumerid varchar2(20),
    amount number
);

insert into TRANSACTION1 values ('C1', 100);
insert into TRANSACTION2 values ('C1', 100);
insert into TRANSACTION3 values ('C1', 100);
commit;

delete from TRANSACTION1;
delete from TRANSACTION2;
delete from TRANSACTION3;
commit;

select * from TRANSACTION1;
select * from TRANSACTION2;
select * from TRANSACTION3;

--30 시큐리티 데이터베이스 사용하기
drop table user_list;

create table user_list (
    name varchar2(20) primary key,
    password varchar2(100),
    authority varchar2(20),
    enabled number(1)
);

insert into user_list values ('user', '암호화된 패스워드', 'ROLE_USER', 1);
insert into user_list values ('admin', '암호화된 패스워드', 'ROLE_ADMIN', 1);
commit;

select * from user_list;

--38 Paging과 Sorting
create table JPAMEMBER03 (
    ID NUMBER(19,0) NOT NULL,
    EMAIL VARCHAR2(255 CHAR),
    NAME VARCHAR2(255 CHAR),
    PRIMARY KEY (ID)
);
create sequence JPAMEMBER03_SEQ
    start with 1
    increment by 1;

insert into JPAMEMBER03 (id, name, email) values (01, 'test01@test.com', 'test01');
insert into JPAMEMBER03 (id, name, email) values (02, 'test02@test.com', 'test02');
insert into JPAMEMBER03 (id, name, email) values (03, 'test03@test.com', 'test03');
insert into JPAMEMBER03 (id, name, email) values (04, 'test04@test.com', 'test04');
insert into JPAMEMBER03 (id, name, email) values (05, 'test05@test.com', 'test05');
insert into JPAMEMBER03 (id, name, email) values (06, 'test06@test.com', 'test06');
insert into JPAMEMBER03 (id, name, email) values (07, 'test07@test.com', 'test07');
insert into JPAMEMBER03 (id, name, email) values (08, 'test08@test.com', 'test08');
insert into JPAMEMBER03 (id, name, email) values (09, 'test09@test.com', 'test09');
insert into JPAMEMBER03 (id, name, email) values (10, 'test10@test.com', 'test10');
insert into JPAMEMBER03 (id, name, email) values (11, 'test11@test.com', 'test11');
insert into JPAMEMBER03 (id, name, email) values (12, 'test12@test.com', 'test12');
insert into JPAMEMBER03 (id, name, email) values (13, 'test13@test.com', 'test13');
insert into JPAMEMBER03 (id, name, email) values (14, 'test14@test.com', 'test14');
insert into JPAMEMBER03 (id, name, email) values (15, 'test15@test.com', 'test15');
insert into JPAMEMBER03 (id, name, email) values (16, 'test16@test.com', 'test16');
insert into JPAMEMBER03 (id, name, email) values (17, 'test17@test.com', 'test17');
insert into JPAMEMBER03 (id, name, email) values (18, 'test18@test.com', 'test18');
insert into JPAMEMBER03 (id, name, email) values (19, 'test19@test.com', 'test19');
insert into JPAMEMBER03 (id, name, email) values (20, 'test20@test.com', 'test20');
insert into JPAMEMBER03 (id, name, email) values (21, 'test21@test.com', 'test21');
insert into JPAMEMBER03 (id, name, email) values (22, 'test22@test.com', 'test22');
insert into JPAMEMBER03 (id, name, email) values (23, 'test23@test.com', 'test23');
insert into JPAMEMBER03 (id, name, email) values (24, 'test24@test.com', 'test24');
commit;



