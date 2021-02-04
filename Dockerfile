FROM openjdk:8u272-jre

COPY ./build/libs/lw-api-tester-0.0.1-SNAPSHOT.jar      /usr/app/api-tester.jar

WORKDIR /usr/app

EXPOSE 8080

HEALTHCHECK --interval=1s --timeout=1s --start-period=60s --retries=3 \
            CMD curl -f http://127.0.0.1:8080/actuator/health || exit 1

ENTRYPOINT ["java", "-jar", "api-tester.jar"]