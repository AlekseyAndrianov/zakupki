FROM adoptopenjdk:14-jre-hotspot

COPY build/libs/indexer-0.0.1-SNAPSHOT.jar /app.jar
COPY build/libs/zakupki-service-0.0.1-SNAPSHOT.jar /app.jar

ENTRYPOINT ["java","-jar","/app.jar"]