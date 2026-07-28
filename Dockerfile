FROM eclipse-temurin:26-jdk AS build

WORKDIR /app

COPY . .

RUN ./mvnw clean package -DskipTests


FROM eclipse-temurin:26-jre

ARG APP_VERSION=local

ENV APP_VERSION=$APP_VERSION

WORKDIR /app

COPY --from=build /app/target/*.jar app.jar

ENTRYPOINT ["java","-jar","app.jar"]