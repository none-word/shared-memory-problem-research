FROM openjdk:17

WORKDIR /smp-module

COPY . .

EXPOSE 4501

RUN chmod +x mvnw
ENTRYPOINT ["./mvnw", "spring-boot:run"]