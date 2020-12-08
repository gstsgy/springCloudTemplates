FROM java:8

RUN mkdir /app
WORKDIR /app
COPY build/libs/gateway-2.0.0.jar /app
ENTRYPOINT ["java", "-Djava.security.egd=file:/dev/./urandom", "-jar", "/app/gateway-2.0.0.jar", "--spring.profiles.active=docker"]
EXPOSE 8080