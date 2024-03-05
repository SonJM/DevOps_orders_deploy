FROM openjdk:11 as stage1

WORKDIR /app

# gradle 빌드시에 필요한 파일들을 workdir로 copy
COPY gradlew .
COPY gradle gradle
COPY build.gradle .
COPY settings.gradle .
COPY src src

# Gradle Wrapper 스크립트에 실행 권한을 부여
RUN chmod +x gradlew

# /app/build/libs/*.jar
RUN ./gradlew bootJar

FROM openjdk:11

WORKDIR /app

# 빌드된 JAR 파일을 복사
COPY --from=stage1 /app/build/libs/*.jar app.jar

# 애플리케이션 실행
ENTRYPOINT [ "java", "-jar", "app.jar" ]

# FROM openjdk:11 as stage1

# WORKDIR /app

# # gradle 빌드시에 필요한 파일들을 workdir로 copy
# COPY gradlew .
# COPY gradle gradle
# COPY build.gradle .
# COPY settings.gradle .
# COPY src src

# RUN apt-get update && \
#     apt-get install -y dos2unix

# # Gradle Wrapper 스크립트에 실행 권한을 부여하고 EOL 문자를 LF로 변경
# RUN chmod +x gradlew && \
#     dos2unix gradlew

# # Gradle을 사용하여 애플리케이션 빌드
# RUN ./gradlew bootJar

# FROM openjdk:11

# WORKDIR /app

# # 빌드된 JAR 파일을 복사
# COPY --from=stage1 /app/build/libs/*.jar app.jar

# # 애플리케이션 실행
# ENTRYPOINT [ "java", "-jar", "app.jar" ]
