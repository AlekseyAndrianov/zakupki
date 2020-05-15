FROM adoptopenjdk:14-jre-hotspot

COPY indexer/build/libs/indexer-0.0.1-SNAPSHOT.jar /indexer.jar
COPY zakupki-service/build/libs/zakupki-service-0.0.1-SNAPSHOT.jar /service.jar

#ENTRYPOINT ["java","-jar","/app.jar"]