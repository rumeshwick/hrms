# HRMS

This is a simple Rest API for a Human Resource Management System

## Available features

  #### View departments - (Reference Data)

    URL(GET) - http://localhost:9090/api/v1/department

    Response - {"departments":[{"id":1,"code":"HR","name":"HR Department","phone":"0112274937"},{"id":2,"code":"ACC","name":"Accounts Department","phone":"0112233937"},{"id":3,"code":"SEC","name":"Security Department","phone":"0112274557"},{"id":4,"code":"ITD","name":"IT Department","phone":"0112274988"}]}


  #### Save employees - (With assigned departments)

    URL(POST) - http://localhost:9090/api/v1/employee/save

    Request Body - {"firstName":"Json","lastName":"Holder","address":"Test Address","bankCode":"SMB","bankAccNo":"42344335435345","departments":[{"id":1}]}
    
    Response - {"employee":{"id":1,"firstName":"Json","lastName":"Holder","address":"Test Address","bankCode":"SMB","bankAccNo":"42344335435345","registeredDate":"2020-09-09T02:46:56.959+00:00","image":null,"departments":[{"id":1,"code":"HR","name":"HR Department","phone":"0112274937"}]}}
    
    *** NOTE - firstName and lastName is mandotory
    
    
  #### View employees - (With assigned departments)

    URL(GET) - http://localhost:9090/api/v1/employee

    Response - {"employees":[{"id":1,"firstName":"Json","lastName":"Holder","address":"Test Address","bankCode":"SMB","bankAccNo":"42344335435345","registeredDate":"2020-09-09T03:01:51.540+00:00","image":null,"departments":[{"id":1,"code":"HR","name":"HR Department","phone":"0112274937"}]}]}


  #### Update employees - (With assigned departments)

    URL(PUT) - http://localhost:9090/api/v1/employee/save/1

    Request Body - {"firstName":"Json","lastName":"Stathom","address":"Test Address","bankCode":"SMB","bankAccNo":"42344335435345","departments":[{"id":1}]}
    
    Response - {"success":"Employee updated"}
    
    *** NOTE - firstName and lastName is mandotory
    

  #### Delete employees

    URL(Delete) - http://localhost:9090/api/v1/employee/delete/1

    Response - {"success":"Employee deleted"}
    

  #### Upload employee image - (With assigned departments)

    URL(POST) - http://localhost:9090/api/v1/employee/image_upload/{employee_id}
    
    file - select file

    Response - {"success":"Image uploaded"}

  ### Spring use bellow database configurations

	spring.datasource.url=jdbc:h2:mem:hrmsdb
	spring.datasource.driverClassName=org.h2.Driver
	spring.datasource.username=sa
	spring.datasource.password=password

  ### Below url can be used to access H2 Console
  
  http://localhost:9090/h2-console
  
  #### Update below property in application.properties file to change the image upload location
  
    image.upload.path=/Users/rumesh/MyStorage/Projects/static_contents/images/
  
  ## Build Project
  
    clone the project
    cd to the root folder
    mvn clean install
    docker build -t hrms-docker.jar . 
    docker run -p 9090:8080 hrms-docker.jar
  
  #### Use this to run without docker
    mvn spring-boot:run



