# FilmAPI - Backend (Spring Boot)

Este es el proyecto backend de **FilmAPI**, desarrollado con **Spring Boot 3.5.6** y **Java 17**. Provee una API REST para gestionar usuarios, títulos, favoritos y reseñas, además de seguridad JWT y endpoints de Actuator.

## Requisitos previos
- **Java 17** (o superior) instalado y configurado en `PATH`.
- **Maven** (se incluye con la instalación de Java o se puede usar la wrapper `mvnw`).
- **MySQL** 8.x corriendo en `localhost:3306`.
- Credenciales de MySQL (por defecto `root/root`). Puedes modificarlas en `src/main/resources/application.yaml`.

## Configuración de la base de datos
1. Crea la base de datos (si no existe):
   ```sql
   CREATE DATABASE IF NOT EXISTS watchlist CHARACTER SET utf8mb4 COLLATE utf8mb4_unicode_ci;
   ```
2. Ejecuta el script de esquema:
   ```bash
   mysql -u root -p watchlist < src/main/resources/db/schema.sql
   ```
3. Opcionalmente, inserta datos de prueba:
   ```bash
   mysql -u root -p watchlist < src/main/resources/db/insert.sql
   ```
   > **Nota:** Si la tabla `roles` ya existía sin la columna `id`, elimina la base de datos y vuelve a crearla para evitar errores de clave foránea.

## Variables de entorno
- `WATCHMODE_API_KEY`: clave de la API pública de Watchmode.
- `JWT_SECRET`: secreto usado para firmar los tokens JWT.

Puedes definirlas en un archivo `.env` o exportarlas en la consola antes de iniciar la aplicación.

## Compilación y ejecución
```bash
# Desde la raíz del proyecto (Trabajo_Junieth_Orozco.S)
./mvnw spring-boot:run
```
El servidor arrancará en **http://localhost:8080**.

## Endpoints principales
- **/auth/** – login y registro.
- **/titles/** – búsqueda y gestión de favoritos.
- **/reviews/** – CRUD de reseñas.
- **/actuator/** – monitorización (health, metrics, etc.).
- **/swagger-ui/index.html** – documentación interactiva de la API (Swagger UI).

## Seguridad
- JWT se gestiona mediante la clase `JwtUtils`. El secreto se lee de `application.yaml` o la variable `JWT_SECRET`.
- Los endpoints de Actuator están expuestos sin autenticación para facilitar pruebas.

## Tests
```bash
./mvnw test
```

## Empaquetado
```bash
./mvnw clean package
```
Genera el archivo `target/filmapi-0.0.1-SNAPSHOT.jar` listo para producción.

---
**¡Listo!** Con estos pasos deberías poder ejecutar y probar la API sin problemas. Si necesitas volver a generar el ZIP de entrega, ejecuta el script de empaquetado que se incluye a continuación.
