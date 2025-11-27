# 🧪 Guía de Pruebas - Sistema de Reservas de Hotel

## 📋 Pasos para Probar el Programa

### Paso 1: Verificar Requisitos Previos

Asegúrate de tener instalado:
- ✅ Java 17 o superior
- ✅ Maven 3.6+
- ✅ MySQL 8.0+ ejecutándose

**Verificar Java:**
```bash
java -version
```

**Verificar Maven:**
```bash
mvn -version
```

**Verificar MySQL:**
```bash
mysql --version
# O intenta conectarte:
mysql -u root -p
```

---

### Paso 2: Configurar la Base de Datos MySQL

#### Opción A: Ejecutar Scripts Manualmente

1. **Conectarte a MySQL:**
```bash
mysql -u root -p
```

2. **Ejecutar el script de esquema:**
```bash
source /Users/oliveriswackk/Desktop/Repositorios/Hotel/database/schema.sql
```

3. **Ejecutar el script de datos:**
```bash
source /Users/oliveriswackk/Desktop/Repositorios/Hotel/database/data.sql
```

4. **Verificar que se crearon las tablas:**
```sql
USE hotel_db;
SHOW TABLES;
SELECT * FROM habitaciones;
SELECT * FROM clientes;
SELECT * FROM reservas;
```

#### Opción B: Usar la Configuración Automática

La aplicación está configurada para crear la base de datos automáticamente si no existe (`createDatabaseIfNotExist=true`). Sin embargo, es recomendable ejecutar los scripts primero para tener datos iniciales.

---

### Paso 3: Ajustar Credenciales de MySQL (si es necesario)

Edita el archivo `src/main/resources/application.properties`:

```properties
spring.datasource.username=root
spring.datasource.password=TU_PASSWORD_AQUI
```

Si tu MySQL no tiene contraseña o usa otra, cámbiala aquí.

---

### Paso 4: Compilar el Proyecto

Desde la raíz del proyecto (`/Users/oliveriswackk/Desktop/Repositorios/Hotel`):

```bash
mvn clean install
```

Esto descargará las dependencias y compilará el proyecto. La primera vez puede tardar varios minutos.

---

### Paso 5: Ejecutar la Aplicación

```bash
mvn spring-boot:run
```

O si prefieres usar el JAR compilado:

```bash
java -jar target/hotel-reservation-system-1.0.0.jar
```

**Deberías ver algo como:**
```
  .   ____          _            __ _ _
 /\\ / ___'_ __ _ _(_)_ __  __ _ \ \ \ \
( ( )\___ | '_ | '_| | '_ \/ _` | \ \ \ \
 \\/  ___)| |_)| | | | | || (_| |  ) ) ) )
  '  |____| .__|_| |_|_| |_\__, | / / / /
 =========|_|==============|___/=/_/_/_/
 :: Spring Boot ::                (v3.2.0)

