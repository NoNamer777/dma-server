FROM gradle:7.3-jdk11 as build

ADD . /usr/src/tmp

WORKDIR /usr/src/tmp

RUN ./gradlew init && ./gradlew build

FROM gradle:7.2-jre11

WORKDIR /usr/src/app

COPY --from=build /usr/src/tmp/build/libs/*.jar ./app.jar

EXPOSE 8080

ENTRYPOINT ["java", "-jar", "/usr/src/app/app.jar"]
