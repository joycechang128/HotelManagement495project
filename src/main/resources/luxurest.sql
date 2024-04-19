CREATE DATABASE LUXUREST;

USE LUXUREST;

CREATE TABLE customer (
    CustomerID INT AUTO_INCREMENT PRIMARY KEY,
    CustomerName VARCHAR(255),
    Email VARCHAR(255),
    PhoneNumber VARCHAR(255)
);

INSERT INTO customer (CustomerName, Email, PhoneNumber)
VALUES 
('Abram Hendricks', 'abram@gmail.com', '234-567-8901'),
('Jovanni Wade', 'jovanni@gmail.com', '890-123-4567'),
('Ruben McDaniel', 'ruben@gmail.com', '456-789-0123'),
('Elliott Higgins', 'elliott@gmail.com', '789-012-3456'),
('Lyric Castillo', 'lyric@gmail.com', '678-901-2345'),
('Matthew Poole', 'matthew@gmail.com', '987-654-3210'),
('Ambrose Houston', 'ambrose@gmail.com', '567-890-1234'),
('Daniela Coleman', 'daniela@gmail.com', '123-456-7890'),
('Foster Hebert', 'foster@gmail.com', '345-678-9012'),
('Rory Hale', 'rory@gmail.com', '901-234-5678');

CREATE TABLE housekeeping (
    HousekeepingID INT PRIMARY KEY AUTO_INCREMENT,
    HousekeeperName VARCHAR(255),
    ContactInfo VARCHAR(255),
    Shift VARCHAR(255)
);

INSERT INTO housekeeping (HousekeeperName, ContactInfo, Shift)
VALUES 
('Rowen Meyers', 'rowen.meyers@gmail.com', 'Night'),
('Eliseo Villegas', 'eliseo.villegas@gmail.com', 'Day'),
('Justice Shelton', 'justice.shelton@gmail.com', 'Afternoon'),
('Arian Kelley', 'arian.kelley@gmail.com', 'Night'),
('Karsyn Robinson', 'karsyn.robinson@gmail.com', 'Day'),
('Giuliana Medrano', 'giuliana.medrano@gmail.com', 'Afternoon'),
('Arielle Gentry', 'arielle.gentry@gmail.com', 'Night'),
('Bonnie Lara', 'bonnie.lara@gmail.com', 'Day'),
('Noelle Ryan', 'noelle.ryan@gmail.com', 'Afternoon'),
('Amira Rangel', 'amira.rangel@gmail.com', 'Night');

CREATE TABLE room (
    RoomID INT AUTO_INCREMENT PRIMARY KEY,
    RoomNumber INT NOT NULL,
    RoomFloor INT NOT NULL,
    RoomType VARCHAR(255),
    Availability BOOLEAN,
    CleaningStatus VARCHAR(255),
    LastCleaningDate DATE,
    HousekeepingID INT,
    FOREIGN KEY (HousekeepingID) REFERENCES Housekeeping(HousekeepingID)
);

INSERT INTO room (RoomNumber, RoomFloor, RoomType, Availability, CleaningStatus, LastCleaningDate, HousekeepingID)
VALUES 
(101, 1, 'Single', false, 'Prepared', '2024-04-06 09:00:00', 2),
(102, 1, 'Double', true, 'Needs Cleaning', '2024-04-07 10:00:00', 2),
(103, 1, 'Single', true, 'Prepared', '2024-04-19 11:00:00', 4),
(201, 2, 'Double', false, 'Prepared', '2024-04-08 12:00:00', 3),
(202, 2, 'Triple', false, 'Needs Cleaning', '2024-04-09 13:00:00', 5),
(203, 2, 'Family', true, 'Prepared', '2024-04-07 14:00:00', 8),
(301, 3, 'Single', false, 'Needs Cleaning', '2024-04-11 15:00:00', 10),
(302, 3, 'Double', true, 'Prepared', '2024-04-06 16:00:00', 8),
(303, 3, 'Triple', false, 'Prepared', '2024-04-08 17:00:00', 1),
(304, 3, 'Family', true, 'Needs Cleaning', '2024-04-06 18:00:00', 9);

SET FOREIGN_KEY_CHECKS = 0;

CREATE TABLE billing (
    BillingID INT PRIMARY KEY,
    ReservationID INT,
    CustomerID INT,
    PaymentStatus VARCHAR(255),
    FOREIGN KEY (ReservationID) REFERENCES Reservation(ReservationId),
    FOREIGN KEY (CustomerID) REFERENCES Customer(CustomerId)
);

INSERT INTO billing (BillingID, ReservationID, CustomerID, PaymentStatus)
VALUES
(1, 1, 5, 'Paid'),
(2, 2, 3, 'Overdue'),
(3, 3, 10, 'Paid'),
(4, 4, 4, 'Overdue'),
(5, 5, 2, 'Unpaid'),
(6, 6, 15, 'Unpaid'),
(7, 7, 20, 'Unpaid'),
(8, 8, 10, 'Overdue'),
(9, 9, 22, 'Paid'),
(10, 10, 25, 'Overdue');

SET FOREIGN_KEY_CHECKS = 1;

CREATE TABLE reservation (
    ReservationID INT PRIMARY KEY AUTO_INCREMENT,
    CustomerID INT,
    RoomID INT,
    CheckInDate DATE,
    CheckOutDate DATE,
    BillingID INT,
    FOREIGN KEY (CustomerID) REFERENCES Customer(CustomerID),
    FOREIGN KEY (RoomID) REFERENCES Room(RoomID),
    FOREIGN KEY (BillingID) REFERENCES Billing(BillingID)
);

INSERT INTO Reservation (CustomerID, RoomID, CheckInDate, CheckOutDate, BillingID)
VALUES (1, 3, '2024-04-15', '2024-04-18', 1),
       (2, 5, '2024-04-20', '2024-04-25', 2),
       (3, 6, '2024-05-01', '2024-05-05', 3),
       (4, 7, '2024-05-10', '2024-05-15', 4),
       (5, 8, '2024-05-20', '2024-05-25', 5),
       (6, 10, '2024-05-30', '2024-06-05', 6),
       (7, 1, '2024-06-10', '2024-06-15', 7),
       (8, 2, '2024-06-20', '2024-06-25', 8),
       (9, 4, '2024-06-30', '2024-07-05', 9),
       (10, 9, '2024-07-10', '2024-07-15', 10);
       