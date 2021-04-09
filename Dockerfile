FROM adoptopenjdk/openjdk11:alpine-jre
ADD target/springboot-mongodb-0.0.1-SNAPCHOT.jar app.jar
ENTRYPOINT ["java","-jar","app.jar"]