FROM gradle:7.3-jdk11 as build

LABEL maintainer = 'Oscar Wellner, https://github.com/NoNamer777'

ADD . /usr/src/tmp
WORKDIR /usr/src/tmp
RUN ./gradlew build

FROM adoptopenjdk/openjdk11:jre-11.0.12_7-ubuntu

ARG DB_URL
ARG DB_USER
ARG DB_PASSWORD

WORKDIR /usr/src/server
COPY --from=build /usr/src/tmp/build/libs/dma-server-*.jar server.jar

EXPOSE 8080

ENTRYPOINT [ "java", "-Dspring.datasource.url=jdbc:$DB_URL", "-Dspring.datasource.username=$DB_USER", "-Dspring.datasource.password=$DB_PASSWORD", "-jar", "server.jar" ]
