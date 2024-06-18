# Hotel Management System
# CMSC 495 Capstone Project

## Description
The hotel management project aims to develop a comprehensive software solution for managing various aspects of a hotel business. Create a basic application for a hotel management system that will have room, billing, reservation, and customers accounted for. The system will provide functionalities such as room booking, check-in/check-out, and billing. The goal is to streamline hotel operations, improve customer satisfaction, and enhance overall efficiency. We will be using MySQL, Java EE IDE, Spring Initializr(Spring framework), and GitHub to create the hotel management system.

## Table of Contents
1. [Installation](#installation)
2. [Usage](#usage)
3. [Examples](#examples)

## Installation
### Development Tools
- Java EE IDE: IntelliJ IDEA, Eclipse IDE for Java EE
- Maven
- MySQL Workbench for database management
### Frameworks and Libraries
- Spring Initializr for setting up the project with Spring Boot
- Spring Framework for dependency injection, MVC architecture, and transaction management
- Hibernate ORM for object-relational mapping
- MySQL as the relational database management system
- Thymeleaf as a template for Spring MVC

### Steps
1. Clone the repository
2. Install dependencies
3. Configure the application:
    - Create a `application.properties` file in `src/main/resources/`
    - Add your MySQL configuration:
    ```properties
    spring.datasource.url=jdbc:mysql://localhost:3306/
    spring.datasource.username=root
    spring.datasource.password=password
    ```

## Usage
1. Run the application
2. Open your browser and go to `http://localhost:8080`.

## Examples
- To go to any management pages, go to the login page and log in.
- To view available rooms, go to the room page.
- To create a new room, go to the room page, click CREATE button, and fill in the details.
- To modify an existing room data, click EDIT button.
- To delete a room data, click DELETE button.
- To view customers, go to the customer page.
- To create a new customer, go to the customer page, click CREATE button, and fill in the details.
- To modify an existing customer data, click EDIT button.
- To view reservations, go to the reservation page.
- To create a new reservation, go to the reservation page, click CREATE button, and fill in the details.
- To modify an existing reservation data, click EDIT button.

Project Link: https://github.com/joycechang128/HotelManagement495project
