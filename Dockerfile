FROM java:8-alpine
MAINTAINER Your Name <you@example.com>

ADD target/uberjar/clo.jar /clo/app.jar

EXPOSE 3000

CMD ["java", "-jar", "/clo/app.jar"]
