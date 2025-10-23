🏦 Online Banking System (Spring Boot Project)
# Overview
A simple Online Banking System built with Spring Boot.
Users can register, log in (JWT), create accounts, check balances, and transfer money between accounts.
This project demonstrates core Java backend development skills — entities, services, REST controllers, and JWT authentication.
# Tech Stack
Java 17
Spring Boot 3
Spring Data JPA (Hibernate)
Spring Security + JWT
MySQL
Lombok
Maven
# Main Features
✅ User registration and login with JWT authentication
💳 Account creation and balance tracking
💸 Money transfer between accounts
🔐 Secure endpoints using JWT
⚙️ MySQL database integration
# API Endpoints
Auth
POST /auth/register → Register new user
POST /auth/login → Login and receive JWT token
Accounts
GET /api/accounts/user/{userId} → Get all accounts of a user
GET /api/accounts/{accountId}/balance → Get balance
POST /api/accounts?userId=1&currency=AZN&initialBalance=1000 → Create new account
Transactions
POST /api/transactions/transfer?senderAccountId=1&receiverAccountId=2&amount=100&description=Payment → Transfer money
# How to Run
Clone the repo:
git clone https://github.com/asifrhmv/springboot-Bank-app.git
Create a MySQL database:
CREATE DATABASE onlinebank;
Update application.properties:
spring.datasource.url=jdbc:mysql://localhost:3306/onlinebank
spring.datasource.username=root
spring.datasource.password=root
spring.jpa.hibernate.ddl-auto=update
Run the app:
mvn spring-boot:run
# Author
Asif Rehimov
📧 asif.rehimov64@gmail.com
