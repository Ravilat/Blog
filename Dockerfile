# --- Stage 1: Build stage ---
FROM maven:3.9.4-eclipse-temurin-17 AS build

# Рабочая директория в контейнере
WORKDIR /app

# Копируем pom.xml и скачиваем зависимости (кэшируем этот слой)
COPY pom.xml .

RUN mvn dependency:go-offline

# Копируем исходники
COPY src ./src

# Собираем проект, пропуская тесты для ускорения (можно убрать -DskipTests, если нужны тесты)
RUN mvn clean package -DskipTests

# --- Stage 2: Run stage ---
FROM eclipse-temurin:17-jdk-jammy

WORKDIR /app

# Копируем собранный JAR из первого этапа в текущий
COPY --from=build /app/target/*.jar app.jar

# Запускаем приложение
ENTRYPOINT ["java", "-jar", "app.jar"]