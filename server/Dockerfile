FROM eclipse-temurin:21-jdk-alpine AS builder
WORKDIR /build
COPY build.gradle settings.gradle gradlew ./
COPY gradle gradle
RUN ./gradlew dependencies --no-daemon

COPY . .
RUN ./gradlew clean build -x test --no-daemon

FROM eclipse-temurin:21-jre-alpine
WORKDIR /server
COPY --from=builder /build/build/libs/server.jar server.jar

ENTRYPOINT ["java", "-Xmx512m", "-jar", "server.jar"]
