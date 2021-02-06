/*Lozinke za sve usere su 123*/

insert into AUTHORITY (id, name) VALUES (1, 'ROLE_PATIENT');
insert into AUTHORITY (id, name) VALUES (2, 'ROLE_DERMATOLOGIST');
insert into AUTHORITY (id, name) VALUES (3, 'ROLE_PHARMACIST');
insert into AUTHORITY (id, name) VALUES (4, 'ROLE_SYSTEMADMIN');
insert into AUTHORITY (id, name) VALUES (5, 'ROLE_SUPPLIER');
insert into AUTHORITY (id, name) VALUES (6, 'ROLE_PHARMACYADMIN');

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

insert into pharmacy (id, name, average_grade, city_id, address, latitude, longitude, pharmacist_price) values (nextval('pharmacies_seq'),'Janković', 4.5, 2, 'Lasla Gala 15', 45.246101, 19.837765, 1000);
insert into pharmacy (id, name, average_grade, city_id, address, latitude, longitude, pharmacist_price) values (nextval('pharmacies_seq'),'Betty', 5.0, 2, 'Danila Kiša 17', 45.247523, 19.836453, 900);
insert into pharmacy (id, name, average_grade, city_id, address, latitude, longitude, pharmacist_price) values (nextval('pharmacies_seq'),'Pharma',3.7, 2,'Gavrila Principa 8', 45.248707, 19.811935, 800);
insert into pharmacy (id, name, average_grade, city_id, address, latitude, longitude, pharmacist_price) values (nextval('pharmacies_seq'),'Crvena apoteka', 4.8, 1, 'Cara Lazara 15', 44.817567, 20.454853, 750);


insert into doctor (id, name, surname, telephone, average_grade, type_of_doctor,city_id,address,date_of_birth, is_deleted) 
					values (nextval('users_seq'),'Nada','Nadić', '065585230', 4.8, 0,1,'Tolstojeva 12','1970-12-12', false);
insert into user_account (authority_id, username, password, enabled, last_password_reset_date, user_id) VALUES (2, 'nada.nadic@gmail.com', '$2a$04$Vbug2lwwJGrvUXTj6z7ff.97IzVBkrJ1XfApfGNl.Z695zqcnPYra',  true, '2017-10-01 21:58:58.508-07', 1);

insert into doctor (id, name, surname, telephone, average_grade, type_of_doctor,city_id,address,date_of_birth, is_deleted)
					values (nextval('users_seq'),'Marija','Marić', '066598620', 3.9, 1,2,'Balzakova 23','1982-01-10', false);
insert into user_account (authority_id, username, password, enabled, last_password_reset_date, user_id) VALUES (3, 'marija.maric@gmail.com', '$2a$04$Vbug2lwwJGrvUXTj6z7ff.97IzVBkrJ1XfApfGNl.Z695zqcnPYra',  true, '2017-10-01 18:57:58.508-07', 2);

					   
insert into patient(id, name, surname, telephone, city_id, penalty, address, date_of_birth) 
					values (nextval('users_seq'),'Pera','Perić', '0668989985', 1,0,'Kralja Petra I','1963-07-13');
insert into patient(id, name, surname, telephone, city_id, penalty, address, date_of_birth) 
					values (nextval('users_seq'),'Ana','Anić', '0632145214', 1,1,'Maksima Gorkog 4','1957-03-05');
insert into patient(id, name, surname, telephone, city_id, penalty, address, date_of_birth) 
					values (nextval('users_seq'),'Lana','Ilić', '0632145555', 1,0,'Maksima Gorkog 15','1988-03-10');
insert into user_account (authority_id, username, password, enabled, last_password_reset_date, user_id) VALUES (1, 'pera.peric@gmail.com', '$2a$04$Vbug2lwwJGrvUXTj6z7ff.97IzVBkrJ1XfApfGNl.Z695zqcnPYra',  true, '2017-10-01 21:58:58.508-07', 3);
insert into user_account (authority_id, username, password, enabled, last_password_reset_date, user_id) VALUES (1, 'ana.anic@gmail.com', '$2a$04$Vbug2lwwJGrvUXTj6z7ff.97IzVBkrJ1XfApfGNl.Z695zqcnPYra',  true, '2017-10-01 18:57:58.508-07', 4);
insert into user_account (authority_id, username, password, enabled, last_password_reset_date, user_id) VALUES (1, 'lana.ilic@gmail.com', '$2a$04$Vbug2lwwJGrvUXTj6z7ff.97IzVBkrJ1XfApfGNl.Z695zqcnPYra',  true, '2017-10-01 21:58:58.508-07', 5);