...
Started HotelApplication in X.XXX seconds
```

---

### Paso 6: Acceder a la Aplicación

Abre tu navegador y ve a:

**http://localhost:8080**

Deberías ver la página de inicio del sistema.

---

## 🧪 Casos de Prueba

### Prueba 1: Verificar Datos Iniciales

1. Ve a **Habitaciones** → Deberías ver 5 habitaciones
2. Ve a **Clientes** → Deberías ver 3 clientes
3. Ve a **Reservas** → Deberías ver 3 reservas

### Prueba 2: CRUD de Habitaciones

1. **Crear una nueva habitación:**
   - Click en "Nueva Habitación"
   - Número: `401`
   - Tipo: `Suite`
   - Precio: `200.00`
   - Disponible: ✅
   - Guardar

2. **Editar una habitación:**
   - Click en "Editar" de cualquier habitación
   - Cambiar el precio
   - Guardar

3. **Eliminar una habitación:**
   - Click en "Eliminar"
   - Confirmar

### Prueba 3: CRUD de Clientes

1. **Crear un nuevo cliente:**
   - Click en "Nuevo Cliente"
   - Nombre: `Ana Martínez`
   - Email: `ana.martinez@email.com`
   - Teléfono: `555-0104`
   - Guardar

2. **Editar un cliente:**
   - Click en "Editar"
   - Modificar datos
   - Guardar

3. **Intentar crear cliente duplicado:**
   - Intentar crear otro cliente con el mismo email
   - Debería mostrar error

### Prueba 4: Crear Reserva (Funcionalidad Principal)

1. **Crear una reserva nueva:**
   - Click en "Nueva Reserva"
   - Cliente: Selecciona un cliente
   - Habitación: Selecciona una habitación disponible
   - Fecha Inicio: `2024-02-10`
   - Fecha Fin: `2024-02-15` (5 días)
   - Método de Pago: `Tarjeta de Crédito`
   - Descuento: (dejar vacío para descuento automático)
   - Click en "Crear Reserva"

2. **Verificar notificación:**
   - Revisa la consola donde ejecutaste la aplicación
   - Deberías ver un mensaje de email simulado

3. **Verificar cálculo automático:**
   - El sistema debería calcular:
     - 5 días × precio de habitación
     - Descuento del 10% (por ser 3+ días)
     - Total final

### Prueba 5: Límite de 3 Reservas Activas

1. **Crear 3 reservas para el mismo cliente:**
   - Crea 3 reservas con estado "Pendiente" o "Confirmada" para el mismo cliente
   - Todas deberían crearse exitosamente

2. **Intentar crear la 4ta reserva:**
   - Intenta crear otra reserva para el mismo cliente
   - **Debería mostrar error:** "El cliente ya tiene 3 reservas activas. No se pueden crear más."

### Prueba 6: Agregar Comentario a Reserva

1. **Confirmar una reserva:**
   - Ve a Reservas
   - Edita una reserva
   - Cambia el estado a "Confirmada"
   - Guardar

2. **Agregar comentario:**
   - Click en el ícono de comentario (💬) de la reserva confirmada
   - Escribe un comentario: `"Excelente servicio, habitación muy cómoda"`
   - Guardar

3. **Intentar comentar reserva no confirmada:**
   - Intenta agregar comentario a una reserva "Pendiente"
   - Debería mostrar error

### Prueba 7: Descuentos Automáticos

1. **Reserva de 2 días (sin descuento):**
   - Crear reserva del 2024-03-01 al 2024-03-03
   - Descuento debería ser 0%

2. **Reserva de 4 días (10% descuento):**
   - Crear reserva del 2024-03-01 al 2024-03-05
   - Descuento debería ser 10%

3. **Reserva de 8 días (15% descuento):**
   - Crear reserva del 2024-03-01 al 2024-03-09
   - Descuento debería ser 15%

### Prueba 8: Validaciones

1. **Validar campos requeridos:**
   - Intentar crear habitación sin número → Error
   - Intentar crear cliente sin email → Error
   - Intentar crear reserva sin fechas → Error

2. **Validar fechas:**
   - Intentar crear reserva con fecha fin anterior a fecha inicio → Error

3. **Validar email único:**
   - Intentar crear cliente con email existente → Error

---

## 🐛 Solución de Problemas Comunes

### Error: "Cannot connect to MySQL"

**Solución:**
1. Verifica que MySQL esté ejecutándose:
   ```bash
   # macOS
   brew services list
   # O
   sudo /usr/local/mysql/support-files/mysql.server start
   ```

2. Verifica las credenciales en `application.properties`

3. Verifica que el puerto 3306 esté disponible

### Error: "Port 8080 already in use"

**Solución:**
1. Cambia el puerto en `application.properties`:
   ```properties
   server.port=8081
   ```

2. O mata el proceso que usa el puerto:
   ```bash
   # macOS/Linux
   lsof -ti:8080 | xargs kill -9
   ```

### Error: "Table doesn't exist"

**Solución:**
1. Ejecuta los scripts SQL manualmente:
   ```bash
   mysql -u root -p < database/schema.sql
   mysql -u root -p < database/data.sql
   ```

2. O verifica que `spring.jpa.hibernate.ddl-auto=update` esté en `application.properties`

### La aplicación no inicia

**Solución:**
1. Verifica los logs en la consola
2. Asegúrate de tener Java 17:
   ```bash
   java -version
   ```
3. Limpia y recompila:
   ```bash
   mvn clean install
   ```

---

## ✅ Checklist de Verificación

Antes de considerar que todo funciona:

- [ ] La aplicación inicia sin errores
- [ ] Puedo acceder a http://localhost:8080
- [ ] Veo las habitaciones iniciales
- [ ] Veo los clientes iniciales
- [ ] Veo las reservas iniciales
- [ ] Puedo crear una nueva habitación
- [ ] Puedo crear un nuevo cliente
- [ ] Puedo crear una nueva reserva
- [ ] Recibo notificación al crear reserva (consola)
- [ ] El límite de 3 reservas activas funciona
- [ ] Puedo agregar comentario a reserva confirmada
- [ ] Los descuentos se calculan automáticamente
- [ ] Las validaciones funcionan correctamente

---

## 📞 Próximos Pasos

Una vez que todo funcione:

1. Explora todas las funcionalidades
2. Prueba casos límite
3. Verifica que los patrones de diseño funcionen:
   - Observer: Notificaciones en consola
   - Facade: Cálculos automáticos
   - Singleton: Conexión a BD (aunque Spring maneja esto)

¡Disfruta probando tu sistema de reservas! 🎉

