# ETAPA 1: Construcción (Build)
# Usamos una imagen de Maven con Java 21 para compilar el proyecto
FROM maven:3.9.6-eclipse-temurin-21 AS build
WORKDIR /app

# Copiamos el archivo pom.xml y descargamos las dependencias (esto optimiza la caché)
COPY pom.xml .
RUN mvn dependency:go-offline

# Copiamos el código fuente y generamos el archivo ejecutable (.jar)
COPY src ./src
RUN mvn clean package -DskipTests

# ETAPA 2: Ejecución (Runtime)
# Usamos una imagen ligera de Java 21 para correr la app
FROM eclipse-temurin:21-jdk-alpine
WORKDIR /app

# Copiamos el .jar generado en la etapa anterior y lo renombramos a app.jar
COPY --from=build /app/target/*.jar app.jar

# Exponemos el puerto 8080
EXPOSE 8080

# Definimos el comando de inicio
ENTRYPOINT ["java", "-jar", "app.jar"]