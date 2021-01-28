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

insert into doctor (id, name, surname, email, password, average_price, type_of_doctor,city_id) 
					values (nextval('users_seq'),'Nada','Nadić','nada.nadic@gmail.com','nada1234', 4.8, 0,1);
insert into doctor (id, name, surname, email, password, average_price, type_of_doctor,city_id) 
					values (nextval('users_seq'),'Marija','Marić','marija.maric@gmail.com','marija1234',3.9, 1,2);
					   
insert into patient(id, name, surname, email, password,city_id,penalty) 
					values (nextval('users_seq'),'Pera','Perić','pera.peric@gmail.com','pera1234',1,0);
insert into patient(id, name, surname, email, password,city_id,penalty) 
					values (nextval('users_seq'),'Ana','Anić','ana.anic@gmail.com','ana1234',1,1);

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
					
insert into pharmacy_administrator (id, name, surname, email, password, pharmacy_id) values 
					   (nextval('users_seq'),'Miloš','Milošević','milosm@gmail.com','milos1234',1);
insert into pharmacy_administrator (id, name, surname, email, password, pharmacy_id) values 
					   (nextval('users_seq'),'Darko','Darković','darkod@gmail.com','darko1234',2);

insert into pharmacy_action (id, description, end_date, name, start_date, pharmacy_id) values (nextval('actions_seq'), 'Vitamni C,D,B na popustu 30%', '2021-02-20', 'Popust na pensionere', '2021-01-31', 1);
insert into pharmacy_action (id, description, end_date, name, start_date, pharmacy_id) values (nextval('actions_seq'), 'Svi gelovi za zglobove na popustu 40%', '2021-03-01', 'Februarski popust', '2021-02-01', 1);


insert into appointment (id,start_time,end_time,type_of_appointment,price,doctor_id,pharmacy_id,patient_id)
					values (nextval('appointments_seq'),'2020-12-30 12:00:00','2020-12-30 12:30:00',0,600,1,1,3);
insert into appointment (id,start_time,end_time,type_of_appointment,price,doctor_id,pharmacy_id,patient_id)
					values (nextval('appointments_seq'),'2021-01-13 07:30:00','2021-01-13 08:00:00',0,600,1,2,4);
insert into appointment (id,start_time,end_time,type_of_appointment,price,doctor_id,pharmacy_id,patient_id)
					values (nextval('appointments_seq'),'2020-11-16 13:00:00','2020-11-16 13:30:00',1,800,2,1,3);
insert into appointment (id,start_time,end_time,type_of_appointment,price,doctor_id,pharmacy_id,patient_id)
					values (nextval('appointments_seq'),'2020-12-03 15:30:00','2020-12-03 16:00:00',1,800,2,3,4);

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

insert into drug_quantity (id, quantity, drug_id,purpose) values (nextval('drugquantities_seq'),33,1,0);
insert into drug_quantity (id, quantity, drug_id,purpose) values (nextval('drugquantities_seq'),12,2,0);
insert into drug_quantity (id, quantity, drug_id,purpose) values (nextval('drugquantities_seq'),45,1,1);

insert into pharmacy_order (id, limit_date, is_finished) values (nextval('orders_seq'), '2021-01-31', true);
insert into pharmacy_order (id, limit_date, is_finished) values (nextval('orders_seq'), '2021-03-03', false);

insert into pharmacy_order_ordered_drugs (pharmacy_order_id, ordered_drugs_id) values (1, 1);
insert into pharmacy_order_ordered_drugs (pharmacy_order_id, ordered_drugs_id) values (1, 2);
insert into pharmacy_order_ordered_drugs (pharmacy_order_id, ordered_drugs_id) values (2, 3);

insert into therapy (id, duration, drug_id,examination_id) values (nextval('therapies_seq'), 3, 1, 1);
insert into therapy (id, duration, drug_id,examination_id) values (nextval('therapies_seq'), 2, 3, 1);
insert into therapy (id, duration, drug_id,examination_id) values (nextval('therapies_seq'), 8, 4, 3);

insert into drug_offer (id, is_accepted, limit_date, total_price, pharmacy_order_id) values (nextval('offers_seq'), false, '2021-02-15', 22000, 1);
insert into drug_offer (id, is_accepted, limit_date, total_price, pharmacy_order_id) values (nextval('offers_seq'), false, '2021-02-10', 21000, 1);
insert into drug_offer (id, is_accepted, limit_date, total_price, pharmacy_order_id) values (nextval('offers_seq'), false, '2021-02-22', 54000, 2);
insert into drug_offer (id, is_accepted, limit_date, total_price, pharmacy_order_id) values (nextval('offers_seq'), false, '2021-02-17', 56000, 2);

insert into pricelist (id, start_date, end_date, price, pharmacy_id, drug_id) values (nextval('pricelists_seq'), '2021-01-01', '2021-01-31', 350, 1, 1);
insert into pricelist (id, start_date, end_date, price, pharmacy_id, drug_id) values (nextval('pricelists_seq'), '2021-02-01', '2021-02-28', 370, 1, 1);
insert into pricelist (id, start_date, end_date, price, pharmacy_id, drug_id) values (nextval('pricelists_seq'), '2021-01-01', '2021-01-31', 340, 2, 1);
insert into pricelist (id, start_date, end_date, price, pharmacy_id, drug_id) values (nextval('pricelists_seq'), '2021-01-01', '2021-01-31', 770, 1, 2);
insert into pricelist (id, start_date, end_date, price, pharmacy_id, drug_id) values (nextval('pricelists_seq'), '2021-01-01', '2021-01-31', 1650, 1, 3);
insert into pricelist (id, start_date, end_date, price, pharmacy_id, drug_id) values (nextval('pricelists_seq'), '2021-01-01', '2021-01-31', 150, 1, 4);

insert into drug_reservation (id,date_limit,is_done,patient_id) values (nextval('reservation_seq'),'2020-12-30 12:00:00',true,3);
insert into drug_reservation (id,date_limit,is_done,patient_id) values (nextval('reservation_seq'),'2021-01-03 15:00:00',true,4);
insert into drug_reservation (id,date_limit,is_done,patient_id) values (nextval('reservation_seq'),'2021-01-30 12:00:00',false,3);

insert into working_time (id,start_time,end_time,doctor_id,pharmacy_id) values (nextval('work_time_seq'),'08:00:00','12:00:00',1,1);
insert into working_time (id,start_time,end_time,doctor_id,pharmacy_id) values (nextval('work_time_seq'),'12:00:00','16:00:00',1,2);
insert into working_time (id,start_time,end_time,doctor_id,pharmacy_id) values (nextval('work_time_seq'),'08:00:00','16:00:00',2,1);

insert into vacation_request(id,start_date,end_date,status,reason_for_rejection,doctor_id)
				values (nextval('vacation_seq'),'2021-07-01','2021-07-31',0,null,1);
insert into vacation_request(id,start_date,end_date,status,reason_for_rejection,doctor_id)
				values (nextval('vacation_seq'),'2021-01-25','2021-02-25',1,'Nemamo dovoljno radnog kapaciteta.',2);
insert into vacation_request(id,start_date,end_date,status,reason_for_rejection,doctor_id)
				values (nextval('vacation_seq'),'2021-03-01','2021-03-10',1,'U tom periodu imate zakazane termine.',2);
				
insert into pharmacy_doctors(pharmacy_id, doctors_id) values (1,1);
insert into pharmacy_doctors(pharmacy_id, doctors_id) values (2,1);
insert into pharmacy_doctors(pharmacy_id, doctors_id) values (1,2);
