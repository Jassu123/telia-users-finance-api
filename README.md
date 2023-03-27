# telia-users-finance-api

RRunning the application
To run the application, follow these steps:
-----------------------------------------

Clone the repository to your local machine.
Open a terminal and navigate to the root directory of the project.
Run the following command: mvn spring-boot:run
Wait for the application to start up.
Open your web browser and go to the following URL: http://localhost:8080/swagger-ui.html
You should see the Swagger UI interface with the available REST API endpoints

Configuring the database:
------------------------
The application is configured to use an in-memory H2 database by default. You can change the database configuration by modifying the application.properties file located in the src/main/resources directory.

Available endpoints
The following endpoints are available in this application:

GET /api/users: Get All Users REST API is used to get a all the users from the database.

GET /api/users/{id}: Get User By ID REST API is used to get a single user from the database.

POST /api/users: Create User REST API is used to save user information in a database.

PUT /api/users/{id}: Update User REST API is used to update a particular user in the database.

DELETE /api/users/{id}: Deletes a user from the database.

GET /api/users/getbynameandpersonalnumber:Build Get All Users REST API  with fullname and personal number

Documentation:
-------------
Swagger UI is integrated into the application to provide API documentation. You can access the Swagger UI interface at http://localhost:8080/swagger-ui.html once the application is running.

Accessing the H2 Console:
------------------------
The H2 Console can be accessed at http://localhost:8080/h2-console. The login credentials are as follows:

Driver Class: org.h2.Driver
JDBC URL: jdbc:h2:mem:telia
User Name: sa
Password: password


