
# 👤 User Service (Microservicio de Gestión de Usuarios y Autenticación)

Este microservicio forma parte del sistema de control de almacén. Se encarga de la gestión de usuarios, registro y el proceso de autenticación (`Login`), sirviendo como puerta de entrada al ecosistema y validando las sesiones antes de permitir el acceso al panel de operaciones.

---

## 🚀 Características Principales
* **Registro de Usuarios:** Endpoints para la creación de cuentas de usuario (`/api/users/register`).
* **Autenticación (Login):** Validación de credenciales y retorno de identidad para control de sesión (`/api/users/login`).
* **Base de Datos Embebida (H2):** No requiere la instalación previa de motores de base de datos externos; los datos se gestionan en memoria para facilitar pruebas inmediatas.
* **Consola H2 Activa:** Permite visualizar y auditar las tablas de la base de datos en tiempo real.

---

## 🛠️ Tecnologías Utilizadas
* **Java** (JDK 21)
* **Spring Boot**
* **Spring Data JPA**
* **Base de Datos H2** (En memoria)
* **Maven** (Gestor de dependencias)

---

## 📂 Estructura del Proyecto Relacionada
Este proyecto funciona de manera independiente pero complementaria dentro de la arquitectura:

```text
Ecosistema de Microservicios:
├── 👤 user-service (Este repositorio - Puerto 8081)
└── 📦 product-service (Repositorio asociado - Puerto 8080)
⚙️ Configuración y Ejecución Local
1. Clonar el repositorio
Bash
git clone [https://github.com/tu-usuario/user-service.git](https://github.com/tu-usuario/user-service.git)
cd user-service
2. Verificar el puerto de ejecución
Por defecto, este servicio está configurado para correr en el puerto 8081 (para evitar conflictos con el microservicio de productos). Puedes verificarlo o modificarlo en el archivo src/main/resources/application.properties:

Properties
server.port=8081
3. Ejecutar la aplicación
Puedes compilar y arrancar el microservicio usando Maven Wrapper desde la terminal:

En Linux / macOS:

Bash
./mvnw spring-boot:run
En Windows:

Bash
mvnw.cmd spring-boot:run
🌐 Endpoints Principales

Método	Endpoint	        Descripción
POST	/api/users/register	Registra un nuevo usuario en el sistema.
POST	/api/users/login	Valida las credenciales de acceso y retorna los datos del usuario.
🔍 Consola H2 (Base de Datos)
Mientras el microservicio esté corriendo, puedes acceder a la consola web de H2 para revisar los registros almacenados:

URL: http://localhost:8081/h2-console

Nota: Asegúrate de verificar las credenciales de conexión JDBC especificadas en el application.properties de este proyecto.

🔗 Enlace con el Microservicio de Productos (product-service)
Este microservicio trabaja de la mano con el Product Service (que opera en el puerto 8080).

Al iniciar sesión correctamente en este portal (8081), la aplicación captura la credencial/ID del usuario y redirige de manera automática al panel de inventario ubicado en el otro microservicio (http://localhost:8080/productos.html).

Para probar el flujo completo de punta a punta, asegúrate de tener ambos microservicios corriendo simultáneamente en sus respectivos puertos.
```
Puedes consultar el repositorio del otro componente aquí: 👉 Enlace al [Repositorio de product-service] (https://github.com/Jariruna/product-service.git)
