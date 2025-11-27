-- Script de datos iniciales para la base de datos hotel_db

USE hotel_db;

-- Insertar habitaciones
INSERT INTO habitaciones (numero, tipo, precio, disponible) VALUES
('101', 'Simple', 50.00, TRUE),
('102', 'Doble', 80.00, TRUE),
('201', 'Suite', 150.00, TRUE),
('202', 'Simple', 50.00, TRUE),
('301', 'Doble', 80.00, TRUE);

-- Insertar clientes
INSERT INTO clientes (nombre, email, telefono) VALUES
('Juan Pérez', 'juan.perez@email.com', '555-0101'),
('María García', 'maria.garcia@email.com', '555-0102'),
('Carlos López', 'carlos.lopez@email.com', '555-0103');

-- Insertar reservas
INSERT INTO reservas (cliente_id, habitacion_id, fecha_inicio, fecha_fin, estado, metodo_pago, descuento, total) VALUES
(1, 1, '2024-01-15', '2024-01-17', 'Confirmada', 'Tarjeta de Crédito', 0.00, 100.00),
(2, 2, '2024-01-20', '2024-01-25', 'Pendiente', 'Efectivo', 10.00, 360.00),
(1, 3, '2024-02-01', '2024-02-05', 'Confirmada', 'Transferencia Bancaria', 15.00, 510.00);

