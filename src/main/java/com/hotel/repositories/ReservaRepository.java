package com.hotel.repositories;

import com.hotel.models.Reserva;
import com.hotel.models.Cliente;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;
import java.util.List;

@Repository
public interface ReservaRepository extends JpaRepository<Reserva, Long> {
    List<Reserva> findByCliente(Cliente cliente);
    
    @Query("SELECT r FROM Reserva r WHERE r.cliente = :cliente AND r.estado IN ('Pendiente', 'Confirmada')")
    List<Reserva> findReservasActivasByCliente(@Param("cliente") Cliente cliente);
    
    List<Reserva> findByEstado(String estado);
}

