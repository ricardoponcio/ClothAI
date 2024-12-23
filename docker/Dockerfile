FROM maven:3.8.5-openjdk-17 AS build

RUN apt-get update && apt-get install git -y
RUN apt-get install openssh-server -y

RUN mkdir -p /root/.ssh
RUN chmod -R 600 /root/.ssh/
COPY id_rsa /root/.ssh/id_rsa
RUN ssh-keyscan github.com >>/root/.ssh/known_hosts

WORKDIR /build
RUN git clone git@github.com:ricardoponcio/ClothAI.git
COPY application.properties src/main/resources/application.properties
RUN pushd ClothAI && mvn clean package

FROM openjdk:17-jdk AS run

WORKDIR /app
COPY --from=build /build/ClothAI/target/ /app/ClothAI-0.0.1.jar

ENTRYPOINT ["java", "jar", "/app/ClothAI-0.0.1.jar", "-DskipTests"]