# Sistema de Reservas de Hotel — Bay Harbour Suites

## Descripción
Sistema web para gestionar reservas, clientes y habitaciones en un hotel. Incluye roles de usuario y administrador, gestión de reservas y notificaciones simuladas.

## Tecnologías Utilizadas
- Lenguaje: Java 17
- Framework: Spring Boot 3.2
- Base de datos: MySQL 8.0
- Vistas: Thymeleaf
- Gestión de dependencias: Maven
- Frontend: Bootstrap 5.3

## Patrones de Diseño Implementados
- **Creacional:** Singleton - `patterns/creational/DatabaseConnectionSingleton.java`
- **Estructural:** Facade - `patterns/structural/ReservaFacade.java`
- **Comportamiento:** Observer - `patterns/behavioral/ReservaSubject.java` y `EmailNotificationObserver.java`

## Requisitos Previos
- Java 17+
- Maven 3.6+
- MySQL 8.0+
- Navegador moderno

## Instalación y Ejecución
```bash
# Ejecutar script de instalación
./ejecutar.sh

Esto creará la base de datos, insertará datos iniciales y ejecutará la aplicación.

La aplicación estará disponible en: http://localhost:8080

Uso Básico

Administrador: puede gestionar habitaciones, clientes y reservas.

Usuario normal: puede ver habitaciones disponibles y crear reservas.

Estructura del Proyecto
Hotel/
├── src/main/java/com/hotel/
│   ├── controllers/
│   ├── services/
│   ├── repositories/
│   ├── models/
│   ├── patterns/
│   └── utils/
├── src/main/resources/
│   └── templates/
├── database/
│   ├── schema.sql
│   └── data.sql
├── ejecutar.sh
├── README.md
└── pom.xml

Autor
Oli — Grupo IDGS71N