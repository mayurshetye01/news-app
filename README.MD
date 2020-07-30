------------------------------------------------------------------------------------------------
Description
------------------------------------------------------------------------------------------------
This application serves as the API backend to consume Hacker News REST APIs

------------------------------------------------------------------------------------------------
Setup
------------------------------------------------------------------------------------------------
1. Build the application using mvn clean install
2. Run the application using java -jar news-app\target\newsapp.jar

The default port is 9090

------------------------------------------------------------------------------------------------
Important Links
------------------------------------------------------------------------------------------------
Swagger API documentation can be accessed under http://localhost:9090/swagger-ui.html


------------------------------------------------------------------------------------------------
Database
------------------------------------------------------------------------------------------------
Application uses in-memory h2 database for simplicity.
The database console can be accessed under http://localhost:9090/h2-console

Default username/password - admin/admin

Database connection properties can be configured in application.yaml

------------------------------------------------------------------------------------------------
Improvement items
------------------------------------------------------------------------------------------------
 1. Add Exception handling at all layers
 2. Add JUnit test for REST Controllers and Repository layer
 3. Set up application to run inside Docker
 4. Evaluate performance improvement options