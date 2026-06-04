# FilmAPI - Client (React Frontend)

Este es el cliente frontend de la aplicación web FilmAPI, desarrollado con React, Vite y Tailwind CSS. La aplicación permite a los usuarios buscar películas y series (consumiendo la API pública Watchmode) y gestionar favoritos conectándose con el backend.

## Requisitos previos
- Node.js (v18+)
- npm o yarn

## Variables de entorno
Antes de arrancar, genera o crea en la raíz del proyecto un archivo `.env` o `.env.local` con el siguiente contenido:

```env
VITE_WATCHMODE_API_KEY=tu_clave_de_api_aqui
```

## Instalación
Para instalar dependencias, ejecuta:
```bash
npm install
```

## Inicio de la Aplicación
Para iniciar el proyecto en modo desarrollo conectado a Vite (con Fast Refresh):
```bash
npm run dev
```

La aplicación se iniciará por defecto y estará diponible tras compilar.

## Compilación (Producción)
Para construir el empaquetado final listo para producción (build):
```bash
npm run build
```
