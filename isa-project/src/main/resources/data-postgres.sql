insert into country (id,name) values (1,'Srbija');
insert into country (id,name) values (2,'BiH');
insert into country (id,name) values (3,'Crna Gora');
insert into country (id,name) values (4,'Hrvatska');
insert into country (id,name) values (5,'Makedonija');
insert into country (id,name) values (6,'Slovenija');

insert into city (id,name,country_id) values (1,'Beograd',1);
insert into city (id,name,country_id) values (2,'Novi Sad',1);
insert into city (id,name,country_id) values (3,'Banja Luka',2);
insert into city (id,name,country_id) values (4,'Heceg Novi',3);
insert into city (id,name,country_id) values (5,'Zagreb',4);
insert into city (id,name,country_id) values (6,'Skoplje',5);
insert into city (id,name,country_id) values (7,'Ljubljana',6);

insert into pharmacy (id, name, city_id) values (1,'Janković',1);
insert into pharmacy (id, name, city_id) values (2,'Pharma',2);
insert into pharmacy (id, name, city_id) values (3,'Betty',2);
insert into pharmacy (id, name, city_id) values (4,'Crvena apoteka',3);

insert into pharmacist (id, name, surname, email, password,pharmacy_id) values 
					   (nextval('users_seq'),'Marija','Marić','marija.maric@gmail.com','marija1234',1);
insert into dermatologist (id, name, surname, email, password) values (nextval('users_seq'),'Nada','Nadić','nada.nadic@gmail.com','nada1234');

insert into dermatologist_pharmacies (dermatologist_id,pharmacies_id) values (2,1);
insert into dermatologist_pharmacies (dermatologist_id,pharmacies_id) values (2,2);