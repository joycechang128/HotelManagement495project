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

CREATE TABLE room (
    RoomID INT AUTO_INCREMENT PRIMARY KEY,
    RoomNumber INT NOT NULL,
    RoomFloor INT NOT NULL,
    RoomType VARCHAR(255),
    Availability BOOLEAN,
    CleaningStatus VARCHAR(255)
);

INSERT INTO room (RoomNumber, RoomFloor, RoomType, Availability, CleaningStatus)
VALUES 
(101, 1, 'Single', false, 'Prepared'),
(102, 1, 'Double', true, 'Needs Cleaning'),
(103, 1, 'Single', true, 'Prepared'),
(201, 2, 'Double', false, 'Prepared'),
(202, 2, 'Triple', false, 'Needs Cleaning'),
(203, 2, 'Family', true, 'Prepared'),
(301, 3, 'Single', false, 'Needs Cleaning'),
(302, 3, 'Double', true, 'Prepared'),
(303, 3, 'Triple', false, 'Prepared'),
(304, 3, 'Family', true, 'Needs Cleaning');

SET FOREIGN_KEY_CHECKS = 0;

CREATE TABLE billing (
    BillingID INT PRIMARY KEY AUTO_INCREMENT,
    ReservationID INT,
    CustomerID INT,
    PaymentStatus VARCHAR(255),
    FOREIGN KEY (ReservationID) REFERENCES Reservation(ReservationID),
    FOREIGN KEY (CustomerID) REFERENCES Customer(CustomerID)
);

INSERT INTO billing (ReservationID, CustomerID, PaymentStatus)
VALUES
(1, 5, 'Paid'),
(2, 3, 'Overdue'),
(3, 10, 'Paid'),
(4, 4, 'Overdue'),
(5, 2, 'Unpaid'),
(6, 9, 'Unpaid'),
(7, 1, 'Unpaid'),
(8, 10, 'Overdue'),
(9, 6, 'Paid'),
(10, 7, 'Overdue');

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

INSERT INTO reservation (CustomerID, RoomID, CheckInDate, CheckOutDate, BillingID)
VALUES 
(5, 3, '2024-06-15', '2024-06-18', 1),
(3, 5, '2024-06-20', '2024-06-25', 2),
(10, 6, '2024-06-21', '2024-06-25', 3),
(4, 7, '2024-06-22', '2024-06-27', 4),
(2, 8, '2024-06-25', '2024-06-30', 5),
(9, 10, '2024-06-28', '2024-07-03', 6),
(1, 1, '2024-06-30', '2024-07-05', 7),
(10, 2, '2024-07-01', '2024-07-08', 8),
(6, 4, '2024-07-05', '2024-07-10', 9),
(7, 9, '2024-06-10', '2024-06-20', 10);
