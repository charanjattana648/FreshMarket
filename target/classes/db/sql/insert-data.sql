
INSERT INTO USER VALUES ('rimmi@gmail.com','raminder','pass123','customer');
INSERT INTO USER VALUES ('admin_user','admin_name','rimmi','admin');

INSERT INTO ITEM (ITEMNAME, ITEMTYPE, ITEMPRICE, ITEMCOUNT, ITEMIMAGE, ITEMDESC)  VALUES ('Apple','Fruit',4.74,30,FILE_READ('C:/Users/charan/Desktop/studyData/JSP Practice/dir/registrationdemo/src/main/resources/db/images/apple.jfif'),'Galla, Produced in USA');
INSERT INTO ITEM (ITEMNAME, ITEMTYPE, ITEMPRICE, ITEMCOUNT, ITEMIMAGE, ITEMDESC)  VALUES ('Banana','Fruit',1.45,24,FILE_READ('C:/Users/charan/Desktop/studyData/JSP Practice/dir/registrationdemo/src/main/resources/db/images/banana.jfif'),'Produced in SA');
INSERT INTO ITEM (ITEMNAME, ITEMTYPE, ITEMPRICE, ITEMCOUNT, ITEMIMAGE, ITEMDESC)  VALUES ('Lettuce','Vegie',2.97,15,FILE_READ('C:/Users/charan/Desktop/studyData/JSP Practice/dir/registrationdemo/src/main/resources/db/images/letuce.jfif'),'Produced in Canada');

--INSERT INTO users VALUES (1, 'mkyong', 'mkyong@gmail.com');
--INSERT INTO users VALUES (2, 'alex', 'alex@yahoo.com');
--INSERT INTO users VALUES (3, 'joel', 'joel@gmail.com');(ITEMNAME, ITEMTYPE, ITEMPRICE, ITEMCOUNT, ITEMIMAGE, ITEMDESC) 
--
--INSERT INTO students VALUES('lis@douglascollege.ca', 'Simon Li', '123456');
--INSERT INTO students VALUES('wongi5@douglascollege.ca', 'Ivan Wong', '654321');
--
--INSERT INTO courses VALUES('CSIS1175', 'Introduction to Programming 1');
--INSERT INTO courses VALUES('CSIS1275', 'Introduction to Programming 2');
--
--INSERT INTO registrations VALUES('wongi5@douglascollege.ca', 'CSIS1275');