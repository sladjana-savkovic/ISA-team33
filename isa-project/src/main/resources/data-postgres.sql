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

insert into pharmacy (id, name, average_grade, city_id, address) values (nextval('pharmacies_seq'),'Janković', 4.5, 1, 'Lasla Gala 89');
insert into pharmacy (id, name, average_grade, city_id, address) values (nextval('pharmacies_seq'),'Pharma', 5.0, 2, 'Danila Kiša 7');
insert into pharmacy (id, name, average_grade, city_id, address) values (nextval('pharmacies_seq'),'Betty',3.7, 2,'Gavrila Principa 8' );
insert into pharmacy (id, name, average_grade, city_id, address) values (nextval('pharmacies_seq'),'Crvena apoteka', 4.8, 3, 'Cara Lazara 23');


insert into doctor (id, name, surname, email, password, telephone, average_grade, type_of_doctor,city_id,address,date_of_birth, is_active, is_deleted) 
					values (nextval('users_seq'),'Nada','Nadić','nada.nadic@gmail.com','nada1234', '0652323323', 4.8, 0,1,'Tolstojeva 12','1970-12-12', true, false);
insert into doctor (id, name, surname, email, password, telephone, average_grade, type_of_doctor,city_id,address,date_of_birth, is_active, is_deleted)
					values (nextval('users_seq'),'Marija','Marić','marija.maric@gmail.com','marija1234', '0665859985', 3.9, 1,2,'Balzakova 23','1982-01-10', true, false);

					   
insert into patient(id, name, surname, email, password, telephone, city_id, penalty, address, date_of_birth, is_active) 
					values (nextval('users_seq'),'Pera','Perić','pera.peric@gmail.com','pera1234', '0668989985', 1,0,'Kralja Petra I','1963-07-13', true);
insert into patient(id, name, surname, email, password, telephone, city_id, penalty, address, date_of_birth, is_active) 
					values (nextval('users_seq'),'Ana','Anić','ana.anic@gmail.com','ana1234', '0632145214', 1,1,'Maksima Gorkog 4','1957-03-05', true);
insert into patient(id, name, surname, email, password, telephone, city_id, penalty, address, date_of_birth, is_active) 
					values (nextval('users_seq'),'Lana','Ilić','lana.ilic@gmail.com','kkk3434', '0632145555', 1,0,'Maksima Gorkog 15','1988-03-10', false);					

insert into drug (id, name, type_of_drug, type_of_drugs_form, producer, daily_dose, contraindication, is_allowed_on_prescription) values (nextval('drugs_seq'), 'Amoksicilin', 1, 1, 'Hemofarm', 3, 'Amoksicilin se ne smije primijeniti u slučaju preosjetljivosti na penicilin te u bolesnika s infektivnom mononukleozom i limfatičkom leukemijom zbog učestale pojave osipa.', false);
insert into drug (id, name, type_of_drug, type_of_drugs_form, producer, daily_dose, contraindication, is_allowed_on_prescription) values (nextval('drugs_seq'), 'Cefaleksin', 1, 2, 'Hemofarm', 2, 'Cefaleksin se ne smije primjenjivati u osoba preosjetljivih na cefaleksin i druge cefalosporine, odnosno na neki od pomoćnih sastojaka lijeka.', false);
insert into drug (id, name, type_of_drug, type_of_drugs_form, producer, daily_dose, contraindication, is_allowed_on_prescription) values (nextval('drugs_seq'), 'Brufen', 0, 0, 'Hemofarm', 3, 'Kontraindikacije za upotrebu leka Brufen su: teška insuficijencija jetre, stanja koja uključuju povećanu mogućnost krvarenja, teška insuficijencija bubrega.', true);
insert into drug (id, name, type_of_drug, type_of_drugs_form, producer, daily_dose, contraindication, is_allowed_on_prescription) values (nextval('drugs_seq'), 'Probiotik Forte', 3, 3, 'Hemofarm', 3, 'Uzimanje probiotika povećava rizik od ozbiljnijih infekcija jer se nove bakterije ubacuju u organizam.', true);

insert into drug_substitute_drugs (drug_id,substitute_drugs_id) values (1,3);


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
					

