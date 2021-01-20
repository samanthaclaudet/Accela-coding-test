<h1>Accela coding test</h1>

<h2>This web application manages Persons and Addresses.</h2>

<h3>In order to run, please use: java -jar codingtest-0.0.1-SNAPSHOT.jar</h3>

Developed using Eclipse, Java 8, Spring Boot, Hibernate, JPA, JUnit and Mockito.
<br>
Built using Maven (mvn clean install).
<br>
The database used is H2. Its creation is automatic but you can also have a look at backup_db_creation.sql in the main folder.
<br>
<br>
The welcome page (<a href="http://localhost:8080">http://localhost:8080</a>) sums up all available actions.
<br>
It is possible to view data there, but it's not an actual front-end.
<br>
To test create, update and delete actions, please use Postman.
<br>
Here is a link to my collection of requests :

[![Run in Postman](https://run.pstmn.io/button.svg)](https://god.postman.co/run-collection/7e3c7de484ca6b097dab)

(if button doesn't work please use this link to import in your workspace: <a href="https://www.getpostman.com/collections/e3b4758b2a62f5b9581c">https://www.getpostman.com/collections/e3b4758b2a62f5b9581c</a>)

This test suite does as follow :
- create three new persons --> for each we expect a message of successful creation
- show list --> we expect to see all three newly created elements
- show person #2 --> we expect to get John Doe
- count --> 3
- add an address to person #1, two addresses to person #2 --> for each we expect a message of successful creation
- update person #1 --> we expect a message of successful update
- delete person #3, delete person #1 --> for each we expect a message of successful deletion
- count --> 1
- view addresses of person #2 --> we expect to see two addresses
- view address #3 --> we expect to get the last address created
- update address #3 --> we expect a message of successful update
- delete address #2 --> we expect a message of successful deletion
- view addresses of person #2 --> we expect to see the updated address only
- update person #1 --> we expect a not found message
- delete address #1 --> we expect a not found message

Feel free to use these as basis for more tests.

Here are a few next steps that can be considered :
- develop the front-end side (view list of persons on welcome page, creation form, delete button, confirmation pop-up, ...)
- add logs
- improve the error handling of the application, for example using HTTP return codes
- use data validation and increase the security for creation and update
- include more tests
- ...

Thank you
