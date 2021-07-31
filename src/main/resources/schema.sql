
DROP TABLE IF EXISTS MY_USER;
DROP SEQUENCE IF EXISTS MY_USER_SEQ;

CREATE TABLE MY_USER (
  MY_USER_ID INT PRIMARY KEY,
  USERNAME VARCHAR(250) NOT NULL,
  ENC_PASSWORD VARCHAR(250) NOT NULL,
  IS_LOCKED INT NOT NULL,
  IS_DISABLED INT NOT NULL,
  FAILED_ATTEMPTS INT NOT NULL,
  LAST_PASSWORD_DATE DATE NOT NULL
);

CREATE TABLE MY_LOGIN_ATTEMPT (
    MY_LOGIN_ATTEMPT_ID INT PRIMARY KEY,
	USERNAME VARCHAR(250) NOT NULL,
	RECORD_DATE TImESTAMP NOT NULL,
	IP_ADDRESS VARCHAR(250) NULL
);

CREATE TABLE MY_PASSWORD_HIST (
    MY_PASSWORD_HIST_ID INT PRIMARY KEY,
	MY_USER_ID VARCHAR(250) NOT NULL,
	ENC_PASSWORD VARCHAR(250) NOT NULL,
	RECORD_DATE TImESTAMP NOT NULL
);

CREATE SEQUENCE MY_PASSWORD_HIST_SEQ START WITH 1 INCREMENT BY 1;
CREATE SEQUENCE MY_LOGIN_ATTEMPT_SEQ START WITH 1 INCREMENT BY 1;
CREATE SEQUENCE MY_USER_SEQ START WITH 1 INCREMENT BY 1;

CREATE TABLE DUMMY (
  DUMMY_INT INT,
  DUMMY_VARCHAR VARCHAR(250),
  DUMMY_DATE DATE
);
