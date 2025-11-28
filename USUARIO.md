
---
```markdown

# Manual de Usuario — Sistema de Reservas

## Introducción
Este manual explica cómo usar el sistema de reservas del hotel Bay Harbour Suites.

## Instalación Sencilla
Ejecuta el script `ejecutar.sh` desde la raíz del proyecto. Este script:
- Crea la base de datos `hotel_db`
- Inserta datos iniciales
- Configura credenciales
- Arranca la aplicación

Luego abre: `http://localhost:8080`

---

## Login y Roles
- **Administrador (ADMIN)**
  - Email: `admin@hotel.com`
  - Contraseña: `admin123`
  - Puede gestionar habitaciones, clientes y reservas
- **Usuario (USER)**
  - Email: `user@hotel.com`
  - Contraseña: `user123`
  - Puede ver habitaciones y crear reservas

---

## Funcionalidades Principales

### Habitaciones
- Crear, editar, eliminar y listar
- Tipos: Simple, Doble, Suite
- Disponibilidad

### Clientes
- Crear y editar información de contacto
- Validación de email único

### Reservas
- Crear reservas con fechas y método de pago
- Límite de 3 reservas activas por cliente
- Cálculo automático de total y descuentos
- Estados: Pendiente, Confirmada, Cancelada
- Comentarios disponibles solo en reservas Confirmadas
- Notificaciones simuladas en consola

---

## Casos de Uso Básicos
1. Consultar habitaciones disponibles
2. Crear reserva
3. Administrar clientes y habitaciones (solo ADMIN)
4. Agregar comentario a reserva confirmada

---

## Problemas Comunes y Solución
- **No inicia sesión:** revisar correo, contraseña y rol
- **Error de conexión MySQL:** verificar credenciales en `application.properties` o ejecutar `ejecutar.sh`
- **Puerto 8080 ocupado:** cambiar puerto en `application.properties` o cerrar proceso

---

## Notas
- Los usuarios predefinidos se crean automáticamente si no existen
- Contraseñas cifradas con BCrypt
- Descuentos automáticos: 3+ días → 10%, 7+ días → 15%
