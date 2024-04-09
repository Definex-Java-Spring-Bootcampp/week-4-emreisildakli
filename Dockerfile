FROM openjdk:21
LABEL authors="emreisildakli"
ADD target/kredinbizde-service-0.0.1-SNAPSHOT.jar week-4-emreisildakli.jar
ENTRYPOINT ["java", "-jar", "week-4-emreisildakli.jar"]