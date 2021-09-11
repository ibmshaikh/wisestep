FROM openjdk:8-alpine
WORKDIR = /opt/app
COPY /target/wisestep-0.0.1-SNAPSHOT.jar wisestepv1.jar
EXPOSE 8080
ENTRYPOINT ["java","-jar","wisestepv1.jar"]