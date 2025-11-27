package com.hotel.repositories;

import com.hotel.models.Habitacion;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import java.util.List;
import java.util.Optional;

@Repository
public interface HabitacionRepository extends JpaRepository<Habitacion, Long> {
    Optional<Habitacion> findByNumero(String numero);
    List<Habitacion> findByDisponibleTrue();
    List<Habitacion> findByTipo(String tipo);
}

