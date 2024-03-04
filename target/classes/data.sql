insert into users (enabled, first_name, last_name, phone_number, password, email, language, role, gender, doctor_gender_preference, patient_capacity, has_doctor, doctor_id, address, kilometer_range, ver_emails_sent, beta_key)
values
(TRUE, 'Lisa', 'Cuddy', '111', 'password0', 'doctor@example.com','FRENCH' , 'DOCTOR', 'FEMALE', 'NONE', 2, null, null,'101 Lyon St. N, Ottawa, ON K1R 5T9', null, 0, 'SALTOFTHEEARTH'),
(TRUE,'Lisa', 'House', '000', 'password0', 'cameron@housemd.com','English' , 'DOCTOR', 'FEMALE', 'NONE', 3, null, null,'101 Lyon St. N, Ottawa, ON K1R 5T9', null, 0, 'SALTOFTHEEARTH'),
(TRUE,'James', 'Wilson', '222','password1', 'HouseMD@example.com','BILINGUAL', 'DOCTOR', 'MALE', 'NONE', 3, null, null, '801 King Edward Ave, Ottawa, ON K1N 6N5', null, 0, 'SALTOFTHEEARTH'),
(TRUE,'Allison', 'Cameron', '4385204567','password1', 'coportus68@gmail.com','BILINGUAL', 'DOCTOR', 'FEMALE', 'NONE', 3, null, null,'25 Templeton St, Ottawa, ON K1N 6N5', null, 0, 'SALTOFTHEEARTH'),
(TRUE,'Eric', 'Foreman', '444', 'password1', 'foreman@housemd.com','ENGLISH', 'DOCTOR', 'MALE', 'NONE', 2, null, null, '1-65 Templeton St, Ottawa, ON K1N 7P7', null, 0, 'SALTOFTHEEARTH'),
(TRUE,'Patient', 'A', '001','password2', 'patientA@example.com', 'ENGLISH','PATIENT', 'FEMALE', 'NONE', null, false, null, '196 Somerset St. E, Ottawa, ON K1N 6V1', 1, 0, 'SALTOFTHEEARTH'),
(TRUE,'Patient', 'B', '002','password3', 'patientB@example.com', 'FRENCH','PATIENT', 'MALE', 'NONE',  null,false, null, '191 Somerset St. E, Ottawa, ON K1N 6V1', 2, 0, 'SALTOFTHEEARTH'),
(TRUE,'Patient', 'C', '003','password4', 'patientC@example.com', 'FRENCH', 'PATIENT', 'FEMALE', 'NONE',  null,false, null, '135 Henderson Ave, Ottawa, ON K1N 7P5', 3, 0, 'SALTOFTHEEARTH'),
(TRUE,'Patient', 'D', '004','password5', 'patientD@example.com', 'ENGLISH', 'PATIENT', 'MALE', 'FEMALE', null, false, null, '287 Somerset St. E A, Ottawa, ON K1N 6V7', 3, 0, 'SALTOFTHEEARTH'),
(TRUE,'Patient', 'E', '005','password6', 'patientE@example.com', 'ENGLISH', 'PATIENT', 'FEMALE', 'MALE',  null,false, null, '156 Osgoode St, Ottawa, ON K1N 6S3', 2, 0, 'SALTOFTHEEARTH'),
(TRUE,'Patient', 'F', '006','password7', 'patientF@example.com', 'BILINGUAL', 'PATIENT', 'MALE', 'NONE',  null,false, null, '250 Somerset St. E, Ottawa, ON K1N 6V6', 6, 0, 'SALTOFTHEEARTH'),
(TRUE,'Patient', 'G', '007','password8', 'patientG@example.com', 'FRENCH', 'PATIENT', 'FEMALE', 'MALE',  null,false, null, '283 Alexandre-Taché Blvd, Gatineau, Quebec J8X 3X7', 1.5, 0, 'SALTOFTHEEARTH'),
(TRUE,'Patient', 'H','008','password9', 'patientH@example.com', 'ENGLISH', 'PATIENT', 'MALE', 'NONE',  null,false, null, '1130 Boulevard Saint-Joseph, Gatineau, Quebec J8Z 1T3', 2, 0, 'SALTOFTHEEARTH'),
(TRUE,'Patient', 'I', '009','password10', 'patientI@example.com', 'BILINGUAL', 'PATIENT', 'FEMALE', 'MALE',  null,false, null, '214 Boulevard de la Cité-des-Jeunes, Gatineau, Quebec J8Y 6S8', 4, 0, 'SALTOFTHEEARTH'),
(TRUE,'Patient', 'J', '010','password11', 'patientJ@example.com', 'ENGLISH', 'PATIENT', 'MALE', 'FEMALE', null, false, null, '325 Marché Way Unit 107, Ottawa, ON K1S 5J3', 5, 0, 'SALTOFTHEEARTH'),
(TRUE,'Patient', 'K', '011','password12', 'patientK@example.com', 'FRENCH', 'PATIENT', 'FEMALE', 'NONE',  null,false, null, '70 Laurier Ave E, Ottawa, ON K1N 6N6', 3, 0, 'SALTOFTHEEARTH'),
(TRUE,'Patient', 'L', '012','password13', 'patientL@example.com', 'ENGLISH', 'PATIENT', 'MALE', 'MALE',  null,false, null, '550 Cumberland St, Ottawa, ON K1N 6N8', 3, 0, 'SALTOFTHEEARTH'),
(TRUE,'Patient', 'M', '013','password14', 'patientM@example.com', 'BILINGUAL', 'PATIENT', 'FEMALE', 'NONE',  null,false, null, '55 Laurier Ave E, Ottawa, ON K1N 6N5', 3, 0, 'SALTOFTHEEARTH'),
(TRUE,'Patient', 'N','014', 'password15', 'patientN@example.com', 'ENGLISH', 'PATIENT', 'MALE', 'FEMALE',  null,false, null, '50 Laurier Ave E, Ottawa, ON K1N 1H7', 2, 0, 'SALTOFTHEEARTH'),
(TRUE,'Patient', 'P', '016','password17', 'patientP@example.com', 'ENGLISH', 'PATIENT', 'MALE', 'NONE',  null,false, null, '174 Rue Wilbrod St, Ottawa, ON K1N 6N8', 1, 0, 'SALTOFTHEEARTH');


--INSERT INTO specializations (allergy_immunology, anesthesiology) VALUES (true, true);
--INSERT INTO specializations (neurology, urology) VALUES (true, true);