insert into drug (id, code, name, type_of_drug, type_of_drugs_form, producer, daily_dose, contraindication, is_allowed_on_prescription) values (nextval('drugs_seq'), 'sifra1', 'Amoksicilin', 1, 1, 'Hemofarm', 3, 'Amoksicilin se ne smije primijeniti u slučaju preosjetljivosti na penicilin te u bolesnika s infektivnom mononukleozom i limfatičkom leukemijom zbog učestale pojave osipa.', false);
insert into drug (id, code, name, type_of_drug, type_of_drugs_form, producer, daily_dose, contraindication, is_allowed_on_prescription) values (nextval('drugs_seq'), 'sifra2', 'Cefaleksin', 1, 2, 'Hemofarm', 2, 'Cefaleksin se ne smije primjenjivati u osoba preosjetljivih na cefaleksin i druge cefalosporine, odnosno na neki od pomoćnih sastojaka lijeka.', false);
insert into drug (id, code, name, type_of_drug, type_of_drugs_form, producer, daily_dose, contraindication, is_allowed_on_prescription) values (nextval('drugs_seq'), 'sifra3', 'Brufen', 0, 0, 'Hemofarm', 3, 'Kontraindikacije za upotrebu lijeka Brufen su: teška insuficijencija jetre, stanja koja uključuju povećanu mogućnost krvarenja, teška insuficijencija bubrega.', true);
insert into drug (id, code, name, type_of_drug, type_of_drugs_form, producer, daily_dose, contraindication, is_allowed_on_prescription) values (nextval('drugs_seq'), 'sifra4', 'Probiotik Forte', 3, 3, 'Hemofarm', 3, 'Uzimanje probiotika povećava rizik od ozbiljnijih infekcija jer se nove bakterije ubacuju u organizam.', true);
insert into drug (id, code, name, type_of_drug, type_of_drugs_form, producer, daily_dose, contraindication, is_allowed_on_prescription) values (nextval('drugs_seq'), 'sifra5', 'Dexomen', 1, 1, 'Bosna lijek', 1, 'Uzimanje lijeka povećava rizik od bakterijskih infekcija.', true);
insert into drug (id, code, name, type_of_drug, type_of_drugs_form, producer, daily_dose, contraindication, is_allowed_on_prescription) values (nextval('drugs_seq'), 'sifra6', 'Panklav', 0, 0, 'Hemofarm', 3, 'Uzimanje antibiotika povećava rizik od ozbiljnijih infekcija jer se nove bakterije ubacuju u organizam.', true);
insert into drug (id, code, name, type_of_drug, type_of_drugs_form, producer, daily_dose, contraindication, is_allowed_on_prescription) values (nextval('drugs_seq'), 'sifra7', 'Paracetamol', 1, 1, 'Hemofarm', 2, 'Paracetamol ne smije primjenjivati kod bolesnika s anamnezom anafilaktičke (tj. po život opasne) reakcije na bilo koji sastojaka.', true);
insert into drug (id, code, name, type_of_drug, type_of_drugs_form, producer, daily_dose, contraindication, is_allowed_on_prescription) values (nextval('drugs_seq'), 'sifra8', 'Metalyse', 2, 1, 'Hemofarm', 3, 'Metalyse ne smije primjenjivati kod bolesnika s anamnezom anafilaktičke (tj. po život opasne) reakcije na bilo koji sastojaka.', true);
insert into drug (id, code, name, type_of_drug, type_of_drugs_form, producer, daily_dose, contraindication, is_allowed_on_prescription) values (nextval('drugs_seq'), 'sifra9', 'Hemomycin', 0, 0, 'Hemofarm', 3, 'Kontraindikacije za upotrebu lijeka Hemomycin su ošamućenost, glavobolja, trnjenje, poremećaj ukusa, oštećenje vida, gluvoća,povraćanje, otežano varenje, svrab, ospa, bol u zglobovima, malaksalost.', true);
insert into drug (id, code, name, type_of_drug, type_of_drugs_form, producer, daily_dose, contraindication, is_allowed_on_prescription) values (nextval('drugs_seq'), 'sifra10', 'Sabax', 1, 1, 'Bosna lijek', 3, 'Kontraindikacije za upotrebu lijeka Sabax su: groznica; nemir, vrtoglavica, konfuzija (zbunjenost), halucinacije, nesanica, pospanost (somnolencija), poremećaji vida; zapaljenje jezika (glositis).', true);
insert into drug (id, code, name, type_of_drug, type_of_drugs_form, producer, daily_dose, contraindication, is_allowed_on_prescription) values (nextval('drugs_seq'), 'sifra11', 'Ketonal', 1, 0, 'Bosna lijek', 2, 'Lijek Ketonal je kontraindikovan kod pacijenata sa anamnestičkim podacima o reakcijama preosetljivosti kao što su bronhospazam, astmatični napadi, rinitis, urtikarija ili drugim oblicima alergijskih reakcija.', true);
insert into drug (id, code, name, type_of_drug, type_of_drugs_form, producer, daily_dose, contraindication, is_allowed_on_prescription) values (nextval('drugs_seq'), 'sifra12', 'Tamsol', 1, 0, 'Bosna lijek', 2, 'Lijek Tamsol je kontraindikovan kod pacijenata sa anamnestičkim podacima o reakcijama preosetljivosti kao što su bronhospazam, astmatični napadi, rinitis, urtikarija ili drugim oblicima alergijskih reakcija.', true);

insert into drug_substitute_drugs (drug_id,substitute_drugs_id) values (1,3);
insert into drug_substitute_drugs (drug_id,substitute_drugs_id) values (2,1);
insert into drug_substitute_drugs (drug_id,substitute_drugs_id) values (3,9);
insert into drug_substitute_drugs (drug_id,substitute_drugs_id) values (4,10);
insert into drug_substitute_drugs (drug_id,substitute_drugs_id) values (5,10);
insert into drug_substitute_drugs (drug_id,substitute_drugs_id) values (6,10);

