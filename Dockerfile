FROM gradle:jdk10 as builder
RUN gradle build --no-daemon

FROM openjdk:8-jdk-alpine
RUN apk add --no-cache bash
MAINTAINER himynameisfil@gmail.com
RUN mkdir -p /data/stocks/tradier/input
RUN mkdir -p /data/stocks/yahoo/input
RUN mkdir -p /data/options/tradier/input
RUN mkdir -p /data/options/historicaloptions/input
RUN mkdir -p /data/stocks/tradier/output
RUN mkdir -p /data/stocks/yahoo/output
RUN mkdir -p /data/options/tradier/output
RUN mkdir -p /data/options/historicaloptions/output
RUN mkdir -p /data/stocks/tradier/invalid
RUN mkdir -p /data/stocks/yahoo/invalid
RUN mkdir -p /data/options/tradier/invalid
RUN mkdir -p /data/options/historicaloptions/invalid
COPY build/libs/*.jar market-data-loader.jar
ENTRYPOINT ["java","-jar","market-data-loader.jar", "bootRun"]