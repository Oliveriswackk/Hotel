package com.hotel.patterns.structural;

import com.hotel.models.Habitacion;
import com.hotel.models.Reserva;
import com.hotel.models.Cliente;
import org.springframework.stereotype.Component;

import java.math.BigDecimal;
import java.math.RoundingMode;
import java.time.LocalDate;
import java.time.temporal.ChronoUnit;

/**
 * Patrón Facade para simplificar operaciones complejas de reservas
 * Proporciona una interfaz simplificada para crear reservas, calcular totales,
 * aplicar descuentos y gestionar métodos de pago
 */
@Component
public class ReservaFacade {
    
    /**
     * Calcula el total de una reserva basado en el precio de la habitación,
     * las fechas y el descuento aplicado
     */
    public BigDecimal calcularTotal(Habitacion habitacion, LocalDate fechaInicio, 
                                   LocalDate fechaFin, BigDecimal descuento) {
        if (fechaInicio.isAfter(fechaFin)) {
            throw new IllegalArgumentException("La fecha de inicio debe ser anterior a la fecha de fin");
        }
        
        long dias = ChronoUnit.DAYS.between(fechaInicio, fechaFin);
        if (dias <= 0) {
            dias = 1; // Mínimo 1 día
        }
        
        BigDecimal subtotal = habitacion.getPrecio().multiply(BigDecimal.valueOf(dias));
        
        // Aplicar descuento
        BigDecimal descuentoAplicado = subtotal.multiply(descuento.divide(BigDecimal.valueOf(100), 2, RoundingMode.HALF_UP));
        BigDecimal total = subtotal.subtract(descuentoAplicado);
        
        return total.setScale(2, RoundingMode.HALF_UP);
    }
    
    /**
     * Valida si una habitación está disponible para las fechas especificadas
     */
    public boolean validarDisponibilidad(Habitacion habitacion, LocalDate fechaInicio, LocalDate fechaFin) {
        return habitacion.getDisponible() && 
               fechaInicio != null && 
               fechaFin != null && 
               !fechaInicio.isAfter(fechaFin);
    }
    
    /**
     * Crea una nueva reserva con todos los cálculos necesarios
     */
    public Reserva crearReserva(Cliente cliente, Habitacion habitacion, 
                                LocalDate fechaInicio, LocalDate fechaFin,
                                String metodoPago, BigDecimal descuento) {
        
        // Validar disponibilidad
        if (!validarDisponibilidad(habitacion, fechaInicio, fechaFin)) {
            throw new IllegalArgumentException("La habitación no está disponible para las fechas seleccionadas");
        }
        
        // Calcular total
        BigDecimal total = calcularTotal(habitacion, fechaInicio, fechaFin, descuento);
        
        // Crear reserva
        Reserva reserva = new Reserva();
        reserva.setCliente(cliente);
        reserva.setHabitacion(habitacion);
        reserva.setFechaInicio(fechaInicio);
        reserva.setFechaFin(fechaFin);
        reserva.setEstado("Pendiente");
        reserva.setMetodoPago(metodoPago);
        reserva.setDescuento(descuento);
        reserva.setTotal(total);
        
        return reserva;
    }
    
    /**
     * Valida el método de pago
     */
    public boolean validarMetodoPago(String metodoPago) {
        if (metodoPago == null || metodoPago.trim().isEmpty()) {
            return false;
        }
        
        String[] metodosValidos = {"Efectivo", "Tarjeta de Crédito", "Tarjeta de Débito", "Transferencia Bancaria"};
        for (String metodo : metodosValidos) {
            if (metodo.equalsIgnoreCase(metodoPago)) {
                return true;
            }
        }
        return false;
    }
    
    /**
     * Calcula un descuento basado en la duración de la estadía
     */
    public BigDecimal calcularDescuentoPorDuracion(LocalDate fechaInicio, LocalDate fechaFin) {
        long dias = ChronoUnit.DAYS.between(fechaInicio, fechaFin);
        
        if (dias >= 7) {
            return BigDecimal.valueOf(15); // 15% de descuento para estadías de 7+ días
        } else if (dias >= 3) {
            return BigDecimal.valueOf(10); // 10% de descuento para estadías de 3+ días
        }
        
        return BigDecimal.ZERO;
    }
}