insert into ingredient (id, name) values (nextval('ingr_seq'), 'Azorubin');
insert into ingredient (id, name) values (nextval('ingr_seq'), 'Titan-dioksid');
insert into ingredient (id, name) values (nextval('ingr_seq'), 'Fiziološki rastvor');
insert into ingredient (id, name) values (nextval('ingr_seq'), 'Etanol');
insert into ingredient (id, name) values (nextval('ingr_seq'), 'Azitromicin');
insert into ingredient (id, name) values (nextval('ingr_seq'), 'Kopovidon');
insert into ingredient (id, name) values (nextval('ingr_seq'), 'Etilceluloza');
insert into ingredient (id, name) values (nextval('ingr_seq'), 'Silicijum-dioksid');
insert into ingredient (id, name) values (nextval('ingr_seq'), 'Magnezijum - stearat');
insert into ingredient (id, name) values (nextval('ingr_seq'), 'Povidon');
insert into ingredient (id, name) values (nextval('ingr_seq'), 'Celuloza');
insert into ingredient (id, name) values (nextval('ingr_seq'), 'Makrogol');
insert into ingredient (id, name) values (nextval('ingr_seq'), 'Indigotin');
insert into ingredient (id, name) values (nextval('ingr_seq'), 'Chinolin gelb');
insert into ingredient (id, name) values (nextval('ingr_seq'), 'Etilceluloza');

insert into drug_ingredients (drug_id, ingredients_id) values (1,1);
insert into drug_ingredients (drug_id, ingredients_id) values (1,2);
insert into drug_ingredients (drug_id, ingredients_id) values (1,3);
insert into drug_ingredients (drug_id, ingredients_id) values (1,4);
insert into drug_ingredients (drug_id, ingredients_id) values (2,6);
insert into drug_ingredients (drug_id, ingredients_id) values (2,8);
insert into drug_ingredients (drug_id, ingredients_id) values (2,1);
insert into drug_ingredients (drug_id, ingredients_id) values (2,4);
insert into drug_ingredients (drug_id, ingredients_id) values (3,6);
insert into drug_ingredients (drug_id, ingredients_id) values (3,3);
insert into drug_ingredients (drug_id, ingredients_id) values (3,1);
insert into drug_ingredients (drug_id, ingredients_id) values (3,11);
insert into drug_ingredients (drug_id, ingredients_id) values (4,12);
insert into drug_ingredients (drug_id, ingredients_id) values (4,1);
insert into drug_ingredients (drug_id, ingredients_id) values (4,13);
insert into drug_ingredients (drug_id, ingredients_id) values (5,14);
insert into drug_ingredients (drug_id, ingredients_id) values (5,9);
insert into drug_ingredients (drug_id, ingredients_id) values (5,8);
insert into drug_ingredients (drug_id, ingredients_id) values (5,10);
insert into drug_ingredients (drug_id, ingredients_id) values (5,6);
insert into drug_ingredients (drug_id, ingredients_id) values (6,8);
insert into drug_ingredients (drug_id, ingredients_id) values (6,10);
insert into drug_ingredients (drug_id, ingredients_id) values (6,2);
insert into drug_ingredients (drug_id, ingredients_id) values (7,6);
insert into drug_ingredients (drug_id, ingredients_id) values (7,5);
insert into drug_ingredients (drug_id, ingredients_id) values (7,9);
insert into drug_ingredients (drug_id, ingredients_id) values (7,2);
insert into drug_ingredients (drug_id, ingredients_id) values (8,6);
insert into drug_ingredients (drug_id, ingredients_id) values (8,8);
insert into drug_ingredients (drug_id, ingredients_id) values (8,10);
insert into drug_ingredients (drug_id, ingredients_id) values (8,4);
insert into drug_ingredients (drug_id, ingredients_id) values (9,6);
insert into drug_ingredients (drug_id, ingredients_id) values (9,8);
insert into drug_ingredients (drug_id, ingredients_id) values (9,10);
insert into drug_ingredients (drug_id, ingredients_id) values (10,7);
insert into drug_ingredients (drug_id, ingredients_id) values (10,4);
insert into drug_ingredients (drug_id, ingredients_id) values (11,4);
insert into drug_ingredients (drug_id, ingredients_id) values (11,1);
insert into drug_ingredients (drug_id, ingredients_id) values (12,5);
insert into drug_ingredients (drug_id, ingredients_id) values (12,14);

insert into pharmacy_administrator (id, name, surname, telephone, pharmacy_id,address,date_of_birth, city_id) values 
					   (nextval('users_seq'),'Miloš','Milošević', '0665656653',1,'Miloša Obilića 13','1973-05-10', 3);
insert into pharmacy_administrator (id, name, surname, telephone, pharmacy_id,address,date_of_birth, city_id) values 
					   (nextval('users_seq'),'Darko','Darković', '0632547854',2,'Zmaj Jovina 5','1980-05-08', 2);
insert into user_account (authority_id, username, password, enabled, last_password_reset_date, user_id) VALUES (6, 'milosm@gmail.com', '$2a$04$Vbug2lwwJGrvUXTj6z7ff.97IzVBkrJ1XfApfGNl.Z695zqcnPYra',  true, '2017-10-01 21:58:58.508-07', 6);
insert into user_account (authority_id, username, password, enabled, last_password_reset_date, user_id) VALUES (6, 'darkod@gmail.com', '$2a$04$Vbug2lwwJGrvUXTj6z7ff.97IzVBkrJ1XfApfGNl.Z695zqcnPYra',  true, '2017-10-01 18:57:58.508-07', 7);


insert into supplier (id, name, surname, telephone, address, date_of_birth, city_id) values 
					   (nextval('users_seq'),'Mitar','Mitrović', '0665458859','Braće Ribnikar 22','1995-11-11', 1);
