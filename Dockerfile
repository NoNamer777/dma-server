FROM gradle:7.3-jdk11 as build

LABEL maintainer = 'Oscar Wellner, https://github.com/NoNamer777'

ADD . /usr/src/tmp
WORKDIR /usr/src/tmp
RUN ./gradlew build

FROM adoptopenjdk/openjdk11:jre-11.0.12_7-ubuntu

ENV SPRING_DATASOURCE_URL='jdbc:mysql://localhost:3306/db' \
    SPRING_DATASOURCE_USERNAME='user' \
    SPRING_DATASOURCE_PASSWORD='password'

WORKDIR /usr/src/server
COPY --from=build /usr/src/tmp/build/libs/dma-server-*.jar server.jar

EXPOSE 8080

ENTRYPOINT [ "java", "-jar", "server.jar" ]
