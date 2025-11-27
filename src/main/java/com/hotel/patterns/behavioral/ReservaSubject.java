package com.hotel.patterns.behavioral;

import com.hotel.models.Reserva;
import java.util.ArrayList;
import java.util.List;

/**
 * Implementación del patrón Observer para notificaciones de reservas
 * Mantiene una lista de observadores y los notifica cuando se crea una reserva
 */
public class ReservaSubject implements Subject {
    
    private List<Observer> observers = new ArrayList<>();
    
    @Override
    public void registrarObserver(Observer observer) {
        observers.add(observer);
    }
    
    @Override
    public void removerObserver(Observer observer) {
        observers.remove(observer);
    }
    
    @Override
    public void notificarObservers(Reserva reserva) {
        for (Observer observer : observers) {
            observer.notificar(reserva);
        }
    }
}

