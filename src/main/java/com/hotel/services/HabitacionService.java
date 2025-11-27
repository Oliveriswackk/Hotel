package com.hotel.services;

import com.hotel.models.Habitacion;
import com.hotel.repositories.HabitacionRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Optional;

@Service
@Transactional
public class HabitacionService {
    
    @Autowired
    private HabitacionRepository habitacionRepository;
    
    public List<Habitacion> findAll() {
        return habitacionRepository.findAll();
    }
    
    public Optional<Habitacion> findById(Long id) {
        return habitacionRepository.findById(id);
    }
    
    public Optional<Habitacion> findByNumero(String numero) {
        return habitacionRepository.findByNumero(numero);
    }
    
    public List<Habitacion> findDisponibles() {
        return habitacionRepository.findByDisponibleTrue();
    }
    
    public List<Habitacion> findByTipo(String tipo) {
        return habitacionRepository.findByTipo(tipo);
    }
    
    public Habitacion save(Habitacion habitacion) {
        // Validar que el número no esté duplicado
        if (habitacion.getId() == null) {
            Optional<Habitacion> existente = habitacionRepository.findByNumero(habitacion.getNumero());
            if (existente.isPresent()) {
                throw new IllegalArgumentException("Ya existe una habitación con el número: " + habitacion.getNumero());
            }
        }
        return habitacionRepository.save(habitacion);
    }
    
    public void deleteById(Long id) {
        habitacionRepository.deleteById(id);
    }
    
    public boolean existsById(Long id) {
        return habitacionRepository.existsById(id);
    }
}

