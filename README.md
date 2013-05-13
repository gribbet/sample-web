sample-web
==========

A sample web application backend and frontend.  
Backend: Java REST API with OAuth authentication.  
Frontent: CoffeeScript single page application. Chrome only. Try it out at http://173.230.144.77:3000/

Backend
----------
###Requirements
- JDK 1.6
- Maven 2
- ImageMagick

###Instructions
- mvn package exec:java
- navigate to http://localhost:8080/

###Libraries
- Grizzly web server
- JAX-RS/Jersey
- JPA/Hibernate
- Guice
- Lucene

Frontend
----------
###Requirements
- nodejs
- npm

###Instructions
- npm install
- npm start
- navigate to http://localhost:3000/

