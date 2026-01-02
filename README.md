# Spot the Impostor - Backend API 🕵️‍♂️

Este repositorio contiene el núcleo lógico y la API REST del proyecto **Spot the Impostor**. La aplicación está construida con **Java** y **Spring Boot**, diseñada bajo una arquitectura limpia y robusta para gestionar la autenticación de usuarios y la lógica de negocio.

## 🛠️ Tecnologías y Herramientas

* **Lenguaje:** Java 21
* **Framework:** Spring Boot
* **Seguridad:** Spring Security con BCrypt para hashing de contraseñas.
* **Persistencia:** Spring Data JPA + Hibernate.
* **Base de Datos:** PostgreSQL (Alojada en Aiven Cloud).
* **Documentación/Pruebas:** Postman.

## 📁 Estructura del Proyecto

El proyecto sigue una organización por paquetes basada en responsabilidades:

```text
src/main/java/com/spotimpostor/spotimpostor
├── config/       # Configuraciones de seguridad y beans globales
├── controller/   # Endpoints de la API REST
├── domain/       # Entidades JPA y modelos de dominio
│   ├── entity/
│   └── model/
├── dto/          # Objetos de Transferencia de Datos (Requests y Responses)
│   ├── mapper/
│   ├── request/
│   └── response/
├── exception/    # Manejo Global de Excepciones y clases personalizadas
├── manager/      # Lógica de orquestación (opcional)
├── repository/   # Interfaces de acceso a datos (Spring Data JPA)
├── service/      # Capa de lógica de negocio
└── util/         # Clases de utilidad (Generadores, Respuestas estándar)
```

## ✨ Características Principales

* **Autenticación Segura:** Registro e inicio de sesión utilizando el correo electrónico como identificador único.
* **Gestión de Perfil:** Endpoints dedicados para la actualización de nombre de usuario y cambio de contraseña con validación de seguridad.
* **Manejo Global de Errores:** Implementación de un GlobalExceptionHandler que captura errores específicos (Conflict, NotFound, BadRequest) y devuelve mensajes claros al cliente.
* **Estandarización de Respuestas:** Todas las respuestas de la API siguen el formato ApiResponse, garantizando consistencia en el Frontend.
* **UUID:** Uso de identificadores únicos universales para mayor seguridad en los recursos del sistema.