FROM gradle:6.0.1-jdk8 AS build
COPY --chown=gradle:gradle . /home/gradle/src
WORKDIR /home/gradle/src
RUN gradle build -x test --no-daemon

FROM openjdk:8-jdk-alpine
RUN apk add --no-cache bash
MAINTAINER himynameisfil@gmail.com
RUN mkdir -p /data/MarketData/Input/HistoricalOptions
RUN mkdir -p /data/MarketData/Archive/HistoricalOptions
RUN mkdir -p /data/MarketData/Invalid/HistoricalOptions
RUN mkdir -p /data/MarketData/Input/TradierOptions
RUN mkdir -p /data/MarketData/Archive/TradierOptions
RUN mkdir -p /data/MarketData/Invalid/TradierOptions
COPY --from=build /home/gradle/src/build/libs/*.jar /app/MarketDataLoader.jar
ENTRYPOINT ["java","-jar","/app/MarketDataLoader.jar", "bootRun"]