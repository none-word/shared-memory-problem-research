FROM openjdk:17

WORKDIR /producer

COPY . .

EXPOSE 4501

RUN chmod +x mvnw
ENTRYPOINT ["./mvnw", "spring-boot:run"]