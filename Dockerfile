FROM openjdk:11
EXPOSE 8080
ADD target/hrms-0.0.1-SNAPSHOT.jar hrms-0.0.1-SNAPSHOT.jar
ENTRYPOINT ["java","-jar","/hrms-0.0.1-SNAPSHOT.jar"]