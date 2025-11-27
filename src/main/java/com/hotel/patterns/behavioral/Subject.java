package com.hotel.patterns.behavioral;

/**
 * Interfaz Subject para el patrón Observer
 * Define los métodos para registrar y notificar observadores
 */
public interface Subject {
    void registrarObserver(Observer observer);
    void removerObserver(Observer observer);
    void notificarObservers(com.hotel.models.Reserva reserva);
}

