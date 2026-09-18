# 👤 User Service (Proyecto de Práctica - Gestión de Usuarios)

¡Hola! Este es un proyecto que desarrollé con el objetivo de **aprender y practicar los fundamentos del desarrollo Backend y la separación de servicios**. Es un microservicio inicial que se encarga del registro y la validación de sesiones de usuarios, simulando la puerta de entrada a un sistema de control de almacén.

## 🎯 Mis Objetivos de Aprendizaje con este Proyecto
Como desarrollador Junior, utilicé este repositorio para desafiarme a mí mismo y aprender:
* Cómo separar las responsabilidades de una aplicación en servicios independientes.
* El manejo de peticiones HTTP (`POST`) y respuestas en formato JSON.
* La configuración y conexión de una base de datos embebida para pruebas rápidas.

## 🛠️ Tecnologías y Herramientas utilizadas
* **Lenguaje:** Java 21
* **Framework:** Spring Boot
* **Persistencia:** Spring Data JPA
* **Base de Datos:** H2 (En memoria, ideal para probar el proyecto de inmediato sin configurar servidores externos)
* **Gestor de dependencias:** Maven

## 🌐 Endpoints de la API (Rutas)
El servicio expone dos rutas principales para interactuar desde el navegador o herramientas como Postman:
1. `POST /api/users/register`: Registra un nuevo usuario en la base de datos H2.
2. `POST /api/users/login`: Valida si las credenciales coinciden para simular el inicio de sesión.

## 🔗 Integración y Flujo Local
Este proyecto fue diseñado para conectarse de manera local y experimental con mi otro repositorio asociado (`product-service` que corre en el puerto 8080). 

Al realizar un login exitoso en este servicio (puerto 8081), la aplicación captura el ID del usuario y realiza una redirección hacia la interfaz de inventario (`http://localhost:8080/productos.html`). Es una prueba de concepto para entender cómo interactúan dos aplicaciones independientes en un entorno local.

---
*Nota: Este proyecto fue creado con fines netamente educativos para consolidar mis bases en el ecosistema de Spring Boot.*
