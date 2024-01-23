#based docker image
FROM eclipse-temurin:17
LABEL maintainer="teamnova.com"
ADD target/my-app-test-0.0.1-SNAPSHOT.jar my-test-app.jar
ENTRYPOINT ["java","-jar","my-test-app.jar"]

