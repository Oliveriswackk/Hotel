package com.hotel.controllers;

import com.hotel.models.Reserva;
import com.hotel.services.ReservaService;
import com.hotel.services.ClienteService;
import com.hotel.services.HabitacionService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.format.annotation.DateTimeFormat;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.util.List;

@Controller
@RequestMapping("/reservas")
public class ReservaController {
    
    @Autowired
    private ReservaService reservaService;
    
    @Autowired
    private ClienteService clienteService;
    
    @Autowired
    private HabitacionService habitacionService;
    
    @GetMapping
    public String listar(Model model) {
        List<Reserva> reservas = reservaService.findAll();
        model.addAttribute("reservas", reservas);
        return "reservas/list";
    }
    
    @GetMapping("/nueva")
    public String mostrarFormularioNueva(Model model) {
        model.addAttribute("reserva", new Reserva());
        model.addAttribute("clientes", clienteService.findAll());
        model.addAttribute("habitaciones", habitacionService.findDisponibles());
        model.addAttribute("metodosPago", List.of("Efectivo", "Tarjeta de Crédito", "Tarjeta de Débito", "Transferencia Bancaria"));
        model.addAttribute("estados", List.of("Pendiente", "Confirmada", "Cancelada"));
        return "reservas/form";
    }
    
    @GetMapping("/{id}/editar")
    public String mostrarFormularioEditar(@PathVariable Long id, Model model) {
        Reserva reserva = reservaService.findById(id)
            .orElseThrow(() -> new IllegalArgumentException("Reserva no encontrada"));
        model.addAttribute("reserva", reserva);
        model.addAttribute("clientes", clienteService.findAll());
        model.addAttribute("habitaciones", habitacionService.findAll());
        model.addAttribute("metodosPago", List.of("Efectivo", "Tarjeta de Crédito", "Tarjeta de Débito", "Transferencia Bancaria"));
        model.addAttribute("estados", List.of("Pendiente", "Confirmada", "Cancelada"));
        return "reservas/form";
    }
    
    @GetMapping("/{id}/comentario")
    public String mostrarFormularioComentario(@PathVariable Long id, Model model) {
        Reserva reserva = reservaService.findById(id)
            .orElseThrow(() -> new IllegalArgumentException("Reserva no encontrada"));
        
        if (!"Confirmada".equals(reserva.getEstado())) {
            model.addAttribute("error", "Solo se pueden agregar comentarios a reservas confirmadas");
            return "redirect:/reservas";
        }
        
        model.addAttribute("reserva", reserva);
        return "reservas/comentario";
    }
    
    @PostMapping
    public String guardar(@RequestParam Long clienteId,
                         @RequestParam Long habitacionId,
                         @RequestParam @DateTimeFormat(iso = DateTimeFormat.ISO.DATE) LocalDate fechaInicio,
                         @RequestParam @DateTimeFormat(iso = DateTimeFormat.ISO.DATE) LocalDate fechaFin,
                         @RequestParam String metodoPago,
                         @RequestParam(required = false) BigDecimal descuento,
                         RedirectAttributes redirectAttributes) {
        
        try {
            Reserva reserva = reservaService.crearReserva(clienteId, habitacionId, fechaInicio, 
                                                          fechaFin, metodoPago, descuento);
            redirectAttributes.addFlashAttribute("mensaje", "Reserva creada exitosamente. Se ha enviado una notificación al cliente.");
            redirectAttributes.addFlashAttribute("tipoMensaje", "success");
        } catch (IllegalArgumentException | IllegalStateException e) {
            redirectAttributes.addFlashAttribute("mensaje", e.getMessage());
            redirectAttributes.addFlashAttribute("tipoMensaje", "danger");
            return "redirect:/reservas/nueva";
        }
        
        return "redirect:/reservas";
    }
    
    @PostMapping("/{id}")
    public String actualizar(@PathVariable Long id,
                            @RequestParam String estado,
                            RedirectAttributes redirectAttributes) {
        try {
            reservaService.actualizarEstado(id, estado);
            redirectAttributes.addFlashAttribute("mensaje", "Reserva actualizada exitosamente");
            redirectAttributes.addFlashAttribute("tipoMensaje", "success");
        } catch (IllegalArgumentException e) {
            redirectAttributes.addFlashAttribute("mensaje", e.getMessage());
            redirectAttributes.addFlashAttribute("tipoMensaje", "danger");
        } catch (Exception e) {
            redirectAttributes.addFlashAttribute("mensaje", "Error al actualizar la reserva: " + e.getMessage());
            redirectAttributes.addFlashAttribute("tipoMensaje", "danger");
        }
        
        return "redirect:/reservas";
    }
    
    @PostMapping("/{id}/comentario")
    public String agregarComentario(@PathVariable Long id,
                                   @RequestParam String comentario,
                                   RedirectAttributes redirectAttributes) {
        try {
            reservaService.agregarComentario(id, comentario);
            redirectAttributes.addFlashAttribute("mensaje", "Comentario agregado exitosamente");
            redirectAttributes.addFlashAttribute("tipoMensaje", "success");
        } catch (IllegalArgumentException | IllegalStateException e) {
            redirectAttributes.addFlashAttribute("mensaje", e.getMessage());
            redirectAttributes.addFlashAttribute("tipoMensaje", "danger");
        }
        
        return "redirect:/reservas";
    }
    
    @PostMapping("/{id}/eliminar")
    public String eliminar(@PathVariable Long id, RedirectAttributes redirectAttributes) {
        try {
            reservaService.deleteById(id);
            redirectAttributes.addFlashAttribute("mensaje", "Reserva eliminada exitosamente");
            redirectAttributes.addFlashAttribute("tipoMensaje", "success");
        } catch (Exception e) {
            redirectAttributes.addFlashAttribute("mensaje", "Error al eliminar la reserva: " + e.getMessage());
            redirectAttributes.addFlashAttribute("tipoMensaje", "danger");
        }
        
        return "redirect:/reservas";
    }
}

