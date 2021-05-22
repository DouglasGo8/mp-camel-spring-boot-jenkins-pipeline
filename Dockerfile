FROM mcr.microsoft.com/java/jre:8u292-zulu-alpine
COPY ./target/*-SNAPSHOT.jar app.jar
ENTRYPOINT ["java", "-jar", "app.jar"]
