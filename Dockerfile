# Usamos una imagen oficial de Java ligera
FROM eclipse-temurin:17-jdk-alpine

# Directorio de trabajo dentro del contenedor
WORKDIR /app

# Copiamos todos los archivos del proyecto actual
COPY . .

# Si tu proyecto compila con Maven, esta línea compila el paquete jar
RUN ./mvnw clean package -DskipTests

# Comando para ejecutar tu archivo JAR compilado por consola
CMD ["java", "-jar", "target/Optica-Proyect-1.0-SNAPSHOT.jar"]