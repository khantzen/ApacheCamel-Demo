CREATE DATABASE khantzen
  DEFAULT CHARACTER SET utf8
  DEFAULT COLLATE utf8_general_ci;

use khantzen;

Create Table Users (
  id int auto_increment not null,
  FirstName varchar(50) not null,
  LastName varchar(50) not null,
  Email varchar(75) not null,
  BirthDate DateTime not null,
  Primary Key (id)
);

Create Table Audit (
  id int auto_increment not null,
  Message varchar(50),
  Sender varchar(50),
  Owner varchar(50),
  Date DateTime NOT NULL default  CURRENT_TIMESTAMP,
  Level Varchar(10),
  PRIMARY KEY (id)
);
