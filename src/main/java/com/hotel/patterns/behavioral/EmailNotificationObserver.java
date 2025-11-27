package com.hotel.patterns.behavioral;

import com.hotel.models.Reserva;
import org.springframework.stereotype.Component;

/**
 * Observador concreto que envía notificaciones por email
 * Implementa el patrón Observer para notificar a clientes sobre nuevas reservas
 */
@Component
public class EmailNotificationObserver implements Observer {
    
    @Override
    public void notificar(Reserva reserva) {
        String mensaje = String.format(
            "Notificación de Reserva\n" +
            "=====================\n" +
            "Estimado/a %s,\n\n" +
            "Su reserva ha sido creada exitosamente:\n" +
            "- Habitación: %s (%s)\n" +
            "- Fecha de inicio: %s\n" +
            "- Fecha de fin: %s\n" +
            "- Estado: %s\n" +
            "- Total: $%.2f\n\n" +
            "Gracias por elegir nuestro hotel.\n",
            reserva.getCliente().getNombre(),
            reserva.getHabitacion().getNumero(),
            reserva.getHabitacion().getTipo(),
            reserva.getFechaInicio(),
            reserva.getFechaFin(),
            reserva.getEstado(),
            reserva.getTotal().doubleValue()
        );
        
        // En un sistema real, aquí se enviaría un email real
        System.out.println("=== EMAIL ENVIADO ===");
        System.out.println("Para: " + reserva.getCliente().getEmail());
        System.out.println(mensaje);
        System.out.println("=====================");
    }
}

