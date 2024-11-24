drop table account;
create table ACCOUNT (
                         ID BIGINT PRIMARY KEY,
                         VERSION INTEGER NOT NULL,
                         NAME VARCHAR(30) NOT NULL,
                         SURNAME VARCHAR(50) NOT NULL,
                         EMAIL   VARCHAR(100) NOT NULL, CONSTRAINT EMAIL_UNIQUE unique (EMAIL),
                         QUESTION VARCHAR(80) NOT NULL,
                         ANSWER VARCHAR(30) NOT NULL,
                         LOGIN VARCHAR(30) NOT NULL, CONSTRAINT LOGIN_UNIQUE unique (LOGIN),
                         PASSWORD VARCHAR(64) NOT NULL,
                         ACTIVE BOOLEAN NOT NULL,
                         LEVEL_OF_ACCESS VARCHAR(31) NOT NULL,
                         AUTHORIZED_BY BIGINT REFERENCES ACCOUNT(ID),
                         MODIFICATED_BY BIGINT REFERENCES ACCOUNT(ID),
                         CREATION_DATE TIMESTAMP NOT NULL,
                         MODIFICATION_DATE TIMESTAMP);