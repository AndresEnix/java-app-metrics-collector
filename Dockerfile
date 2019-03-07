FROM openjdk:8-jdk-alpine

ADD service/build/libs/java-app-metrics-collector.jar java-app-metrics-collector.jar

ENTRYPOINT ["java","-Djava.security.egd=file:/dev/./urandom","-jar","java-app-metrics-collector.jar"]
