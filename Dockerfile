# Build

FROM maven:3.6.3-openjdk-8 as builder
WORKDIR /home/app/
COPY src /home/app/src
COPY pom.xml /home/app
RUN mvn -f /home/app/pom.xml clean package

FROM openjdk:8-jdk-alpine
COPY --from=builder /home/app/target/*.jar /apps/searchPOIartifact.jar
RUN unzip /apps/searchPOIartifact.jar -d /apps/
EXPOSE 80

ENTRYPOINT [ "java", "-jar", "/apps/searchPOIartifact.jar"]