FROM openjdk:17
EXPOSE 8080
ADD target/api-testing.jar api-testing.jar
ENTRYPOINT [ "java","-jar","/api-testing.jar" ]