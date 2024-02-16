# Establecer la imagen base para la etapa de construcción
FROM maven:3.8.1-openjdk-17 AS build

# Copiar el directorio del proyecto al contenedor
COPY API_Rest-1 /app

# Cambiar al directorio de la aplicación
WORKDIR /app

# Construir la aplicación con Maven, omitiendo las pruebas unitarias
RUN mvn clean package -DskipTests

# Establecer la imagen base final
FROM openjdk:17-jdk-slim

# Exponer el puerto de la aplicación
EXPOSE 8080

# Copiar el archivo JAR de la primera imagen (etapa build) a la segunda (etapa final)
COPY --from=build /app/target/EjemploAPI-0.0.1-SNAPSHOT.jar app.jar

# Establecer el comando de entrada
ENTRYPOINT ["java", "-jar", "app.jar"]