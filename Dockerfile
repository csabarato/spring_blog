FROM maven:3.9.6-sapmachine-17
WORKDIR /spring_blog
COPY . .
RUN mvn clean install -DskipTests
CMD mvn spring-boot:run