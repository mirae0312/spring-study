--========================================
-- 관리자 계정 - spring계정 생성
--========================================

--ALTER SESSION SET "_oracle_script" = TRUE; -- 일반사용자 c## 접두어 없이 계정생성

--CREATE USER SPRING
--indentified BY SPRING
--DEFAULT tablespace users;
--
--ALTER USER SPRING quota unlimited ON users;
--
--GRANT CONNECT, resource TO SPRING;

ALTER SESSION SET "_oracle_script" = TRUE;

CREATE USER SPRING
IDENTIFIED BY rlaalfo580312E
DEFAULT tablespace users;

CREATE USER SPRING
IDENTIFIED BY spring
DEFAULT tablespace users;


ALTER USER SPRING QUOTA UNLIMITED ON USERS;

GRANT CONNECT, RESOURCE TO SPRING;

--========================================
-- spring 계정
--========================================
-- dev테이블
CREATE TABLE DEV (
	NO NUMBER,
	NAME VARCHAR2(50) NOT NULL,
	CAREER NUMBER NOT NULL,
	EMAIL VARCHAR2(200) NOT NULL,
	GENDER CHAR(1),
	LANG VARCHAR2(100) NOT NULL,
	CONSTRAINT PK_DEV_NO PRIMARY KEY(NO),
	CONSTRAINT CK_DEV_GENDER CHECK(GENDER IN ('M', 'F'))
);

CREATE SEQUENCE SEQ_DEV_NO;

SELECT * FROM DEV;


