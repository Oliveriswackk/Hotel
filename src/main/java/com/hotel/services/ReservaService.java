package com.hotel.services;

import com.hotel.models.Reserva;
import com.hotel.models.Cliente;
import com.hotel.models.Habitacion;
import com.hotel.patterns.behavioral.EmailNotificationObserver;
import com.hotel.patterns.behavioral.ReservaSubject;
import com.hotel.patterns.structural.ReservaFacade;
import com.hotel.repositories.ReservaRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDate;
import java.util.List;
import java.util.Optional;

@Service
@Transactional
public class ReservaService {
    
    @Autowired
    private ReservaRepository reservaRepository;
    
    @Autowired
    private ClienteService clienteService;
    
    @Autowired
    private HabitacionService habitacionService;
    
    @Autowired
    private ReservaFacade reservaFacade;
    
    @Autowired
    private EmailNotificationObserver emailNotificationObserver;
    
    @Autowired
    private ReservaSubject reservaSubject;
    
    @jakarta.annotation.PostConstruct
    public void init() {
        // Registrar el observador de email después de la inyección de dependencias
        reservaSubject.registrarObserver(emailNotificationObserver);
    }
    
    public List<Reserva> findAll() {
        return reservaRepository.findAll();
    }
    
    public Optional<Reserva> findById(Long id) {
        return reservaRepository.findById(id);
    }
    
    public List<Reserva> findByCliente(Cliente cliente) {
        return reservaRepository.findByCliente(cliente);
    }
    
    public List<Reserva> findReservasActivasByCliente(Cliente cliente) {
        return reservaRepository.findReservasActivasByCliente(cliente);
    }
    
    public List<Reserva> findByEstado(String estado) {
        return reservaRepository.findByEstado(estado);
    }
    
    /**
     * Crea una nueva reserva con todas las validaciones y notificaciones
     */
    public Reserva crearReserva(Long clienteId, Long habitacionId, LocalDate fechaInicio, 
                                LocalDate fechaFin, String metodoPago, java.math.BigDecimal descuento) {
        
        // Validar cliente
        Cliente cliente = clienteService.findById(clienteId)
            .orElseThrow(() -> new IllegalArgumentException("Cliente no encontrado"));
        
        // Validar que el cliente no tenga más de 3 reservas activas
        List<Reserva> reservasActivas = findReservasActivasByCliente(cliente);
        if (reservasActivas.size() >= 3) {
            throw new IllegalStateException("El cliente ya tiene 3 reservas activas. No se pueden crear más.");
        }
        
        // Validar habitación
        Habitacion habitacion = habitacionService.findById(habitacionId)
            .orElseThrow(() -> new IllegalArgumentException("Habitación no encontrada"));
        
        // Validar método de pago
        if (!reservaFacade.validarMetodoPago(metodoPago)) {
            throw new IllegalArgumentException("Método de pago no válido");
        }
        
        // Calcular descuento automático si no se proporciona
        if (descuento == null) {
            descuento = reservaFacade.calcularDescuentoPorDuracion(fechaInicio, fechaFin);
        }
        
        // Crear reserva usando el Facade
        Reserva reserva = reservaFacade.crearReserva(cliente, habitacion, fechaInicio, fechaFin, metodoPago, descuento);
        
        // Guardar reserva
        reserva = reservaRepository.save(reserva);
        
        // Notificar usando Observer
        reservaSubject.notificarObservers(reserva);
        
        return reserva;
    }
    
    public Reserva save(Reserva reserva) {
        return reservaRepository.save(reserva);
    }
    
    /**
     * Actualiza el estado de una reserva
     */
    public Reserva actualizarEstado(Long id, String nuevoEstado) {
        Reserva reserva = reservaRepository.findById(id)
            .orElseThrow(() -> new IllegalArgumentException("Reserva no encontrada"));
        
        reserva.setEstado(nuevoEstado);
        return reservaRepository.save(reserva);
    }
    
    /**
     * Agrega un comentario a una reserva completada
     */
    public Reserva agregarComentario(Long id, String comentario) {
        Reserva reserva = reservaRepository.findById(id)
            .orElseThrow(() -> new IllegalArgumentException("Reserva no encontrada"));
        
        if (!"Confirmada".equals(reserva.getEstado())) {
            throw new IllegalStateException("Solo se pueden agregar comentarios a reservas confirmadas");
        }
        
        reserva.setComentario(comentario);
        return reservaRepository.save(reserva);
    }
    
    public void deleteById(Long id) {
        reservaRepository.deleteById(id);
    }
    
    public boolean existsById(Long id) {
        return reservaRepository.existsById(id);
    }
}

