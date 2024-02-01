#
# Build stage
#
FROM maven:3.8.4-openjdk-17 as BUILD
COPY . .
RUN mvn clean package -Pprod -DskipTests

#
# Package stage
#
FROM openjdk:17-jdk-slim
COPY --from=build /target/exotic.2000-0.0.1-SNAPSHOT.jar demo.jar
# ENV PORT=8081
EXPOSE 8081
ENTRYPOINT ["java","-jar","demo.jar"]