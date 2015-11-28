use RReservation;

DROP TABLE CUSTOMER;
DROP TABLE RESTAURANT;
DROP TABLE Manager;
DROP TABLE Reservation;
DROP TABLE ReservationArchive;

CREATE TABLE Customer(id SERIAL PRIMARY KEY, username VARCHAR(256), login_password VARCHAR(256), reservation_id INTEGER, my_name VARCHAR(256), phone_number VARCHAR(20));

CREATE TABLE Restaurant(id SERIAL PRIMARY KEY, name VARCHAR(256), capacity INTEGER);

CREATE TABLE Manager(id SERIAL PRIMARY KEY, my_name VARCHAR(256), username VARCHAR(256), login_password VARCHAR(256), restaurant_id INTEGER);

CREATE TABLE Reservation(id SERIAL PRIMARY KEY, reservation_timestamp TIMESTAMP, reservation_duration TIME, restaurant_id INTEGER, customer_id INTEGER, party_count INTEGER);

CREATE TABLE ReservationArchive(id SERIAL PRIMARY KEY, reservation_timestamp TIMESTAMP, reservation_duration TIME, restaurant_id INTEGER, customer_id INTEGER, party_count INTEGER);
