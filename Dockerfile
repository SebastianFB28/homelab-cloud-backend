# Etapa 1: Construcción (Build)
FROM maven:3.9.6-eclipse-temurin-21-alpine AS build
WORKDIR /app
# Copiamos el pom y descargamos dependencias (esto optimiza la caché de Docker)
COPY pom.xml .
RUN mvn dependency:go-offline
# Copiamos el código fuente y generamos el .jar
COPY src ./src
RUN mvn clean package -DskipTests

# Etapa 2: Ejecución (Runtime)
FROM eclipse-temurin:21-jre-alpine
WORKDIR /app
# Copiamos solo el archivo JAR resultante de la etapa anterior
COPY --from build /app/target/*.jar app.jar

# Definimos variables de entorno por defecto (vacías o genéricas)
ENV DB_URL=jdbc:postgresql://postgres-db:5432/homelab_db
ENV DB_USERNAME=postgres
ENV DB_PASSWORD=password

# Exponemos el puerto
EXPOSE 8080

# Comando para arrancar la aplicación
ENTRYPOINT ["java", "-jar", "app.jar"]