insert into pharmacy_administrator (id, name, surname, email, password, telephone, pharmacy_id,address,date_of_birth, is_active, city_id) values 
					   (nextval('users_seq'),'Miloš','Milošević','milosm@gmail.com','milos1234', '0665656653',1,'Miloša Obilića 13','1973-05-10', true, 3);
insert into pharmacy_administrator (id, name, surname, email, password, telephone, pharmacy_id,address,date_of_birth, is_active, city_id) values 
					   (nextval('users_seq'),'Darko','Darković','darkod@gmail.com','darko1234', '0632547854',2,'Zmaj Jovina 5','1980-05-08', true, 2);
					   
insert into supplier (id, name, surname, email, password, telephone,address,date_of_birth, is_active) values 
					   (nextval('users_seq'),'Mitar','Mitrović','mitarm@gmail.com','mitar1234', '0665858859','Braće Ribnikar 12','1995-11-11', true);
insert into supplier (id, name, surname, email, password, telephone,address,date_of_birth, is_active) values 
					   (nextval('users_seq'),'Slavko','Ilić','slavkoi@gmail.com','mitar1234', '0632585258','Jevrejska 10','1994-12-12', false);
					   
					   
insert into pharmacy_action (id, description, end_date, name, start_date, pharmacy_id) values (nextval('actions_seq'), 'Vitamni C,D,B na popustu 30%', '2021-02-20', 'Popust na pensionere', '2021-01-31', 1);
insert into pharmacy_action (id, description, end_date, name, start_date, pharmacy_id) values (nextval('actions_seq'), 'Svi gelovi za zglobove na popustu 40%', '2021-03-01', 'Februarski popust', '2021-02-01', 1);


insert into appointment (id,start_time,end_time,type_of_appointment,price,doctor_id,pharmacy_id,patient_id,status)
					values (nextval('appointments_seq'),'2020-12-30 12:00:00','2020-12-30 12:30:00',0,600,1,1,3,3);
insert into appointment (id,start_time,end_time,type_of_appointment,price,doctor_id,pharmacy_id,patient_id,status)
					values (nextval('appointments_seq'),'2021-01-13 07:30:00','2021-01-13 08:00:00',0,600,1,2,4,3);
insert into appointment (id,start_time,end_time,type_of_appointment,price,doctor_id,pharmacy_id,patient_id,status)
					values (nextval('appointments_seq'),'2020-11-16 13:00:00','2020-11-16 13:30:00',1,800,2,1,3,3);
insert into appointment (id,start_time,end_time,type_of_appointment,price,doctor_id,pharmacy_id,patient_id,status)
					values (nextval('appointments_seq'),'2020-12-03 15:30:00','2020-12-03 16:00:00',1,800,2,3,4,3);
insert into appointment (id,start_time,end_time,type_of_appointment,price,doctor_id,pharmacy_id,patient_id,status)
					values (nextval('appointments_seq'),'2021-02-03 09:30:00','2021-01-13 10:00:00',0,800,1,1,4,0);
insert into appointment (id,start_time,end_time,type_of_appointment,price,doctor_id,pharmacy_id,patient_id,status)
					values (nextval('appointments_seq'),'2020-12-03 07:30:00','2020-12-03 8:00:00',0,1000,1,2,3,0);
insert into appointment (id,start_time,end_time,type_of_appointment,price,doctor_id,pharmacy_id,patient_id,status)
					values (nextval('appointments_seq'),'2021-02-03 11:30:00','2021-01-13 12:00:00',0,800,1,2,4,4);
insert into appointment (id,start_time,end_time,type_of_appointment,price,doctor_id,pharmacy_id,patient_id,status)
					values (nextval('appointments_seq'),'2021-02-20 09:30:00','2021-02-20 10:00:00',0,800,1,2,3,1);
insert into appointment (id,start_time,end_time,type_of_appointment,price,doctor_id,pharmacy_id,patient_id,status)
					values (nextval('appointments_seq'),'2021-01-05 09:30:00','2020-01-05 10:00:00',0,800,1,2,4,2);
				

insert into examination_report (id,diagnosis,appointment_id) 
					values (nextval('examinations_seq'),'Upala pluća',1);
insert into examination_report (id,diagnosis,appointment_id) 
					values (nextval('examinations_seq'),'COVID-19',2);
