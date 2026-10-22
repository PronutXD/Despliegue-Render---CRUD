# Paso 1: Usamos una imagen de Maven con Java 17 para compilar
FROM maven:3.9.6-eclipse-temurin-17-alpine AS build
WORKDIR /app

# Copiamos todo el código fuente al contenedor
COPY . .

# Compilamos forzando el uso de Java 17 e ignorando los tests
RUN mvn clean package -Dmaven.compiler.source=17 -Dmaven.compiler.target=17 -Dmaven.compiler.release=17 -DskipTests

# Paso 2: Creamos la imagen final ligera para ejecutar la aplicación
FROM eclipse-temurin:17-jre-alpine
WORKDIR /app

# Copiamos el JAR compilado desde la etapa anterior
COPY --from=build /app/target/Optica-Proyect-1.0-SNAPSHOT.jar /app/app.jar

# Comando para ejecutar el programa por consola
CMD ["java", "-jar", "app.jar"]