insert into supplier (id, name, surname, telephone, address, date_of_birth, city_id) values 
					   (nextval('users_seq'),'Slavko','Ilić', '0632595258','Jevrejska 1','1994-12-12', 1);
insert into user_account (authority_id, username, password, enabled, last_password_reset_date, user_id) VALUES (5, 'mitarm@gmail.com', '$2a$04$Vbug2lwwJGrvUXTj6z7ff.97IzVBkrJ1XfApfGNl.Z695zqcnPYra',  true, '2017-10-01 21:58:58.508-07', 8);
insert into user_account (authority_id, username, password, enabled, last_password_reset_date, user_id) VALUES (5, 'slavkoi@gmail.com', '$2a$04$Vbug2lwwJGrvUXTj6z7ff.97IzVBkrJ1XfApfGNl.Z695zqcnPYra',  true, '2017-10-01 18:57:58.508-07', 9);
					   
					   
insert into pharmacy_action (id, description, end_date, name, start_date, pharmacy_id) values (nextval('actions_seq'), 'Vitamni C,D,B na popustu 30%', '2021-02-20', 'Popust na pensionere', '2021-01-31', 1);
insert into pharmacy_action (id, description, end_date, name, start_date, pharmacy_id) values (nextval('actions_seq'), 'Svi gelovi za zglobove na popustu 40%', '2021-03-01', 'Februarski popust', '2021-02-01', 1);


/*Slobodni (status = 0) termini kod dermatologa*/
insert into appointment (id,start_time,end_time,price,doctor_id,pharmacy_id,patient_id,status)
					values (nextval('appointments_seq'),'2021-02-23 09:30:00','2021-02-23 10:00:00',800,1,1,null,0);					
insert into appointment (id,start_time,end_time,price,doctor_id,pharmacy_id,patient_id,status)
					values (nextval('appointments_seq'),'2021-02-16 07:30:00','2021-02-16 8:00:00',1000,1,1,null,0);
insert into appointment (id,start_time,end_time,price,doctor_id,pharmacy_id,patient_id,status)
					values (nextval('appointments_seq'),'2021-02-16 07:30:00','2021-02-16 8:00:00',1000,1,2,null,0);
insert into appointment (id,start_time,end_time,price,doctor_id,pharmacy_id,patient_id,status)
					values (nextval('appointments_seq'),'2021-02-17 07:30:00','2021-02-17 8:00:00',1000,1,2,null,0);
insert into appointment (id,start_time,end_time,price,doctor_id,pharmacy_id,patient_id,status)
					values (nextval('appointments_seq'),'2021-02-13 07:30:00','2021-02-13 8:00:00',1000,1,1,null,0);
insert into appointment (id,start_time,end_time,price,doctor_id,pharmacy_id,patient_id,status)
					values (nextval('appointments_seq'),'2021-03-01 09:30:00','2021-03-01 10:00:00',1000,1,1,null,0);

/*Zakazani (status = 1) termini kod dermatologa*/
insert into appointment (id,start_time,end_time,price,doctor_id,pharmacy_id,patient_id,status)
					values (nextval('appointments_seq'),'2021-02-25 07:30:00','2021-02-25 8:00:00',1000,1,1,4,1);
insert into appointment (id,start_time,end_time,price,doctor_id,pharmacy_id,patient_id,status)
					values (nextval('appointments_seq'),'2021-02-10 07:30:00','2021-02-10 8:00:00',1000,1,1,3,1);
insert into appointment (id,start_time,end_time,price,doctor_id,pharmacy_id,patient_id,status)
					values (nextval('appointments_seq'),'2021-02-13 08:30:00','2021-02-13 9:00:00',1000,1,2,4,1);
insert into appointment (id,start_time,end_time,price,doctor_id,pharmacy_id,patient_id,status)
					values (nextval('appointments_seq'),'2021-02-28 12:00:00','2021-02-28 12:30:00',600,1,2,3,1);

					
/*Otkazani (status = 2) termini kod dermatologa*/
insert into appointment (id,start_time,end_time,price,doctor_id,pharmacy_id,patient_id,status)
					values (nextval('appointments_seq'),'2021-02-25 09:30:00','2021-02-25 10:00:00',1000,1,1,null,2);
insert into appointment (id,start_time,end_time,price,doctor_id,pharmacy_id,patient_id,status)
					values (nextval('appointments_seq'),'2021-02-10 13:30:00','2021-02-10 14:00:00',1000,1,1,null,2);
insert into appointment (id,start_time,end_time,price,doctor_id,pharmacy_id,patient_id,status)
					values (nextval('appointments_seq'),'2021-02-13 11:30:00','2021-02-13 12:00:00',1000,1,2,null,2);
					
/*Zavrseni (status = 3) termini kod dermatologa*/
insert into appointment (id,start_time,end_time,price,doctor_id,pharmacy_id,patient_id,status)
					values (nextval('appointments_seq'),'2021-02-01 09:30:00','2021-02-01 10:00:00',1000,1,1,4,3);
insert into appointment (id,start_time,end_time,price,doctor_id,pharmacy_id,patient_id,status)
					values (nextval('appointments_seq'),'2021-01-30 13:30:00','2021-01-30 14:00:00',1000,1,1,3,3);
insert into appointment (id,start_time,end_time,price,doctor_id,pharmacy_id,patient_id,status)
					values (nextval('appointments_seq'),'2021-02-02 11:30:00','2021-02-02 12:00:00',1000,1,2,4,3);
