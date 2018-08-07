# AMT
Automobile Maintenance Tracker

This is a simple application to keep track of vehicle maintenance. There are 2 parts to this application:
 - The web front end uses 
    - JSP and the MVC 1.0 (ozark)
    - BootStrap and JQuery
 - Restful Webservices - these are use to populate the web front end
 
 To run this application
  

Building
-------------------

Prerequisites:

* Maven 3.5.0 or Newer
* JDK 10

To build with your own Maven installation:
    mvn install
    
To run the application
    mvn jetty:run -DskipTests
 