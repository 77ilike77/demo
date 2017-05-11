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