insert into appointment (id,start_time,end_time,price,doctor_id,pharmacy_id,patient_id,status)
					values (nextval('appointments_seq'),'2021-01-21 11:30:00','2021-01-21 12:00:00',1000,1,2,5,3);
					
/*Neobavljeni (status = 4) termini kod dermatologa*/
insert into appointment (id,start_time,end_time,price,doctor_id,pharmacy_id,patient_id,status)
					values (nextval('appointments_seq'),'2021-01-20 09:30:00','2021-01-20 10:00:00',1000,1,1,4,4);
insert into appointment (id,start_time,end_time,price,doctor_id,pharmacy_id,patient_id,status)
					values (nextval('appointments_seq'),'2021-02-03 13:30:00','2021-02-03 14:00:00',1000,1,1,3,4);

/*Zakazani (status = 1) termini kod farmaceuta*/		
insert into appointment (id,start_time,end_time,price,doctor_id,pharmacy_id,patient_id,status)
					values (nextval('appointments_seq'),'2021-02-23 09:30:00','2021-02-23 10:30:00',800,2,1,3,1);
insert into appointment (id,start_time,end_time,price,doctor_id,pharmacy_id,patient_id,status)
					values (nextval('appointments_seq'),'2021-02-19 15:30:00','2021-02-19 16:00:00',800,2,1,4,1);
insert into appointment (id,start_time,end_time,price,doctor_id,pharmacy_id,patient_id,status)
					values (nextval('appointments_seq'),'2021-02-03 11:30:00','2021-02-03 12:00:00',800,2,1,4,1);
insert into appointment (id,start_time,end_time,price,doctor_id,pharmacy_id,patient_id,status)
					values (nextval('appointments_seq'),'2021-03-08 09:30:00','2021-03-08 10:00:00',800,2,1,3,1);
insert into appointment (id,start_time,end_time,price,doctor_id,pharmacy_id,patient_id,status)
					values (nextval('appointments_seq'),'2021-02-28 09:30:00','2021-02-28 10:00:00',800,2,1,4,1);
insert into appointment (id,start_time,end_time,price,doctor_id,pharmacy_id,patient_id,status)
					values (nextval('appointments_seq'),'2021-03-03 09:30:00','2021-03-03 10:00:00',800,2,1,5,1);
					
/*Otkazani (status = 2) termini kod farmaceuta*/
insert into appointment (id,start_time,end_time,price,doctor_id,pharmacy_id,patient_id,status)
					values (nextval('appointments_seq'),'2021-02-24 09:30:00','2021-02-24 10:00:00',1000,2,1,null,2);
insert into appointment (id,start_time,end_time,price,doctor_id,pharmacy_id,patient_id,status)
					values (nextval('appointments_seq'),'2021-02-28 13:30:00','2021-02-28 14:00:00',1000,2,1,null,2);
insert into appointment (id,start_time,end_time,price,doctor_id,pharmacy_id,patient_id,status)
					values (nextval('appointments_seq'),'2021-02-17 11:30:00','2021-02-17 12:00:00',1000,2,1,null,2);
					
/*Zavrseni (status = 3) termini kod farmaceuta*/
insert into appointment (id,start_time,end_time,price,doctor_id,pharmacy_id,patient_id,status)
					values (nextval('appointments_seq'),'2021-01-25 09:30:00','2021-01-25 10:00:00',1000,2,1,4,3);
insert into appointment (id,start_time,end_time,price,doctor_id,pharmacy_id,patient_id,status)
					values (nextval('appointments_seq'),'2021-01-31 13:30:00','2021-01-31 14:00:00',1000,2,1,3,3);
insert into appointment (id,start_time,end_time,price,doctor_id,pharmacy_id,patient_id,status)
					values (nextval('appointments_seq'),'2021-02-01 11:30:00','2021-02-01 12:00:00',1000,2,2,4,3);
					
/*Neobavljeni (status = 4) termini kod farmaceuta*/
insert into appointment (id,start_time,end_time,price,doctor_id,pharmacy_id,patient_id,status)
					values (nextval('appointments_seq'),'2021-01-20 09:30:00','2021-01-20 10:00:00',1000,2,1,4,4);
insert into appointment (id,start_time,end_time,price,doctor_id,pharmacy_id,patient_id,status)
					values (nextval('appointments_seq'),'2021-02-03 13:30:00','2021-02-03 14:00:00',1000,2,1,3,4);

/*Izvjestaji pregleda kod dermatologa*/					
insert into examination_report (id,diagnosis,appointment_id) 
					values (nextval('examinations_seq'),'Rosea pytiriasis',14);
insert into examination_report (id,diagnosis,appointment_id) 
					values (nextval('examinations_seq'),'Acne vulgaris',15);
insert into examination_report (id,diagnosis,appointment_id) 
					values (nextval('examinations_seq'),'Hepes simplex',16);		
insert into examination_report (id,diagnosis,appointment_id) 
					values (nextval('examinations_seq'),'Dermatitis',17);	
					
/*Izvjestaji pregleda kod farmaceuta*/						
insert into examination_report (id,diagnosis,appointment_id) 
					values (nextval('examinations_seq'),'Popiti jos jednu dozu lijekova',29);
insert into examination_report (id,diagnosis,appointment_id) 
					values (nextval('examinations_seq'),'Terapiju trositi 10 dana',30);
