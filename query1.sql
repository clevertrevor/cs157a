use RReservation;

CREATE TABLE Restaurant(id SERIAL PRIMARY KEY, name VARCHAR(256));

CREATE TABLE Manager(id SERIAL PRIMARY KEY, my_name VARCHAR(256), username VARCHAR(256), login_password VARCHAR(256), restaurant_id INTEGER);

CREATE TABLE Reservation(id SERIAL PRIMARY KEY, reservation_timestamp TIMESTAMP, restaurant_id INTEGER, customer_id INTEGER, party_count INTEGER);

CREATE TABLE ReservationArchive(id SERIAL PRIMARY KEY, reservation_timestamp TIMESTAMP, restaurant_id INTEGER, customer_id INTEGER, party_count INTEGER);

CREATE TABLE Customer(id SERIAL PRIMARY KEY, username VARCHAR(256),login_password VARCHAR(256), reservation_id INTEGER, my_name VARCHAR(256), phone_number VARCHAR(20));
