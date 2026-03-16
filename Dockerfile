FROM maven:3.9.14-eclipse-temurin-21
WORKDIR /app
CMD ["mvn", "spring-boot:run"]

#Bind Mounts


