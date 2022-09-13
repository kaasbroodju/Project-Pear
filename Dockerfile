#FROM maven:3.8.5-openjdk-18 AS build
#COPY src /oep/pear/src
#COPY pom.xml /oep/pear
#RUN mvn -f /oep/pear/pom.xml clean package

FROM openjdk:18-alpine
ADD target/pear-api.jar pear-api.jar
ENTRYPOINT ["java","--enable-preview","-jar","/pear-api.jar"]
EXPOSE 3080