insert into examination_report (id,diagnosis,appointment_id) 
					values (nextval('examinations_seq'),'Oboljelo mesto tretirati lekom 2 puta dnevno',31);
					
					
insert into patient_allergies (patient_id,allergies_id) values (3,1);
insert into patient_allergies (patient_id,allergies_id) values (4,2);


insert into drug_quantity_pharmacy (id, quantity, drug_id, pharmacy_id, is_deleted) values (nextval('quantity_pharmacy_seq'),33,1,1,false);
insert into drug_quantity_pharmacy (id, quantity, drug_id, pharmacy_id, is_deleted) values (nextval('quantity_pharmacy_seq'),12,2,1,false);
insert into drug_quantity_pharmacy (id, quantity, drug_id, pharmacy_id, is_deleted) values (nextval('quantity_pharmacy_seq'),45,3,1,false);
insert into drug_quantity_pharmacy (id, quantity, drug_id, pharmacy_id, is_deleted) values (nextval('quantity_pharmacy_seq'),65,4,1,false);
insert into drug_quantity_pharmacy (id, quantity, drug_id, pharmacy_id, is_deleted) values (nextval('quantity_pharmacy_seq'),66,5,1,false);
insert into drug_quantity_pharmacy (id, quantity, drug_id, pharmacy_id, is_deleted) values (nextval('quantity_pharmacy_seq'),85,6,1,false);
insert into drug_quantity_pharmacy (id, quantity, drug_id, pharmacy_id, is_deleted) values (nextval('quantity_pharmacy_seq'),15,7,1,false);
insert into drug_quantity_pharmacy (id, quantity, drug_id, pharmacy_id, is_deleted) values (nextval('quantity_pharmacy_seq'),5,8,1,false);
insert into drug_quantity_pharmacy (id, quantity, drug_id, pharmacy_id, is_deleted) values (nextval('quantity_pharmacy_seq'),33,1,2,false);
insert into drug_quantity_pharmacy (id, quantity, drug_id, pharmacy_id, is_deleted) values (nextval('quantity_pharmacy_seq'),12,4,2,false);
insert into drug_quantity_pharmacy (id, quantity, drug_id, pharmacy_id, is_deleted) values (nextval('quantity_pharmacy_seq'),66,11,2,false);
insert into drug_quantity_pharmacy (id, quantity, drug_id, pharmacy_id, is_deleted) values (nextval('quantity_pharmacy_seq'),45,10,2,false);
insert into drug_quantity_pharmacy (id, quantity, drug_id, pharmacy_id, is_deleted) values (nextval('quantity_pharmacy_seq'),99,9,2,false);
insert into drug_quantity_pharmacy (id, quantity, drug_id, pharmacy_id, is_deleted) values (nextval('quantity_pharmacy_seq'),17,6,3,false);
insert into drug_quantity_pharmacy (id, quantity, drug_id, pharmacy_id, is_deleted) values (nextval('quantity_pharmacy_seq'),18,7,3,false);
insert into drug_quantity_pharmacy (id, quantity, drug_id, pharmacy_id, is_deleted) values (nextval('quantity_pharmacy_seq'),51,10,3,false);
insert into drug_quantity_pharmacy (id, quantity, drug_id, pharmacy_id, is_deleted) values (nextval('quantity_pharmacy_seq'),33,1,3,false);
insert into drug_quantity_pharmacy (id, quantity, drug_id, pharmacy_id, is_deleted) values (nextval('quantity_pharmacy_seq'),12,2,4,false);
insert into drug_quantity_pharmacy (id, quantity, drug_id, pharmacy_id, is_deleted) values (nextval('quantity_pharmacy_seq'),3,10,4,false);
insert into drug_quantity_pharmacy (id, quantity, drug_id, pharmacy_id, is_deleted) values (nextval('quantity_pharmacy_seq'),33,11,4,false);
insert into drug_quantity_pharmacy (id, quantity, drug_id, pharmacy_id, is_deleted) values (nextval('quantity_pharmacy_seq'),44,12,4,false);

insert into pharmacy_order (id, limit_date, is_finished, pharmacy_administrator_id) values (nextval('orders_seq'), '2021-02-03', true, 6);
insert into pharmacy_order (id, limit_date, is_finished, pharmacy_administrator_id) values (nextval('orders_seq'), '2021-03-03', false, 6);

insert into drug_quantity_order (id, quantity, drug_id, pharmacy_order_id) values (nextval('drugquantities_seq'),33,1,1);
insert into drug_quantity_order (id, quantity, drug_id, pharmacy_order_id) values (nextval('drugquantities_seq'),12,2,1);
insert into drug_quantity_order (id, quantity, drug_id, pharmacy_order_id) values (nextval('drugquantities_seq'),45,1,2);


insert into therapy (id, duration, drug_id,examination_id) values (nextval('therapies_seq'), 3, 1, 1);
insert into therapy (id, duration, drug_id,examination_id) values (nextval('therapies_seq'), 2, 3, 2);
insert into therapy (id, duration, drug_id,examination_id) values (nextval('therapies_seq'), 7, 2, 2);
insert into therapy (id, duration, drug_id,examination_id) values (nextval('therapies_seq'), 3, 1, 3);
insert into therapy (id, duration, drug_id,examination_id) values (nextval('therapies_seq'), 10, 3, 4);
insert into therapy (id, duration, drug_id,examination_id) values (nextval('therapies_seq'), 5, 2, 5);
insert into therapy (id, duration, drug_id,examination_id) values (nextval('therapies_seq'), 6, 1, 6);

