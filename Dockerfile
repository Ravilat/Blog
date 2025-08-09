# Используем официальный образ с JDK 17 от Eclipse Temurin на базе Ubuntu Jammy
FROM eclipse-temurin:17-jdk-jammy

# Аргумент для указания, какой JAR файл копировать (можно поменять, если у тебя другой путь/имя)
ARG JAR_FILE=target/*.jar

# Копируем собранный jar из target в контейнер с именем app.jar
COPY ${JAR_FILE} app.jar

# Команда запуска контейнера — стартуем Spring Boot приложение
ENTRYPOINT ["java", "-jar", "/app.jar"]