FROM eclipse-temurin:17
LABEL maintainer="teamnova.com"
ADD target/Health-Hivw-0.0.1-SNAPSHOT.jar health_hive.jar
ENTRYPOINT ["java","-jar","health_hive.jar"]