FROM maven:3.9.6-eclipse-temurin-25 AS build
WORKDIR /build

COPY grahaksensemono/pom.xml .
COPY grahaksensemono/.mvn .mvn
COPY grahaksensemono/mvnw .
RUN chmod +x mvnw

COPY grahaksensemono/src ./src
RUN ./mvnw -B clean package -DskipTests

FROM eclipse-temurin:25-jdk-jammy
WORKDIR /app
COPY --from=build /build/target/*.jar app.jar
EXPOSE 8080
ENTRYPOINT ["sh","-c","java -jar /app/app.jar --server.port=${PORT:-8080}"]
