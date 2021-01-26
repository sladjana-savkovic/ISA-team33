insert into country (id,name) values (1,'Srbija');
insert into country (id,name) values (2,'BiH');
insert into country (id,name) values (3,'Crna Gora');
insert into country (id,name) values (4,'Hrvatska');
insert into country (id,name) values (5,'Makedonija');
insert into country (id,name) values (6,'Slovenija');

insert into pharmacist (id, name, surname, email, password) values (nextval('users_seq'),'Marija','Marić','marija.maric@gmail.com','marija1234');
insert into dermatologist (id, name, surname, email, password) values (nextval('users_seq'),'Nada','Nadić','nada.nadic@gmail.com','nada1234');