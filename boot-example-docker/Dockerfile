FROM openjdk:8-jdk-alpine
VOLUME /Users/pengli/software/docker
COPY target/boot-example-docker-1.0-SNAPSHOT.jar app.jar
ENTRYPOINT ["java","-jar","/app.jar"]