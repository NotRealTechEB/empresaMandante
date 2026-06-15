# 1. Usamos una imagen base con JDK 25 para compilar y ejecutar
FROM eclipse-temurin:25-jdk-jammy AS build

# 2. Establecemos el directorio de trabajo
WORKDIR /app

# 3. Copiamos solo los archivos de Gradle primero para aprovechar el caché
COPY gradlew .
COPY gradle gradle
COPY build.gradle .
COPY settings.gradle .

# 4. Descargamos las dependencias (esto acelera futuras compilaciones)
# Usamos --no-daemon para entornos CI/CD y aseguramos que baje el toolchain
RUN chmod +x gradlew && ./gradlew dependencies --no-daemon

# 5. Copiamos el código fuente y compilamos
COPY src src
RUN ./gradlew build -x check -x test -Pproduction --no-daemon

# 6. Imagen final (ligera) para correr la aplicación
FROM eclipse-temurin:25-jre-jammy
WORKDIR /app

# 7. Copiamos el JAR generado desde la etapa anterior
# Ajusta 'tu-proyecto' al nombre real de tu jar, o usa build/libs/*.jar
COPY --from=build /app/build/libs/*.jar app.jar

# 8. Puerto donde corre Spring Boot
EXPOSE 8080

# 9. Comando para ejecutar
ENTRYPOINT ["java", "-jar", "app.jar"]