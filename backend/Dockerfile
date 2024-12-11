FROM openjdk:17-jdk-alpine
VOLUME /tmp
EXPOSE 8080
ADD target/travel-app.jar travel-app.jar
ENTRYPOINT ["java", "-jar", "/travel-app.jar"]