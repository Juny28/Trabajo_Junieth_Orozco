# 🎬 FilmAPI - Descubre tu próxima historia

![Version](https://img.shields.io/badge/version-1.0.0-blue.svg)
![Spring Boot](https://img.shields.io/badge/backend-Spring%20Boot%203.5-green.svg)
![React](https://img.shields.io/badge/frontend-React%2018-61DAFB.svg)
![License](https://img.shields.io/badge/license-MIT-yellow.svg)

**FilmAPI** es una plataforma integral para los amantes del cine y las series. Permite explorar un catálogo inmenso de títulos gracias a la integración con la API de **Watchmode**, gestionar una lista personalizada de favoritos y escribir reseñas. 

Este proyecto es el resultado de un Trabajo de Fin de Grado (TFG) enfocado en la integración de microservicios y una experiencia de usuario moderna.

---

## 🚀 Características Principales

-   🔍 **Búsqueda Inteligente**: Encuentra cualquier película o serie en tiempo real.
-   🔥 **Tendencias Actualizadas**: Visualiza los títulos más populares del momento directamente desde Watchmode API.
-   ❤️ **Gestión de Favoritos**: Guarda tus historias imprescindibles en tu perfil personal.
-   ✍️ **Reseñas de Usuarios**: Comparte tu opinión y lee lo que otros usuarios piensan.
-   🔐 **Seguridad Avanzada**: Autenticación robusta mediante **JWT (JSON Web Tokens)**.
-   📱 **Diseño Premium**: Interfaz moderna, responsiva y con animaciones fluidas.

---

## 🛠️ Tecnologías

### Backend
-   **Java 17** & **Spring Boot 3.5.6**
-   **Spring Security** + **JWT**
-   **Spring Data JPA** (MySQL 8.x)
-   **Maven** para la gestión de dependencias.
-   **JUnit 5** & **Mockito** para tests unitarios.

### Frontend
-   **React 18** + **Vite**
-   **Tailwind CSS** para un diseño elegante.
-   **Lucide React** para iconografía moderna.
-   **React Query** para una gestión eficiente de datos asíncronos.

---

## 📦 Estructura del Proyecto

El repositorio está dividido en dos grandes bloques:

-   📂 [**Trabajo_Junieth_Orozco.S**](./Trabajo_Junieth_Orozco.S): Corazón de la aplicación (Backend).
-   📂 [**Trabajo_Junieth_Orozco.C**](./Trabajo_Junieth_Orozco.C): Interfaz de usuario (Frontend).

---

## ⚙️ Instalación y Configuración

### 1. Requisitos Previos
Asegúrate de tener instalado:
-   Java 17+
-   MySQL 8.x
-   Node.js 18+

### 2. Configuración de la Base de Datos
```sql
CREATE DATABASE watchlist;
```
Importa los scripts de `src/main/resources/db/` en tu instancia de MySQL.

### 3. Variables de Entorno
Necesitarás una API Key de [Watchmode](https://api.watchmode.com/). Configúrala en:
-   **Backend**: `application.yaml` o variable `WATCHMODE_API_KEY`.
-   **Frontend**: Archivo `.env` como `VITE_WATCHMODE_API_KEY`.

---

## 🏃 Ejecución

### Iniciar Backend
```bash
cd Trabajo_Junieth_Orozco.S
./mvnw spring-boot:run
```

### Iniciar Frontend
```bash
cd Trabajo_Junieth_Orozco.C
npm install
npm run dev
```

---
## 🏃 Ejecución y Comandos

### Backend (Java/Spring Boot)
Ubícate en la carpeta `Trabajo_Junieth_Orozco.S/`:

- **Iniciar Servidor**:
  ```bash
  ./mvnw spring-boot:run
  ```
- **Ejecutar Tests (Mockito)**:
  ```bash
  ./mvnw test
  ```
- **Generar Reporte de Cobertura (JaCoCo)**:
  ```bash
  ./mvnw jacoco:report
  ```
  *El reporte se genera en `target/site/jacoco/index.html`.*

### Frontend (React/Vite)
Ubícate en la carpeta `Trabajo_Junieth_Orozco.C/`:

- **Instalar Dependencias**:
  ```bash
  npm install
  ```
- **Iniciar en Desarrollo**:
  ```bash
  npm run dev
  ```

---

## 🧪 Calidad del Código (TFG Metrics)

Contamos con una arquitectura sólida y verificada:
- ✅ **Cobertura de Código**: **80.6%** (Línea final alcanzada a través de Mockito).
- ✅ **Documentación**: Javadoc y Swagger integrados.
- ✅ **GitFlow**: Flujo de trabajo profesional en ramas `main` y `develop`.

---

Desarrollado con ❤️ por **Junieth Orozco**.
