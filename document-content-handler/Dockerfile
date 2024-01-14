FROM --platform=linux/amd64  amazoncorretto:21-alpine3.16
COPY /target/*.jar app.jar
ENTRYPOINT ["java","-jar","/app.jar"]