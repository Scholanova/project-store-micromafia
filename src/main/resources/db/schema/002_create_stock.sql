--liquibase formatted sql

--changeset scholanova:1
CREATE TABLE IF NOT EXISTS STOCK (
  ID                  SERIAL          NOT NULL,
  NAME                VARCHAR(255)    NOT NULL,
  TYPE				  VARCHAR(255)	  NOT NULL,
  VALUE	              INTEGER 		  NOT NULL,
  ID_STORE 			  INTEGER    	  NOT NULL,
  FOREIGN KEY (ID_STORE) REFERENCES STORES(ID),
  PRIMARY KEY (ID)
);