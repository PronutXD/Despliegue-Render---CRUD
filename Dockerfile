# Paso 1: Usamos una imagen con Maven y Java 17 para compilar el proyecto
FROM maven:3.9.6-eclipse-temurin-17-alpine AS build
WORKDIR /app

# Copiamos todo el código fuente al contenedor
COPY . .

# Compilamos el proyecto y generamos el archivo JAR ignorando los tests
RUN mvn clean package -DskipTests

# Paso 2: Creamos la imagen final ligera para ejecutar la aplicación
FROM eclipse-temurin:17-jdk-alpine
WORKDIR /app

# Copiamos el JAR compilado desde la etapa anterior
COPY --from=build /app/target/Optica-Proyect-1.0-SNAPSHOT.jar /app/app.jar

# Comando para ejecutar el programa por consola
CMD ["java", "-jar", "app.jar"]