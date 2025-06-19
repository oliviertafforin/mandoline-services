# Étape 1 : Build de l'application
FROM eclipse-temurin:23-jdk-alpine AS build

WORKDIR /app

# Copie du projet (exclut les fichiers ignorés par .dockerignore)
COPY . .

# Build du jar exécutable (avec Maven Wrapper)
RUN ./mvnw clean package -DskipTests

# Étape 2 : Image finale, allégée
FROM eclipse-temurin:23-jre-alpine

# Répertoire de l'app
WORKDIR /app

# Copier uniquement le JAR généré
COPY --from=build /app/target/mandoline-0.0.1-SNAPSHOT.jar app.jar

# Exposer le port de l'application Spring Boot
EXPOSE 8080

# Commande de démarrage
ENTRYPOINT ["java","-agentlib:jdwp=transport=dt_socket,server=y,suspend=n,address=*:5005", "-Dspring.profiles.active=docker", "-jar", "app.jar"]
