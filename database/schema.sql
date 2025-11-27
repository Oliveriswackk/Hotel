-- Script de creación de la base de datos y tablas
-- Base de datos: hotel_db

CREATE DATABASE IF NOT EXISTS hotel_db;
USE hotel_db;

-- Tabla: habitaciones
CREATE TABLE IF NOT EXISTS habitaciones (
    id INT PRIMARY KEY AUTO_INCREMENT,
    numero VARCHAR(10) UNIQUE NOT NULL,
    tipo VARCHAR(50) NOT NULL,
    precio DECIMAL(10,2) NOT NULL,
    disponible BOOLEAN DEFAULT TRUE,
    CONSTRAINT chk_tipo CHECK (tipo IN ('Simple', 'Doble', 'Suite')),
    CONSTRAINT chk_precio CHECK (precio > 0)
);

-- Tabla: clientes
CREATE TABLE IF NOT EXISTS clientes (
    id INT PRIMARY KEY AUTO_INCREMENT,
    nombre VARCHAR(100) NOT NULL,
    email VARCHAR(100) UNIQUE NOT NULL,
    telefono VARCHAR(20) NOT NULL
);

-- Tabla: reservas
CREATE TABLE IF NOT EXISTS reservas (
    id INT PRIMARY KEY AUTO_INCREMENT,
    cliente_id INT NOT NULL,
    habitacion_id INT NOT NULL,
    fecha_inicio DATE NOT NULL,
    fecha_fin DATE NOT NULL,
    estado VARCHAR(20) NOT NULL,
    metodo_pago VARCHAR(50) NOT NULL,
    descuento DECIMAL(5,2) DEFAULT 0,
    total DECIMAL(10,2) NOT NULL,
    comentario TEXT NULL,
    CONSTRAINT fk_reserva_cliente FOREIGN KEY (cliente_id) REFERENCES clientes(id) ON DELETE CASCADE,
    CONSTRAINT fk_reserva_habitacion FOREIGN KEY (habitacion_id) REFERENCES habitaciones(id) ON DELETE CASCADE,
    CONSTRAINT chk_estado CHECK (estado IN ('Pendiente', 'Confirmada', 'Cancelada')),
    CONSTRAINT chk_fechas CHECK (fecha_inicio <= fecha_fin),
    CONSTRAINT chk_descuento CHECK (descuento >= 0 AND descuento <= 100),
    CONSTRAINT chk_total CHECK (total >= 0)
);

-- Índices para mejorar el rendimiento
CREATE INDEX idx_reserva_cliente ON reservas(cliente_id);
CREATE INDEX idx_reserva_habitacion ON reservas(habitacion_id);
CREATE INDEX idx_reserva_estado ON reservas(estado);
CREATE INDEX idx_habitacion_disponible ON habitaciones(disponible);

