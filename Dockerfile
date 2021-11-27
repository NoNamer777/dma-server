FROM gradle:7.3-jdk11 as build

LABEL maintainer = 'Oscar Wellner, https://github.com/NoNamer777'

ADD . /usr/src/tmp
WORKDIR /usr/src/tmp
RUN ./gradlew build

FROM adoptopenjdk/openjdk11:jre-11.0.12_7-ubuntu

# A JDBC url of the database
ARG DMA_DATABASE_URL='jdbc:mysql://localhost:3306/db'

# The user the server uses to log into the password
ARG DMA_DATABASE_USERNAME='user'

# The password the database user use to authenticate himself
ARG DMA_DATABASE_PASSWORD='password'

# Pass the build arguments to the spring environment
ENV SPRING_DATASOURCE_URL=$DMA_DATABASE_URL \
    SPRING_DATASOURCE_USERNAME=$DMA_DATABASE_USERNAME \
    SPRING_DATASOURCE_PASSWORD=$DMA_DATABASE_PASSWORD

WORKDIR /usr/src/server
COPY --from=build /usr/src/tmp/build/libs/dma-server-*.jar server.jar

EXPOSE 8080

ENTRYPOINT [ "java", "-jar", "server.jar" ]
