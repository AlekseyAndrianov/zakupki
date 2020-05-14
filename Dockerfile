FROM adoptopenjdk:14-jre-hotspot

COPY indexer/build/libs/indexer-0.0.1-SNAPSHOT.jar /app.jar
COPY zakupki-service/build/libs/zakupki-service-0.0.1-SNAPSHOT.jar /app.jar

ENTRYPOINT ["java","-jar","/app.jar"]