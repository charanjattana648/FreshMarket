--DROP TABLE customer IF EXISTS;
--DROP TABLE admin IF EXISTS;
--DROP TABLE item IF EXISTS;
--DROP TABLE Purchase_History IF EXISTS;
--DROP TABLE Order_details IF EXISTS;


CREATE TABLE User (
  email VARCHAR(255) PRIMARY KEY,
  name VARCHAR(255),
  password VARCHAR(255),
  userType VARCHAR(255)
);



CREATE TABLE item (
  itemId INTEGER auto_increment PRIMARY KEY,
  itemName VARCHAR(255) unique,
  itemType VARCHAR(255),
  itemPrice DOUBLE,
  itemCount INTEGER,
  itemImage BLOB,
  itemDesc VARCHAR(500)
);

CREATE TABLE Purchase_History(
  orderId INTEGER auto_increment PRIMARY KEY,
  email VARCHAR(255),
  status VARCHAR(255),
  total_Price DOUBLE,
  order_time VARCHAR(255)
);

CREATE TABLE Order_details(
  id INTEGER auto_increment PRIMARY KEY,
  itemName VARCHAR(255),
  itemId INTEGER,
  orderId INTEGER,
  itemQty INTEGER,
  itemPrice DOUBLE,
  item_total_Price DOUBLE,
  foreign key (orderId) references Purchase_History(orderId)
);
