# Sistema de Reservas de Hotel

Sistema completo de gestión de reservas de hotel desarrollado con Spring Boot, MySQL y Thymeleaf.

## 🚀 Características

- ✅ CRUD completo para Habitaciones, Reservas y Clientes
- ✅ Persistencia en base de datos MySQL
- ✅ Manejo de errores y validaciones
- ✅ Interfaz web funcional con Thymeleaf
- ✅ Notificaciones a clientes al crear reservas (Patrón Observer)
- ✅ Diferentes tipos de habitaciones y estados de reserva
- ✅ Diferentes métodos de pago y descuentos automáticos
- ✅ Comentarios en reservas completadas
- ✅ Límite de 3 reservas activas por cliente

## 📋 Requisitos Previos

- Java 17 o superior
- Maven 3.6+
- MySQL 8.0+
- IDE (IntelliJ IDEA, Eclipse, VS Code)

## 🛠️ Instalación y Configuración

### 1. Configurar Base de Datos

Crea la base de datos MySQL ejecutando los scripts en la carpeta `database/`:

```bash
mysql -u root -p < database/schema.sql
mysql -u root -p < database/data.sql
```

O ejecuta los scripts manualmente en tu cliente MySQL.

### 2. Configurar Credenciales

Edita el archivo `src/main/resources/application.properties` y ajusta las credenciales de MySQL si es necesario:

```properties
spring.datasource.username=root
spring.datasource.password=tu_password
```

### 3. Compilar y Ejecutar

```bash
# Compilar el proyecto
mvn clean install

# Ejecutar la aplicación
mvn spring-boot:run
```

La aplicación estará disponible en: `http://localhost:8080`

## 📁 Estructura del Proyecto

```
Hotel/
├── src/
│   ├── main/
│   │   ├── java/com/hotel/
│   │   │   ├── models/          # Entidades JPA
│   │   │   ├── controllers/     # Controladores web
│   │   │   ├── services/        # Lógica de negocio
│   │   │   ├── repositories/    # Repositorios JPA
│   │   │   ├── patterns/        # Patrones de diseño
│   │   │   │   ├── creational/  # Singleton
│   │   │   │   ├── structural/  # Facade
│   │   │   │   └── behavioral/  # Observer
│   │   │   ├── utils/           # Utilidades
│   │   │   └── config/          # Configuraciones
│   │   └── resources/
│   │       ├── templates/       # Vistas Thymeleaf
│   │       └── application.properties
│   └── test/                    # Pruebas
├── database/
│   ├── schema.sql              # Script de creación de tablas
│   └── data.sql                # Datos iniciales
├── pom.xml
└── README.md
```

## 🎯 Funcionalidades Implementadas

### Habitaciones
- Crear, editar, eliminar y listar habitaciones
- Tipos: Simple, Doble, Suite
- Control de disponibilidad

### Clientes
- CRUD completo de clientes
- Validación de email único
- Gestión de información de contacto

### Reservas
- Crear reservas con validaciones automáticas
- Cálculo automático de totales y descuentos
- Límite de 3 reservas activas por cliente
- Estados: Pendiente, Confirmada, Cancelada
- Métodos de pago: Efectivo, Tarjeta de Crédito, Tarjeta de Débito, Transferencia Bancaria
- Comentarios en reservas confirmadas
- Notificaciones automáticas al crear reservas

## 🏗️ Patrones de Diseño Implementados

### 1. Singleton (Creacional)
- **Clase**: `DatabaseConnectionSingleton`
- **Ubicación**: `patterns/creational/`
- **Propósito**: Garantizar una única instancia de conexión a la base de datos

### 2. Facade (Estructural)
- **Clase**: `ReservaFacade`
- **Ubicación**: `patterns/structural/`
- **Propósito**: Simplificar operaciones complejas de reservas (cálculo de totales, descuentos, validaciones)

### 3. Observer (Comportamiento)
- **Clases**: `ReservaSubject`, `EmailNotificationObserver`
- **Ubicación**: `patterns/behavioral/`
- **Propósito**: Notificar automáticamente a los clientes cuando se crea una reserva

## 📊 Base de Datos

### Tablas

**habitaciones**
- id, numero (único), tipo, precio, disponible

**clientes**
- id, nombre, email (único), telefono

**reservas**
- id, cliente_id (FK), habitacion_id (FK), fecha_inicio, fecha_fin, estado, metodo_pago, descuento, total, comentario

## 🔒 Reglas de Negocio

1. **Límite de Reservas Activas**: Un cliente no puede tener más de 3 reservas activas (Pendiente o Confirmada) simultáneamente
2. **Descuentos Automáticos**: 
   - 15% para estadías de 7+ días
   - 10% para estadías de 3+ días
3. **Comentarios**: Solo se pueden agregar a reservas con estado "Confirmada"
4. **Notificaciones**: Se envían automáticamente al crear una reserva (simulado en consola)

## 🧪 Pruebas