insert into drug_offer (id, limit_date, total_price, pharmacy_order_id, supplier_id, status) values (nextval('offers_seq'), '2021-02-22', 22000, 1, 8, 2);
insert into drug_offer (id, limit_date, total_price, pharmacy_order_id, supplier_id, status) values (nextval('offers_seq'), '2021-02-25', 21000, 1, 8, 2);
insert into drug_offer (id, limit_date, total_price, pharmacy_order_id, supplier_id, status) values (nextval('offers_seq'), '2021-03-22', 54000, 2, 8, 2);
insert into drug_offer (id, limit_date, total_price, pharmacy_order_id, supplier_id, status) values (nextval('offers_seq'), '2021-03-27', 56000, 2, 8, 2);

insert into pricelist (id, start_date, end_date, price, pharmacy_id, drug_id, creation_date) values (nextval('pricelists_seq'), '2021-01-01', '2021-01-31', 350, 1, 1, '2020-12-30');
insert into pricelist (id, start_date, end_date, price, pharmacy_id, drug_id, creation_date) values (nextval('pricelists_seq'), '2021-01-01', '2021-01-31', 380, 1, 1, '2021-01-15');
insert into pricelist (id, start_date, end_date, price, pharmacy_id, drug_id, creation_date) values (nextval('pricelists_seq'), '2021-02-01', '2021-02-28', 370, 1, 1, '2021-01-30');
insert into pricelist (id, start_date, end_date, price, pharmacy_id, drug_id, creation_date) values (nextval('pricelists_seq'), '2021-01-01', '2021-01-31', 340, 2, 1, '2020-12-30');
insert into pricelist (id, start_date, end_date, price, pharmacy_id, drug_id, creation_date) values (nextval('pricelists_seq'), '2021-01-01', '2021-01-31', 770, 1, 2, '2020-12-30');
insert into pricelist (id, start_date, end_date, price, pharmacy_id, drug_id, creation_date) values (nextval('pricelists_seq'), '2021-01-01', '2021-01-31', 1650, 1, 3, '2020-12-30');
insert into pricelist (id, start_date, end_date, price, pharmacy_id, drug_id, creation_date) values (nextval('pricelists_seq'), '2021-01-01', '2021-01-31', 150, 1, 4, '2020-12-30');

insert into drug_reservation (id,date_limit,is_done,patient_id,drug_id,pharmacy_id) 
			values (nextval('reservation_seq'),'2020-12-30 12:00:00',true,3,1,1);
insert into drug_reservation (id,date_limit,is_done,patient_id,drug_id,pharmacy_id) 
			values (nextval('reservation_seq'),'2021-02-20 15:00:00',false,4,2,1);
insert into drug_reservation (id,date_limit,is_done,patient_id,drug_id,pharmacy_id) 
			values (nextval('reservation_seq'),'2021-01-30 12:00:00',false,3,1,2);
insert into drug_reservation (id,date_limit,is_done,patient_id,drug_id,pharmacy_id) 
			values (nextval('reservation_seq'),'2020-01-04 12:00:00',true,3,2,1);
insert into drug_reservation (id,date_limit,is_done,patient_id,drug_id,pharmacy_id) 
			values (nextval('reservation_seq'),'2020-01-07 12:00:00',true,3,1,1);
insert into drug_reservation (id,date_limit,is_done,patient_id,drug_id,pharmacy_id) 
			values (nextval('reservation_seq'),'2020-01-16 12:00:00',true,3,1,1);
insert into drug_reservation (id,date_limit,is_done,patient_id,drug_id,pharmacy_id) 
			values (nextval('reservation_seq'),'2020-01-31 12:00:00',true,3,1,1);
insert into drug_reservation (id,date_limit,is_done,patient_id,drug_id,pharmacy_id) 
			values (nextval('reservation_seq'),'2020-02-02 12:00:00',true,3,1,1);
insert into drug_reservation (id,date_limit,is_done,patient_id,drug_id,pharmacy_id) 
			values (nextval('reservation_seq'),'2020-02-04 12:00:00',true,3,1,1);

insert into working_time (id,start_time,end_time,doctor_id,pharmacy_id) values (nextval('work_time_seq'),'08:00:00','12:00:00',1,1);
insert into working_time (id,start_time,end_time,doctor_id,pharmacy_id) values (nextval('work_time_seq'),'12:00:00','16:00:00',1,2);
insert into working_time (id,start_time,end_time,doctor_id,pharmacy_id) values (nextval('work_time_seq'),'08:00:00','16:00:00',2,1);

insert into vacation_request(id,start_date,end_date,status,reason_for_rejection,doctor_id,pharmacy_id)
				values (nextval('vacation_seq'),'2021-07-01','2021-07-31',0,null,1,1);
insert into vacation_request(id,start_date,end_date,status,reason_for_rejection,doctor_id,pharmacy_id)
				values (nextval('vacation_seq'),'2021-01-25','2021-02-25',1,'Nemamo dovoljno radnog kapaciteta.',1,2);
insert into vacation_request(id,start_date,end_date,status,reason_for_rejection,doctor_id,pharmacy_id)
				values (nextval('vacation_seq'),'2021-03-01','2021-03-10',1,'U tom periodu imate zakazane termine.',2,1);
				
