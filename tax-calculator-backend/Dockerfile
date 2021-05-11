FROM adoptopenjdk/openjdk11:alpine
COPY ./target/*.jar /home/app.jar
WORKDIR /home
CMD ["java", "-Xmx1g", "-jar","-Dspring.profiles.active=prod", "/home/app.jar"]