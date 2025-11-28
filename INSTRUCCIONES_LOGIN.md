# 🔐 Instrucciones de Login y Registro

## Nuevas Funcionalidades Implementadas

### ✅ Sistema de Autenticación
- **Login**: Los usuarios deben iniciar sesión para acceder al sistema
- **Registro**: Los usuarios pueden crear una cuenta nueva
- **Roles**: Se implementaron dos roles:
  - **USER**: Usuarios normales que pueden ver y reservar habitaciones
  - **ADMIN**: Administradores que pueden gestionar todo el sistema

### ✅ Vistas por Rol

#### Para Usuarios (USER):
- **Carrusel de Habitaciones**: Vista con tarjetas en carrusel para seleccionar habitaciones
- **Formulario de Reserva**: Permite reservar habitaciones directamente desde el carrusel
- **Acceso restringido**: No pueden acceder a las vistas de administración

#### Para Administradores (ADMIN):
- **Vistas de Administración**: Acceso completo a:
  - Gestión de Habitaciones (CRUD)
  - Gestión de Clientes (CRUD)
  - Gestión de Reservas (CRUD)
- **Acceso restringido**: Solo admins pueden acceder a estas rutas

---

## 🚀 Cómo Probar el Sistema

### Paso 1: Actualizar la Base de Datos

Ejecuta los scripts actualizados:

```bash
mysql -u root -p < database/schema.sql
mysql -u root -p < database/data.sql
```

O ejecuta la aplicación y los usuarios se crearán automáticamente.

### Paso 2: Usuarios de Prueba

El sistema crea automáticamente dos usuarios:

**Administrador:**
- Email: `admin@hotel.com`
- Contraseña: `admin123`
- Rol: ADMIN

**Usuario Normal:**
- Email: `user@hotel.com`
- Contraseña: `user123`
- Rol: USER

### Paso 3: Probar como Administrador

1. Ve a `http://localhost:8080`
2. Click en "Iniciar Sesión"
3. Ingresa:
   - Email: `admin@hotel.com`
   - Contraseña: `admin123`
4. Serás redirigido a la vista de Habitaciones (panel de administración)
5. Puedes gestionar Habitaciones, Clientes y Reservas

### Paso 4: Probar como Usuario Normal

1. Cierra sesión (si estás logueado como admin)
2. Click en "Iniciar Sesión"
3. Ingresa:
   - Email: `user@hotel.com`
   - Contraseña: `user123`
4. Serás redirigido al carrusel de habitaciones
5. Puedes navegar por las habitaciones y reservar

### Paso 5: Crear Nuevo Usuario

1. Ve a `http://localhost:8080`
2. Click en "Registrarse"
3. Completa el formulario:
   - Nombre completo
   - Email (único)
   - Contraseña
   - Confirmar contraseña
   - **Checkbox "Quiero ser administrador"** (opcional)
4. Si marcas el checkbox, el usuario será ADMIN, si no, será USER

---

## 🎯 Flujo de Usuario Normal

1. **Login** → Usuario inicia sesión
2. **Carrusel de Habitaciones** → Ve habitaciones disponibles agrupadas por tipo (Simple, Doble, Suite)
3. **Seleccionar Habitación** → Click en "Reservar Ahora" en una tarjeta
4. **Formulario de Reserva** → Completa fechas y método de pago
5. **Confirmar** → La reserva se crea y se envía notificación

---

## 🛡️ Seguridad Implementada

- **Spring Security**: Autenticación y autorización
- **BCrypt**: Contraseñas encriptadas
- **Protección de Rutas**: 
  - `/habitaciones/**`, `/clientes/**`, `/reservas/**` → Solo ADMIN
  - `/habitaciones-disponibles/**` → USER y ADMIN
- **Redirección Automática**: Los usuarios son redirigidos según su rol al iniciar sesión

---

## 📝 Notas Importantes

1. **Primera Ejecución**: Los usuarios admin y user se crean automáticamente si no existen
2. **Contraseñas**: Las contraseñas se encriptan automáticamente con BCrypt
3. **Validación**: El sistema valida que las contraseñas coincidan en el registro
4. **Email Único**: No se pueden crear dos usuarios con el mismo email
5. **Cliente Automático**: Cuando un usuario normal hace una reserva, se crea automáticamente un cliente asociado si no existe

---

## 🔧 Solución de Problemas

### Error: "Access Denied"
- Verifica que estés logueado con el rol correcto
- Los usuarios normales no pueden acceder a rutas de admin

### Error: "Usuario no encontrado"
- Asegúrate de que el usuario exista en la base de datos
- Verifica que el email sea correcto

### No puedo ver el carrusel
- Verifica que estés logueado como USER o ADMIN
- Asegúrate de que haya habitaciones disponibles en la base de datos

---

## ✨ Características del Carrusel

- **Agrupación por Tipo**: Las habitaciones se muestran en carruseles separados por tipo
- **Navegación**: Flechas para navegar entre habitaciones del mismo tipo
- **Información Visible**: Número, tipo y precio por noche
- **Reserva Directa**: Botón "Reservar Ahora" en cada tarjeta

¡Disfruta del nuevo sistema de autenticación! 🎉