insert into doctor_pharmacies(pharmacies_id, doctor_id) values (1,1);
insert into doctor_pharmacies(pharmacies_id, doctor_id) values (2,1);
insert into doctor_pharmacies(pharmacies_id, doctor_id) values (1,2);


insert into system_administrator (id, name, surname, telephone,address,date_of_birth) values 
					   (nextval('users_seq'),'Mladen','Mladenović', '0665677653','Miloša Obilića 55','1978-09-10');
insert into system_administrator (id, name, surname, telephone,address,date_of_birth) values 
					   (nextval('users_seq'),'Nikola','Nikolić', '0632547777','Zmaj Jovina 12','1985-05-10');
insert into user_account (authority_id, username, password, enabled, last_password_reset_date, user_id) VALUES (4, 'mladenm@gmail.com', '$2a$04$Vbug2lwwJGrvUXTj6z7ff.97IzVBkrJ1XfApfGNl.Z695zqcnPYra',  true, '2017-10-01 21:58:58.508-07', 10);
insert into user_account (authority_id, username, password, enabled, last_password_reset_date, user_id) VALUES (4, 'nikolan@gmail.com', '$2a$04$Vbug2lwwJGrvUXTj6z7ff.97IzVBkrJ1XfApfGNl.Z695zqcnPYra',  true, '2017-10-01 18:57:58.508-07', 11);

					   

insert into subscription (id, is_canceled, patient_id, pharmacy_id) values (nextval('subscriptions_seq'), true, 4, 1);
insert into subscription (id, is_canceled, patient_id, pharmacy_id) values (nextval('subscriptions_seq'), false, 4, 2);

insert into user_category (id, name, discount, upper_limit, lower_limit) values (nextval('category_seq'), 'gold', 10, 100, 51);
insert into user_category (id, name, discount, upper_limit, lower_limit) values (nextval('category_seq'), 'silver', 5, 50, 21);

insert into drug_quantity_supplier (id, quantity, drug_id, supplier_id) values (nextval('quantity_supplier_seq'), 33, 1, 8);
insert into drug_quantity_supplier (id, quantity, drug_id, supplier_id) values (nextval('quantity_supplier_seq'), 12, 2, 8);
insert into drug_quantity_supplier (id, quantity, drug_id, supplier_id) values (nextval('quantity_supplier_seq'), 45, 1, 8);


insert into patient(id, name, surname, telephone, penalty, address, date_of_birth) 
					values (nextval('users_seq'),'Sima','Simić', '0668989985', 0,'Kralja Petra I','1963-07-13');
insert into patient(id, name, surname, telephone, penalty, address, date_of_birth) 
				    values (nextval('users_seq'),'Mika','Mikić', '0668989985', 0,'Kralja Petra I','1963-07-13');

insert into user_account (authority_id, username, password, enabled, last_password_reset_date, user_id) VALUES (1, 'user@example.com', '$2a$04$Vbug2lwwJGrvUXTj6z7ff.97IzVBkrJ1XfApfGNl.Z695zqcnPYra',  true, '2017-10-01 21:58:58.508-07', 12);
insert into user_account (authority_id, username, password, enabled, last_password_reset_date, user_id) VALUES (1, 'mika.mikic@gmail.com', '$2a$04$Vbug2lwwJGrvUXTj6z7ff.97IzVBkrJ1XfApfGNl.Z695zqcnPYra',  true, '2017-10-01 18:57:58.508-07', 13);


insert into notification (id, drug_id, pharmacy_id, creation_date) values (nextval('notification_seq'), 1, 1, '2021-01-25 15:00:00');
insert into notification (id, drug_id, pharmacy_id, creation_date) values (nextval('notification_seq'), 3, 1, '2021-02-01 10:00:00');
insert into notification (id, drug_id, pharmacy_id, creation_date) values (nextval('notification_seq'), 2, 2, '2021-01-30 12:00:00');


/*Ubacivanje dodatnog farmaceuta i definisanje zavrsenog pregleda*/
insert into doctor (id, name, surname, telephone, average_grade, type_of_doctor,city_id,address,date_of_birth, is_deleted)
					values (nextval('users_seq'),'Petar','Petrić', '063752014', 4.2, 1, 2,'Šekspirova 40','1978-10-13', false);
insert into user_account (authority_id, username, password, enabled, last_password_reset_date, user_id) VALUES (3, 'petar.petric@gmail.com', '$2a$04$Vbug2lwwJGrvUXTj6z7ff.97IzVBkrJ1XfApfGNl.Z695zqcnPYra',  true, '2017-10-01 18:57:58.508-07', 14);
insert into doctor_pharmacies(pharmacies_id, doctor_id) values (1,14);
insert into appointment (id,start_time,end_time,price,doctor_id,pharmacy_id,patient_id,status)
					values (nextval('appointments_seq'),'2021-03-01 07:30:00','2021-03-01 08:00:00',600,14,1,5,3);
insert into examination_report (id,diagnosis,appointment_id) 
					values (nextval('examinations_seq'),'Redovno uzimati terapiju sledecih 10 dana',34);
insert into therapy (id, duration, drug_id,examination_id) values (nextval('therapies_seq'), 10, 2, 8);
					
/*Definisem zakazan pregled kod prvog farmaceuta za pacijenta kojeg nije nikada pregledao*/
insert into appointment (id,start_time,end_time,price,doctor_id,pharmacy_id,patient_id,status)
					values (nextval('appointments_seq'),'2021-03-05 07:30:00','2021-03-05 08:00:00',600,2,1,5,1);
