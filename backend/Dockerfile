# Use the official Gradle image to build the app
FROM gradle:8.9.0-jdk21 AS build
WORKDIR /app
COPY . .
RUN gradle build --no-daemon -x test

# Use the official Amazon Corretto image to run the app
FROM amazoncorretto:21
VOLUME /tmp
COPY --from=build /app/build/libs/backend-0.0.1-SNAPSHOT.jar app.jar
ENTRYPOINT ["java","-jar","/app.jar"]
