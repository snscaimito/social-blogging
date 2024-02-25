FROM openjdk:21

ARG VERSION

WORKDIR /app
COPY target/social-blogging-${VERSION}.jar /app/social-blogging.jar
EXPOSE 8080

CMD java -jar /app/social-blogging.jar
