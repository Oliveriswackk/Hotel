package com.hotel.controllers;

import com.hotel.models.Habitacion;
import com.hotel.services.HabitacionService;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import java.util.List;

@Controller
@RequestMapping("/habitaciones")
public class HabitacionController {
    
    @Autowired
    private HabitacionService habitacionService;
    
    @GetMapping
    public String listar(Model model) {
        List<Habitacion> habitaciones = habitacionService.findAll();
        model.addAttribute("habitaciones", habitaciones);
        return "habitaciones/list";
    }
    
    @GetMapping("/nueva")
    public String mostrarFormularioNueva(Model model) {
        model.addAttribute("habitacion", new Habitacion());
        return "habitaciones/form";
    }
    
    @GetMapping("/{id}/editar")
    public String mostrarFormularioEditar(@PathVariable Long id, Model model) {
        Habitacion habitacion = habitacionService.findById(id)
            .orElseThrow(() -> new IllegalArgumentException("Habitación no encontrada"));
        model.addAttribute("habitacion", habitacion);
        return "habitaciones/form";
    }
    
    @PostMapping
    public String guardar(@Valid @ModelAttribute Habitacion habitacion, 
                         BindingResult result, RedirectAttributes redirectAttributes) {
        if (result.hasErrors()) {
            return "habitaciones/form";
        }
        
        try {
            habitacionService.save(habitacion);
            redirectAttributes.addFlashAttribute("mensaje", "Habitación guardada exitosamente");
            redirectAttributes.addFlashAttribute("tipoMensaje", "success");
        } catch (IllegalArgumentException e) {
            redirectAttributes.addFlashAttribute("mensaje", e.getMessage());
            redirectAttributes.addFlashAttribute("tipoMensaje", "danger");
            return "habitaciones/form";
        }
        
        return "redirect:/habitaciones";
    }
    
    @PostMapping("/{id}")
    public String actualizar(@PathVariable Long id, @Valid @ModelAttribute Habitacion habitacion,
                            BindingResult result, RedirectAttributes redirectAttributes) {
        if (result.hasErrors()) {
            return "habitaciones/form";
        }
        
        habitacion.setId(id);
        try {
            habitacionService.save(habitacion);
            redirectAttributes.addFlashAttribute("mensaje", "Habitación actualizada exitosamente");
            redirectAttributes.addFlashAttribute("tipoMensaje", "success");
        } catch (IllegalArgumentException e) {
            redirectAttributes.addFlashAttribute("mensaje", e.getMessage());
            redirectAttributes.addFlashAttribute("tipoMensaje", "danger");
            return "habitaciones/form";
        }
        
        return "redirect:/habitaciones";
    }
    
    @PostMapping("/{id}/eliminar")
    public String eliminar(@PathVariable Long id, RedirectAttributes redirectAttributes) {
        try {
            habitacionService.deleteById(id);
            redirectAttributes.addFlashAttribute("mensaje", "Habitación eliminada exitosamente");
            redirectAttributes.addFlashAttribute("tipoMensaje", "success");
        } catch (Exception e) {
            redirectAttributes.addFlashAttribute("mensaje", "Error al eliminar la habitación: " + e.getMessage());
            redirectAttributes.addFlashAttribute("tipoMensaje", "danger");
        }
        
        return "redirect:/habitaciones";
    }
}

