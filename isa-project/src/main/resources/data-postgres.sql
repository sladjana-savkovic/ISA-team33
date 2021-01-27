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

insert into doctor (id, name, surname, email, password, type_of_doctor,city_id) 
					values (nextval('users_seq'),'Nada','Nadić','nada.nadic@gmail.com','nada1234',0,1);
insert into doctor (id, name, surname, email, password,type_of_doctor,city_id) 
					values (nextval('users_seq'),'Marija','Marić','marija.maric@gmail.com','marija1234',1,2);
					   
insert into patient(id, name, surname, email, password,city_id) 
					values (nextval('users_seq'),'Pera','Perić','pera.peric@gmail.com','pera1234',1);
insert into patient(id, name, surname, email, password,city_id) 
					values (nextval('users_seq'),'Ana','Anić','ana.anic@gmail.com','ana1234',1);

insert into doctor_pharmacies (doctor_id,pharmacies_id) values (1,1);
insert into doctor_pharmacies (doctor_id,pharmacies_id) values (2,1);
insert into doctor_pharmacies (doctor_id,pharmacies_id) values (2,2);

insert into drug (id, name, type_of_drug, type_of_drugs_form, producer) values (1, 'Amoksicilin', 1, 1, 'Hemofarm');
insert into drug (id, name, type_of_drug, type_of_drugs_form, producer) values (2, 'Cefaleksin', 1, 2, 'Hemofarm');
insert into drug (id, name, type_of_drug, type_of_drugs_form, producer) values (3, 'Brufen', 0, 0, 'Hemofarm');
insert into drug (id, name, type_of_drug, type_of_drugs_form, producer) values (4, 'Probiotik Forte', 3, 3, 'Hemofarm');

insert into drug_pharmacy (drug_id,pharmacy_id) values (1,1);
insert into drug_pharmacy (drug_id,pharmacy_id) values (2,1);
insert into drug_pharmacy (drug_id,pharmacy_id) values (3,1);
insert into drug_pharmacy (drug_id,pharmacy_id) values (1,2);
insert into drug_pharmacy (drug_id,pharmacy_id) values (2,2);

insert into ingredient (id, name) values (1, 'Paracetamol');
insert into ingredient (id, name) values (2, 'Mg');
insert into ingredient (id, name) values (3, 'Fiziološki rastvor');
insert into ingredient (id, name) values (4, 'Etanol');

insert into drug_ingredient (drug_id, ingredient_id) values (1,1);
insert into drug_ingredient (drug_id, ingredient_id) values (1,2);
insert into drug_ingredient (drug_id, ingredient_id) values (2,1);
insert into drug_ingredient (drug_id, ingredient_id) values (2,3);
insert into drug_ingredient (drug_id, ingredient_id) values (3,1);
insert into drug_ingredient (drug_id, ingredient_id) values (3,3);
insert into drug_ingredient (drug_id, ingredient_id) values (4,1);
insert into drug_ingredient (drug_id, ingredient_id) values (4,4);

insert into examination (id,date_time,diagnosis,type_of_examination,doctor_id,patient_id,pharmacy_id) 
					values (nextval('examinations_seq'),'2020-12-30 12:00:00','Upala pluća',0,1,3,1);
insert into examination (id,date_time,diagnosis,type_of_examination,doctor_id,patient_id,pharmacy_id) 
					values (nextval('examinations_seq'),'2021-01-13 07:30:00','Glavobolja',0,1,4,2);
insert into examination (id,date_time,diagnosis,type_of_examination,doctor_id,patient_id,pharmacy_id) 
					values (nextval('examinations_seq'),'2020-11-16 13:00:00','Koristiti redovno propisanu terapiju',1,2,3,2);
insert into examination (id,date_time,diagnosis,type_of_examination,doctor_id,patient_id,pharmacy_id) 
					values (nextval('examinations_seq'),'2020-12-03 15:30:00','Popiti jos jednu dozu lijekova',1,2,4,1);
					
insert into patient_allergies (patient_id,allergies_id) values (3,1);
insert into patient_allergies (patient_id,allergies_id) values (4,2);

insert into examination_therapies (examination_id,therapies_id) values (1,1);
insert into examination_therapies (examination_id,therapies_id) values (1,2);
insert into examination_therapies (examination_id,therapies_id) values (2,3);
insert into examination_therapies (examination_id,therapies_id) values (3,4);