drop table product;
create table PRODUCT (
                         ID BIGINT PRIMARY KEY,
                         VERSION INTEGER NOT NULL,
                         PRODUCT_SYMBOL VARCHAR(13) NOT NULL, CONSTRAINT PRODUCT_UNIQUE unique (PRODUCT_SYMBOL),
                         DESCRIPTION VARCHAR(500) NOT NULL,
                         PRICE DECIMAL(8,2) NOT NULL,
                         WEIGHT_IN_G INTEGER NOT NULL,
                         EASILY_DAMAGE BOOLEAN NOT NULL,
                         CREATED_BY BIGINT REFERENCES ACCOUNT(ID),
                         MODIFICATED_BY BIGINT REFERENCES ACCOUNT(ID),
                         CREATION_DATE TIMESTAMP NOT NULL,
                         MODIFICATION_DATE TIMESTAMP);