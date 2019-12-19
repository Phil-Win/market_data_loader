#FROM gradle:4.10.0-jdk8-alpine AS build
#RUN gradle build --no-daemon

FROM openjdk:8-jre-alpine
RUN apk add --no-cache bash
MAINTAINER himynameisfil@gmail.com
RUN mkdir -p /volume
COPY build/libs/*.jar marketdataloader.jar
ENTRYPOINT ["java","-jar","marketdataloader.jar", "bootRun"]