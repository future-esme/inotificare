FROM openjdk:17
COPY  /target/*.jar .
ENTRYPOINT java --enable-preview -jar *SNAPSHOT.jar