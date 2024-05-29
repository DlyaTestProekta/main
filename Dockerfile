FROM alpine:3.20

RUN apk add temurin-21-jdk
COPY build/libs/main.jar /main.jar

ENTRYPOINT ["java", "-jar", "/main.jar"]