insert into examination_report (id,diagnosis,appointment_id) 
					values (nextval('examinations_seq'),'Popiti jos jednu dozu lijekova',3);
insert into examination_report (id,diagnosis,appointment_id) 
					values (nextval('examinations_seq'),'Redovno trošiti terapiju',4);
					
insert into patient_allergies (patient_id,allergies_id) values (3,1);
insert into patient_allergies (patient_id,allergies_id) values (4,2);


insert into drug_quantity_pharmacy (id, quantity, drug_id, pharmacy_id) values (nextval('quantity_pharmacy_seq'),33,1,1);
insert into drug_quantity_pharmacy (id, quantity, drug_id, pharmacy_id) values (nextval('quantity_pharmacy_seq'),12,2,2);
insert into drug_quantity_pharmacy (id, quantity, drug_id, pharmacy_id) values (nextval('quantity_pharmacy_seq'),45,1,2);

insert into pharmacy_order (id, limit_date, is_finished, pharmacy_administrator_id) values (nextval('orders_seq'), '2021-01-31', true, 6);
insert into pharmacy_order (id, limit_date, is_finished, pharmacy_administrator_id) values (nextval('orders_seq'), '2021-03-03', false, 6);


insert into drug_quantity_order (id, quantity, drug_id, pharmacy_order_id) values (nextval('drugquantities_seq'),33,1,1);
insert into drug_quantity_order (id, quantity, drug_id, pharmacy_order_id) values (nextval('drugquantities_seq'),12,2,1);
insert into drug_quantity_order (id, quantity, drug_id, pharmacy_order_id) values (nextval('drugquantities_seq'),45,1,2);


insert into therapy (id, duration, drug_id,examination_id) values (nextval('therapies_seq'), 3, 1, 1);
insert into therapy (id, duration, drug_id,examination_id) values (nextval('therapies_seq'), 2, 3, 1);
insert into therapy (id, duration, drug_id,examination_id) values (nextval('therapies_seq'), 8, 4, 3);

insert into drug_offer (id, limit_date, total_price, pharmacy_order_id, supplier_id, status) values (nextval('offers_seq'), '2021-02-15', 22000, 1, 8, 2);
insert into drug_offer (id, limit_date, total_price, pharmacy_order_id, supplier_id, status) values (nextval('offers_seq'), '2021-02-10', 21000, 1, 8, 2);
insert into drug_offer (id, limit_date, total_price, pharmacy_order_id, supplier_id, status) values (nextval('offers_seq'), '2021-02-22', 54000, 2, 8, 2);
insert into drug_offer (id, limit_date, total_price, pharmacy_order_id, supplier_id, status) values (nextval('offers_seq'), '2021-02-17', 56000, 2, 8, 2);

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

insert into system_administrator (id, name, surname, email, password, telephone,address,date_of_birth, is_active) values 
					   (nextval('users_seq'),'Mladen','Mladenović','mladenm@gmail.com','mladen1534', '0665677653','Miloša Obilića 55','1978-09-10', true);
insert into system_administrator (id, name, surname, email, password, telephone,address,date_of_birth, is_active) values 
					   (nextval('users_seq'),'Nikola','Nikolić','nikolan@gmail.com','1234nikola', '0632547777','Zmaj Jovina 12','1985-05-10', true);
					   

insert into subscription (id, is_canceled, patient_id, pharmacy_action_id) values (nextval('subscriptions_seq'), true, 4, 1);
insert into subscription (id, is_canceled, patient_id, pharmacy_action_id) values (nextval('subscriptions_seq'), false, 4, 2);

insert into user_category (id, name, discount, upper_limit, lower_limit) values (nextval('category_seq'), 'gold', 10, 100, 51);
insert into user_category (id, name, discount, upper_limit, lower_limit) values (nextval('category_seq'), 'silver', 5, 50, 21);

insert into drug_quantity_supplier (id, quantity, drug_id, supplier_id) values (nextval('quantity_supplier_seq'), 33, 1, 8);
insert into drug_quantity_supplier (id, quantity, drug_id, supplier_id) values (nextval('quantity_supplier_seq'), 12, 2, 8);
insert into drug_quantity_supplier (id, quantity, drug_id, supplier_id) values (nextval('quantity_supplier_seq'), 45, 1, 8);

					   