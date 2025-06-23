Leave Management System (Spring Boot + MySQL + Redis + Kafka)

This is a Spring Boot-based Leave Management System that supports two types of users:

1. Manager

2. Employee

Managers can manage leave requests, while employees can apply for and track their leaves. The system includes robust role-based access, email notifications via Kafka, and Redis caching for better performance.

🚀 Features
1. Manager
   - Register & Login
   - Approve or reject leave requests
   - View all leave requests
   - View pending leave requests
3. Employee
   - Register & Login
   - Apply for leave
   - View leave history

🛠️ Tech Stack

   Backend:	Spring Boot
   
   Database:	MySQL
   
   Cache:	Redis
   
   Messaging:	Apache Kafka
   
   Security:	Spring Security + JWT
   
   Build: Tool	Maven

📁 Project Structure

    src/
    
    ├── controller/        → REST APIs
    
    ├── service/           → Interfaces
    
    ├── serviceImpl/       → Business logic
    
    ├── entity/            → JPA Entities
    
    ├── payload/           → DTOs & Response Models
    
    ├── repository/        → JPA Repositories
    
    ├── exception/         → Custom exceptions
    
    ├── security/          → JWT & user authentication
    
    ├── securityConfig/    → Spring Security configuration
    
🔌 Integrations

    MySQL – Stores users, leave records.
    
    Redis – Caches: Employee's leave history, For Manager pending leaves
    
    Kafka – Sends email notifications when employees apply for leave.

📦 Getting Started

   ✅ Prerequisites
   
       - Java 17+

       - Maven

       - MySQL

       - Redis

       - Kafka

       - Docker (optional for containerization)

  ⚙️ MySQL Setup
  
     Create a database named:

        CREATE DATABASE leavemanagement;

     Set the following in your .env or application.properties:

        spring.datasource.url=jdbc:mysql://localhost:3306/leavemanagement
        
        spring.datasource.username=root
        
        spring.datasource.password=yourpassword
        
🧠 Redis Setup

    Start Redis server locally:

       redis-cli
       
 📬 Kafka Setup
 
    Start Zookeeper and Kafka server:

    # Start Zookeeper
    
      bin/zookeeper-server-start.sh config/zookeeper.properties (or)
      
      .\zookeeper-server-start.bat ..\..\config\zookeeper.properties

    # Start Kafka broker
    
      bin/kafka-server-start.sh config/server.properties (or)

      .\kafka-server-start.bat ..\..\config\server.properties
      
🏗️ Build & Run
    
    # Build the project
    
    mvn clean install
    
    # Run the application
    
    mvn spring-boot:run
    
    The app will be available at http://localhost:8081.
    
🔐 API Endpoints

    ⚠️ All endpoints are protected via JWT. First register/login to get a token.
    
    🔑 Auth
    
      Method	Endpoint	Description
      
      POST	/api/auth/register/employee	Register as employee
      
      POST	/api/auth/register/manager	Register as manager
      
      POST	/api/auth/login	Login & get token
    
    👩‍💻 Employee
    
      Method	Endpoint	Description
      
      POST	/api/employee/leave/{userId}	Apply for leave
      
      GET	/api/employee/leave/users/{userId}	Get own leave history
    
    👨‍💼 Manager
    
      Method	Endpoint	Description
      
      GET	/api/manager/leave/pending	View pending leave requests
      
      GET	/api/manager/leave/leaves	View all leave requests
      
      POST	/api/manager/leave/setstatus/{id}	Approve/Reject a leave request
    
📧 Kafka Notification
    
    On leave application, an email notification is published via Kafka to a consumer service that sends an email to the employee.
    
🐳 Run with Docker (optional)
    
    Build and run using Docker Compose:

    docker-compose up --build
