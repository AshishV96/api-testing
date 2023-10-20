#FROM openjdk:17
#EXPOSE 8080
#ADD target/api-testing.jar api-testing.jar
#ENTRYPOINT [ "java","-jar","/api-testing.jar" ]

FROM maven:3.8.3-openjdk-17 AS build
COPY src /home/app/src
COPY pom.xml /home/app
RUN mvn -f /home/app/pom.xml clean package
EXPOSE 8080
ENTRYPOINT ["java","-jar","/home/app/target/api-testing.jar"]