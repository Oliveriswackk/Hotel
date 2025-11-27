package com.hotel.patterns.behavioral;

import com.hotel.models.Reserva;

/**
 * Interfaz Observer para el patrón Observer
 * Define el método que será llamado cuando se notifique un evento
 */
public interface Observer {
    void notificar(Reserva reserva);
}

