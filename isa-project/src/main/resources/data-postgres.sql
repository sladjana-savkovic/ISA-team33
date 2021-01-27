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


insert into drug (id, name, type_of_drug, type_of_drugs_form, producer) values (nextval('drugs_seq'), 'Amoksicilin', 1, 1, 'Hemofarm');
insert into drug (id, name, type_of_drug, type_of_drugs_form, producer) values (nextval('drugs_seq'), 'Cefaleksin', 1, 2, 'Hemofarm');
insert into drug (id, name, type_of_drug, type_of_drugs_form, producer) values (nextval('drugs_seq'), 'Brufen', 0, 0, 'Hemofarm');
insert into drug (id, name, type_of_drug, type_of_drugs_form, producer) values (nextval('drugs_seq'), 'Probiotik Forte', 3, 3, 'Hemofarm');

insert into drug_substitute_drugs (drug_id,substitute_drugs_id) values (1,3);

insert into drug_pharmacies (drug_id,pharmacies_id) values (1,1);
insert into drug_pharmacies (drug_id,pharmacies_id) values (2,1);
insert into drug_pharmacies (drug_id,pharmacies_id) values (3,1);
insert into drug_pharmacies (drug_id,pharmacies_id) values (1,2);
insert into drug_pharmacies (drug_id,pharmacies_id) values (2,2);

insert into ingredient (id, name) values (1, 'Paracetamol');
insert into ingredient (id, name) values (2, 'Mg');
insert into ingredient (id, name) values (3, 'Fiziološki rastvor');
insert into ingredient (id, name) values (4, 'Etanol');

insert into drug_ingredients (drug_id, ingredients_id) values (1,1);
insert into drug_ingredients (drug_id, ingredients_id) values (1,2);
insert into drug_ingredients (drug_id, ingredients_id) values (2,1);
insert into drug_ingredients (drug_id, ingredients_id) values (2,3);
insert into drug_ingredients (drug_id, ingredients_id) values (3,1);
insert into drug_ingredients (drug_id, ingredients_id) values (3,3);
insert into drug_ingredients (drug_id, ingredients_id) values (4,1);
insert into drug_ingredients (drug_id, ingredients_id) values (4,4);

insert into examination (id,date_time,diagnosis,type_of_examination,doctor_id, patient_id, pharmacy_id) 
					values (nextval('examinations_seq'),'2020-12-30 12:00:00','Upala pluća',0,1,3,1);
insert into examination (id,date_time,diagnosis,type_of_examination,doctor_id, patient_id, pharmacy_id) 
					values (nextval('examinations_seq'),'2021-01-13 07:30:00','Glavobolja',0,1,4,2);
			
insert into examination (id,date_time,diagnosis,type_of_examination,doctor_id, patient_id, pharmacy_id) 
					values (nextval('examinations_seq'),'2020-11-16 13:00:00','Koristiti redovno propisanu terapiju',1,2,3,2);
insert into examination (id,date_time,diagnosis,type_of_examination,doctor_id, patient_id, pharmacy_id) 
					values (nextval('examinations_seq'),'2020-12-03 15:30:00','Popiti jos jednu dozu lijekova',1,2,4,1);
					
insert into pharmacy_administrator (id, name, surname, email, password, pharmacy_id) values 
					   (nextval('users_seq'),'Miloš','Milošević','milosm@gmail.com','milos1234',1);
insert into pharmacy_administrator (id, name, surname, email, password, pharmacy_id) values 
					   (nextval('users_seq'),'Darko','Darković','darkod@gmail.com','darko1234',2);

insert into pharmacy_action (id, description, end_date, name, start_date, pharmacy_id) values (nextval('actions_seq'), 'Vitamni C,D,B na popustu 30%', '2021-02-20', 'Popust na pensionere', '2021-01-31', 1);
insert into pharmacy_action (id, description, end_date, name, start_date, pharmacy_id) values (nextval('actions_seq'), 'Svi gelovi za zglobove na popustu 40%', '2021-03-01', 'Februarski popust', '2021-02-01', 1);

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

insert into ordered_drug (id, quantity, drug_id) values (nextval('orderdrugs_seq'),33,1);
insert into ordered_drug (id, quantity, drug_id) values (nextval('orderdrugs_seq'),12,2);
insert into ordered_drug (id, quantity, drug_id) values (nextval('orderdrugs_seq'),45,1);

insert into pharmacy_order (id, limit_date, is_finished) values (nextval('orders_seq'), '2021-01-31', true);
insert into pharmacy_order (id, limit_date, is_finished) values (nextval('orders_seq'), '2021-03-03', false);

insert into pharmacy_order_ordered_drugs (pharmacy_order_id, ordered_drugs_id) values (1, 1);
insert into pharmacy_order_ordered_drugs (pharmacy_order_id, ordered_drugs_id) values (1, 2);
insert into pharmacy_order_ordered_drugs (pharmacy_order_id, ordered_drugs_id) values (2, 3);

insert into therapy (id, duration, drug_id,examination_id) values (nextval('therapies_seq'), 3, 1, 1);
insert into therapy (id, duration, drug_id,examination_id) values (nextval('therapies_seq'), 2, 3, 1);
insert into therapy (id, duration, drug_id,examination_id) values (nextval('therapies_seq'), 8, 4, 3);

insert into offer (id, is_accepted, limit_date, total_price, pharmacy_order_id) values (nextval('offers_seq'), false, '2021-02-15', 22000, 1);
insert into offer (id, is_accepted, limit_date, total_price, pharmacy_order_id) values (nextval('offers_seq'), false, '2021-02-10', 21000, 1);
insert into offer (id, is_accepted, limit_date, total_price, pharmacy_order_id) values (nextval('offers_seq'), false, '2021-02-22', 54000, 2);
insert into offer (id, is_accepted, limit_date, total_price, pharmacy_order_id) values (nextval('offers_seq'), false, '2021-02-17', 56000, 2);