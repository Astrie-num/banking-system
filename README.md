# Banking System

## Overview
The Banking System is a Java-based application developed with Spring Boot to manage banking operations. 
It allows users to register, save, withdraw, and transfer money to other clients, sends email notifications for transactions, and logs transaction messages.

## Features
- Register new customers
- Save, withdraw, and transfer money between clients
- Send email notifications to sender and receiver
- Log transaction messages

## Technologies Used
- **Language**: Java
- **Framework**: Spring Boot (Spring Data JPA, Spring Mail)
- **Database**: MySQL (or any compatible DB)
- **Other**: JavaMailSender for email notifications

## Setup
1. **Clone the Repo**:
   ```bash
   git clone <repository-url>
   cd banking-system


2.**Set Up Database**:

Create a MySQL database (e.g., banking_system).
Update application.properties with your DB credentials

     spring.datasource.url=jdbc:mysql://localhost:8095/banking_system
    spring.datasource.username=your_username
    spring.datasource.password=your_password
    spring.jpa.hibernate.ddl-auto=update
    spring.mail.host=smtp.gmail.com
    spring.mail.username=your_email@gmail.com
    spring.mail.password=your_email_password

3.**Run the Application**
         
    mvn spring-boot:run
 Access the app at http://localhost:8095


##Usage
  Register a customer via the API or UI (if available).
  Perform transactions (save, withdraw, transfer).
  Check email for transaction notifications.
  View transaction logs in the database.

  
##Contact
GitHub: Astrie-num
Email: uwasterie07@gmail.com
