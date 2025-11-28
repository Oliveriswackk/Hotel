package com.hotel.controllers;

import com.hotel.models.Habitacion;
import com.hotel.models.Usuario;
import com.hotel.models.Cliente;
import com.hotel.repositories.UsuarioRepository;
import com.hotel.services.HabitacionService;
import com.hotel.services.ReservaService;
import com.hotel.services.ClienteService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import java.time.LocalDate;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

@Controller
@RequestMapping("/habitaciones-disponibles")
public class HabitacionPublicaController {
    
    @Autowired
    private HabitacionService habitacionService;
    
    @Autowired
    private ReservaService reservaService;
    
    @Autowired
    private UsuarioRepository usuarioRepository;
    
    @Autowired
    private ClienteService clienteService;
    
    @GetMapping
    public String mostrarHabitacionesDisponibles(Model model) {
        List<Habitacion> todasHabitaciones = habitacionService.findAll();
        
        // Agrupar por tipo
        Map<String, List<Habitacion>> habitacionesPorTipo = todasHabitaciones.stream()
            .filter(Habitacion::getDisponible)
            .collect(Collectors.groupingBy(Habitacion::getTipo));
        
        model.addAttribute("habitacionesPorTipo", habitacionesPorTipo);
        model.addAttribute("tipos", List.of("Simple", "Doble", "Suite"));
        return "public/habitaciones-carrusel";
    }
    
    @GetMapping("/reservar/{id}")
    public String mostrarFormularioReserva(@PathVariable Long id, Model model) {
        Habitacion habitacion = habitacionService.findById(id)
            .orElseThrow(() -> new IllegalArgumentException("Habitación no encontrada"));
        
        Authentication auth = SecurityContextHolder.getContext().getAuthentication();
        String email = auth.getName();
        Usuario usuario = usuarioRepository.findByEmail(email)
            .orElseThrow(() -> new IllegalArgumentException("Usuario no encontrado"));
        
        model.addAttribute("habitacion", habitacion);
        model.addAttribute("usuario", usuario);
        model.addAttribute("fechaMinima", LocalDate.now().toString());
        model.addAttribute("metodosPago", List.of("Efectivo", "Tarjeta de Crédito", "Tarjeta de Débito", "Transferencia Bancaria"));
        
        return "public/form-reserva";
    }
    
    @PostMapping("/reservar/{id}")
    public String procesarReserva(@PathVariable Long id,
                                 @RequestParam @org.springframework.format.annotation.DateTimeFormat(iso = org.springframework.format.annotation.DateTimeFormat.ISO.DATE) LocalDate fechaInicio,
                                 @RequestParam @org.springframework.format.annotation.DateTimeFormat(iso = org.springframework.format.annotation.DateTimeFormat.ISO.DATE) LocalDate fechaFin,
                                 @RequestParam String metodoPago,
                                 @RequestParam(required = false) java.math.BigDecimal descuento,
                                 RedirectAttributes redirectAttributes) {
        
        Authentication auth = SecurityContextHolder.getContext().getAuthentication();
        String email = auth.getName();
        Usuario usuario = usuarioRepository.findByEmail(email)
            .orElseThrow(() -> new IllegalArgumentException("Usuario no encontrado"));
        
        try {
            // Obtener o crear cliente asociado al usuario
            Cliente cliente;
            Long clienteId;
            
            if (usuario.getCliente() != null) {
                cliente = usuario.getCliente();
                clienteId = cliente.getId();
            } else {
                // Buscar cliente por email o crear uno nuevo
                cliente = clienteService.findByEmail(usuario.getEmail())
                    .orElseGet(() -> {
                        Cliente nuevoCliente = new Cliente();
                        nuevoCliente.setNombre(usuario.getNombre());
                        nuevoCliente.setEmail(usuario.getEmail());
                        nuevoCliente.setTelefono("Sin teléfono");
                        return clienteService.save(nuevoCliente);
                    });
                
                // Asociar cliente al usuario
                usuario.setCliente(cliente);
                usuarioRepository.save(usuario);
                clienteId = cliente.getId();
            }
            
            reservaService.crearReserva(clienteId, id, fechaInicio, fechaFin, metodoPago, descuento);
            redirectAttributes.addFlashAttribute("mensaje", 
                "Reserva creada exitosamente. Se ha enviado una notificación a tu email.");
            redirectAttributes.addFlashAttribute("tipoMensaje", "success");
        } catch (IllegalArgumentException | IllegalStateException e) {
            redirectAttributes.addFlashAttribute("mensaje", e.getMessage());
            redirectAttributes.addFlashAttribute("tipoMensaje", "danger");
            return "redirect:/habitaciones-disponibles/reservar/" + id;
        }
        
        return "redirect:/habitaciones-disponibles";
    }
}