Para ejecutar las pruebas:

```bash
mvn test
```

## 📝 Uso del Sistema

1. **Acceder al sistema**: Navega a `http://localhost:8080`
2. **Gestionar Habitaciones**: Crea y administra las habitaciones disponibles
3. **Gestionar Clientes**: Registra los clientes del hotel
4. **Crear Reservas**: 
   - Selecciona cliente y habitación
   - Define fechas de inicio y fin
   - Elige método de pago
   - El sistema calcula automáticamente el total y aplica descuentos
5. **Agregar Comentarios**: Una vez confirmada una reserva, puedes agregar comentarios

## 🐛 Solución de Problemas

### Error de conexión a MySQL
- Verifica que MySQL esté ejecutándose
- Confirma las credenciales en `application.properties`
- Asegúrate de que la base de datos `hotel_db` exista

### Puerto 8080 ocupado
- Cambia el puerto en `application.properties`: `server.port=8081`

## 👨‍💻 Tecnologías Utilizadas

- **Java 17**
- **Spring Boot 3.2.0**
- **Spring Data JPA**
- **Spring Web MVC**
- **Thymeleaf**
- **MySQL 8.0**
- **Maven**
- **Bootstrap 5.3**

## 📄 Licencia

Este proyecto es de código abierto y está disponible para uso educativo.

---

## 📚 Especificaciones Originales del Proyecto

El proyecto fue generado según las siguientes especificaciones:

---

## Lenguaje y Framework
- Java con Spring Boot
- Maven para dependencias

---

## Funcionalidades mínimas
- CRUD completo para Habitaciones, Reservas y Clientes
- Persistencia en base de datos MySQL
- Manejo de errores y validaciones
- Interfaz web funcional con Thymeleaf
- Notificaciones a clientes al crear reservas (Observer)
- Diferentes tipos de habitaciones y estados de reserva
- Diferentes métodos de pago y descuentos
- Permitir agregar **comentarios** a la reserva una vez completada
- Cada cliente puede tener **máximo 3 reservas activas simultáneamente**

---

## Patrones de diseño
- **Creacional:** Singleton (conexión a base de datos)
- **Estructural:** Facade (para simplificar operaciones de reservas)
- **Comportamiento:** Observer (para notificaciones de reservas)

---

## Estructura del proyecto
/proyecto
│
├── /src
│ ├── /models
│ ├── /controllers
│ ├── /views
│ ├── /services
│ ├── /patterns
│ │ ├── /creational
│ │ ├── /structural
│ │ └── /behavioral
│ └── /utils
├── /docs
├── /database
│ ├── schema.sql
│ └── data.sql
├── /tests
├── README.md
└── pom.xml

yaml
Copier le code

---

## Base de datos: `hotel_db`

### Tablas

**habitaciones**
- id INT PRIMARY KEY AUTO_INCREMENT
- numero VARCHAR(10) UNIQUE NOT NULL
- tipo VARCHAR(50) NOT NULL (Simple, Doble, Suite)
- precio DECIMAL(10,2) NOT NULL
- disponible BOOLEAN DEFAULT TRUE

**clientes**
- id INT PRIMARY KEY AUTO_INCREMENT
- nombre VARCHAR(100) NOT NULL
- email VARCHAR(100) UNIQUE NOT NULL
- telefono VARCHAR(20) NOT NULL

**reservas**
- id INT PRIMARY KEY AUTO_INCREMENT
- cliente_id INT NOT NULL (FK -> clientes.id)
- habitacion_id INT NOT NULL (FK -> habitaciones.id)
- fecha_inicio DATE NOT NULL
- fecha_fin DATE NOT NULL
- estado VARCHAR(20) NOT NULL (Pendiente, Confirmada, Cancelada)
- metodo_pago VARCHAR(50) NOT NULL
- descuento DECIMAL(5,2) DEFAULT 0
- total DECIMAL(10,2) NOT NULL
- comentario TEXT NULL
- Restricción: un cliente solo puede tener **3 reservas activas simultáneamente**

---

## Scripts iniciales

**schema.sql**: crear las tablas anteriores con relaciones y restricciones de integridad  
**data.sql**: insertar al menos 3 habitaciones, 2 clientes y 2 reservas iniciales

---

## Reglas de negocio adicionales
1. Notificar al cliente automáticamente al crear una reserva (Observer).  
2. Usar Facade para simplificar la creación de reservas y cálculos de total, descuentos y métodos de pago.  
3. Validar antes de crear reserva que un cliente no tenga más de 3 reservas activas.  
4. Permitir agregar comentarios solo cuando la reserva esté completada (estado Confirmada).  

---

## Documentación
- Diagramas UML
- Manual de usuario
- Justificación de patrones

---

Por favor, genera el proyecto **totalmente completo y funcional**, listo para ejecutar con `mvn spring-boot:run`, con todos los CRUDs, patrones implementados, validaciones, comentarios y límites de reservas activas.