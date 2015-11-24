use RReservation;

CREATE TABLE Restaurant(id SERIAL PRIMARY KEY, name VARCHAR(256));

CREATE TABLE RRUser(id SERIAL PRIMARY KEY, restaurant_id INTEGER, is_manager BOOLEAN);

CREATE TABLE Reservation(id SERIAL PRIMARY KEY, reservation_timestamp TIMESTAMP, restaurant_id INTEGER, customer_id INTEGER, party_count INTEGER);

CREATE TABLE ReservationArchive(id SERIAL PRIMARY KEY, reservation_timestamp TIMESTAMP, restaurant_id INTEGER, customer_id INTEGER, party_count INTEGER);

CREATE TABLE Customer(id SERIAL PRIMARY KEY, reservation_id INTEGER, name VARCHAR(256), phone_number VARCHAR(10), birthdate DATE);
