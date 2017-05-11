# demo

A simple restful project demo.

# Dependency

* Maven
* RESTful API
* Spring
* H2database or Mysql
* Jetty

# How to start

* Make sure you have `Java` on you computer and `JAVA_HOME/bin` in system environment `PATH`.
* Use default h2database or prepare Mysql database.
* Download it from github.
* Compile with JDK1.7 and Maven.
* CD `${project.path}demo-bundle\target\jetty-runnerable` and run `java -jar jetty-runner.jar --lib lib --config jetty-demo.xml demo-impl.war`.

# How to config database

Default database is H2Database in mem.

Change to mysql:
* Open file `${project.path}demo-bundle\target\jetty-runnerable\jetty-demo.xml`.
* Change the configuration of `jdbc/mysql.datasource` like 
```
<Set name="Url">jdbc:mysql://localhost:3306/mydata</Set>
<Set name="User">username</Set>
<Set name="Password">password</Set>
```
* Restart server use `java -Dspring.profiles.active=mysql -jar jetty-runner.jar --lib lib --config jetty-demo.xml demo-impl.war`.

# API

## Department

### 1. Get a department by id
* Request : http://<hostname:port>/demo/dept/{id}
* Method : GET
* Content-Type : application/json
* Body : null
* Parameters : id (type = number, required = true)

* Success Response
* Status : 200
* Content-Type: application/json
* Message : success
* Entity : Department

* Error Response
* Status : 500 / 404
* Content-Type: application/json
* Message : Internal Error / Department id = {id} not found
* Entity : null

### 2. Get all departments
* Request : http://<hostname:port>/demo/depts
* Method : GET
* Content-Type : application/json
* Body : null

* Success Response
* Status : 200
* Content-Type: application/json
* Message : success
* Entity : List\<Department\>

* Error Response
* Status : 500
* Content-Type: application/json
* Message : Internal Error
* Entity : null

### 3. Get parent department by id
* Request : http://<hostname:port>/demo/dept/{id}/parentdept
* Method : GET
* Content-Type : application/json
* Body : null
* Parameters : id (type = number, required = true)

* Success Response
* Status : 200
* Content-Type: application/json
* Message : success
* Entity : Department

* Error Response
* Status : 500 / 404
* Content-Type: application/json
* Message : Internal Error / Department id = {id} not found
* Entity : null

### 4. Get sub departments by id
* Request : http://<hostname:port>/demo/dept/{id}/subdepts
* Method : GET
* Content-Type : application/json
* Body : null
* Parameters : id (type = number, required = true)

* Success Response
* Status : 200
* Content-Type: application/json
* Message : success
* Entity : List\<Department\>

* Error Response
* Status : 500 / 404
* Content-Type: application/json
* Message : Internal Error / Department id = {id} not found
* Entity : null

### 5. Get all employees in a department by department id
* Request : http://<hostname:port>/demo/dept/{id}/employees
* Method : GET
* Content-Type : application/json
* Body : null
* Parameters : id (type = number, required = true)

* Success Response
* Status : 200
* Content-Type: application/json
* Message : success
* Entity : List\<Employee\>

* Error Response
* Status : 500 / 404
* Content-Type: application/json
* Message : Internal Error / Department id = {id} not found
* Entity : null

### 6. Create a sub department by parent department id
* Request : http://<hostname:port>/demo/dept/{id}/subdept
* Method : POST
* Content-Type : application/json
* Body : Department
* Parameters : id (type = number, required = true)

* Success Response
* Status : 200
* Content-Type: application/json
* Message : success
* Entity : Department

* Error Response
* Status : 500 / 404
* Content-Type: application/json
* Message : Internal Error / Department id = {id} not found / No content with Department
* Entity : null

### 7. Create a department
* Request : http://<hostname:port>/demo/dept
* Method : POST
* Content-Type : application/json
* Body : Department

* Success Response
* Status : 200
* Content-Type: application/json
* Message : success
* Entity : Department

* Error Response
* Status : 500 / 404
* Content-Type: application/json
* Message : Internal Error / Department id = {id} not found / No content with Department
* Entity : null

### 8. Update a department by department id
* Request : http://<hostname:port>/demo/dept/{id}
* Method : PUT
* Content-Type : application/json
* Body : Department
* Parameters : id (type = number, required = true)

* Success Response
* Status : 200
* Content-Type: application/json
* Message : success
* Entity : Department

* Error Response
* Status : 500 / 404
* Content-Type: application/json
* Message : Internal Error / Department id = {id} not found / No content with Department
* Entity : null

### 9. Delete a department by department id
* Request : http://<hostname:port>/demo/dept/{id}
* Method : DELETE
* Content-Type : application/json
* Body : null
* Parameters : id (type = number, required = true)

* Success Response
* Status : 200
* Content-Type: application/json
* Message : success
* Entity : Department

* Error Response
* Status : 500 / 404
* Content-Type: application/json
* Message : Internal Error / Department id = {id} not found
* Entity : null

## Employee

### 1. Get a employee by employee id
* Request : http://<hostname:port>/demo/employee/{id}
* Method : GET
* Content-Type : application/json
* Body : null
* Parameters : id (type = number, required = true)

* Success Response
* Status : 200
* Content-Type: application/json
* Message : success
* Entity : Employee

* Error Response
* Status : 500 / 404
* Content-Type: application/json
* Message : Internal Error / Employee id = {id} not found
* Entity : null

### 2. Get all employees
* Request : http://<hostname:port>/demo/employees
* Method : GET
* Content-Type : application/json
* Body : null

* Success Response
* Status : 200
* Content-Type: application/json
* Message : success
* Entity : List\<Employee\>

* Error Response
* Status : 500
* Content-Type: application/json
* Message : Internal Error
* Entity : null

### 3. Create a employee in a department by department id
* Request : http://<hostname:port>/demo/dept/{id}/employee
* Method : POST
* Content-Type : application/json
* Body : Employee
* Parameters : id (type = number, required = true)

* Success Response
* Status : 200
* Content-Type: application/json
* Message : success
* Entity : Employee

* Error Response
* Status : 500 / 404
* Content-Type: application/json
* Message : Internal Error / Department id = {id} not found / No content with Employee
* Entity : null

### 4. Update a employee by id
* Request : http://<hostname:port>/demo/employee/{id}
* Method : PUT
* Content-Type : application/json
* Body : Employee
* Parameters : id (type = number, required = true)

* Success Response
* Status : 200
* Content-Type: application/json
* Message : success
* Entity : Employee

* Error Response
* Status : 500 / 404
* Content-Type: application/json
* Message : Internal Error / Employee id = {id} not found / No content with Employee
* Entity : null

### 5. Delete a employee by id
* Request : http://<hostname:port>/demo/employee/{id}
* Method : DELETE
* Content-Type : application/json
* Body : null
* Parameters : id (type = number, required = true)

* Success Response
* Status : 200
* Content-Type: application/json
* Message : success
* Entity : Employee

* Error Response
* Status : 500 / 404
* Content-Type: application/json
* Message : Internal Error / Employee id = {id} not found
* Entity : null
