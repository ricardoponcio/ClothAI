FROM maven:3.8.5-openjdk-17 AS build

WORKDIR /build
COPY . .
RUN mvn clean package

FROM openjdk:17-jdk AS run

WORKDIR /app
COPY --from=build /build/target/ /app/ClothAI-0.0.1.jar

ENTRYPOINT ["java", "jar", "/app/ClothAI-0.0.1.jar", "-DskipTests"]