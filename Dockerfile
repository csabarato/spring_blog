FROM maven:3.9.6-sapmachine-17
WORKDIR /spring_blog
ARG JAR_FILE=target/spring_blog-0.0.1-SNAPSHOT.jar
COPY ${JAR_FILE} application.jar
ENTRYPOINT ["java", "-jar", "application.jar"]