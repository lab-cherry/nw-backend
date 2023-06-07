FROM eclipse-temurin:17.0.7_7-jdk AS builder
LABEL lab.cherry.nw.authors="https://github.com/lab-cherry"

COPY gradlew .
COPY gradle gradle
COPY build.gradle .
COPY settings.gradle .
COPY nw nw
RUN chmod +x ./gradlew
RUN ./gradlew bootJar

FROM eclipse-temurin:17.0.7_7-jdk
COPY --from=builder nw/build/libs/*-SNAPSHOT.jar app.jar

EXPOSE 8888
ENTRYPOINT ["java ${JAVA_OPTS} -jar /app.jar ${0} ${@}"]