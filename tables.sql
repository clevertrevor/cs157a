use RReservation;

-- delete existing tables
SET FOREIGN_KEY_CHECKS=0;
DROP TABLE IF EXISTS Restaurant;
DROP TABLE IF EXISTS Manager;
DROP TABLE IF EXISTS Reservation;
DROP TABLE IF EXISTS Customer;
DROP TABLE IF EXISTS ReservationArchive;
SET FOREIGN_KEY_CHECKS=1;

-- recreate tables

CREATE TABLE Customer(
	customer_id INTEGER NOT NULL AUTO_INCREMENT PRIMARY KEY,
    username VARCHAR(256),
    login_password VARCHAR(256),
    my_name VARCHAR(256),
    phone_number VARCHAR(20));

CREATE TABLE Restaurant(
	restaurant_id INTEGER NOT NULL AUTO_INCREMENT PRIMARY KEY,
    restaurant_name VARCHAR(256),
    capacity INTEGER);

CREATE TABLE Manager(
	manager_id INTEGER NOT NULL AUTO_INCREMENT PRIMARY KEY,
    my_name VARCHAR(256),
    username VARCHAR(256),
    login_password VARCHAR(256),
    restaurant_id INTEGER,
    FOREIGN KEY (restaurant_id) REFERENCES Restaurant(restaurant_id));

CREATE TABLE Reservation(
	reservation_id INTEGER NOT NULL AUTO_INCREMENT PRIMARY KEY,
    reservation_timestamp TIMESTAMP,
    reservation_duration TIME, 
    restaurant_id INTEGER,
    customer_id INTEGER,
    party_count INTEGER,
    FOREIGN KEY (restaurant_id) REFERENCES Restaurant(restaurant_id),
    FOREIGN KEY (customer_id) REFERENCES Customer(customer_id));

CREATE TABLE ReservationArchive( -- todo change this once archiving is figured out
	id SERIAL PRIMARY KEY, 
    reservation_timestamp TIMESTAMP, 
    reservation_duration TIME, 
    restaurant_id INTEGER, 
    customer_id INTEGER, 
    party_count INTEGER);
    
-- insert predefined tuples

-- Customer

INSERT INTO Customer (username, login_password, my_name, phone_number)
	VALUES("username1", "password1", "Customer1", "408-000-0001");
INSERT INTO Customer (username, login_password, my_name, phone_number)
	VALUES("username2", "password2", "Customer2", "408-000-0002");
INSERT INTO Customer (username, login_password, my_name, phone_number)
	VALUES("username3", "password3", "Customer3", "408-000-0003");
INSERT INTO Customer (username, login_password, my_name, phone_number)
	VALUES("username4", "password4", "Customer4", "408-000-0004");
    
