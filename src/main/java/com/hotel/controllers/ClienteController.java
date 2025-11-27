package com.hotel.controllers;

import com.hotel.models.Cliente;
import com.hotel.services.ClienteService;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import java.util.List;

@Controller
@RequestMapping("/clientes")
public class ClienteController {
    
    @Autowired
    private ClienteService clienteService;
    
    @GetMapping
    public String listar(Model model) {
        List<Cliente> clientes = clienteService.findAll();
        model.addAttribute("clientes", clientes);
        return "clientes/list";
    }
    
    @GetMapping("/nuevo")
    public String mostrarFormularioNuevo(Model model) {
        model.addAttribute("cliente", new Cliente());
        return "clientes/form";
    }
    
    @GetMapping("/{id}/editar")
    public String mostrarFormularioEditar(@PathVariable Long id, Model model) {
        Cliente cliente = clienteService.findById(id)
            .orElseThrow(() -> new IllegalArgumentException("Cliente no encontrado"));
        model.addAttribute("cliente", cliente);
        return "clientes/form";
    }
    
    @PostMapping
    public String guardar(@Valid @ModelAttribute Cliente cliente, 
                         BindingResult result, RedirectAttributes redirectAttributes) {
        if (result.hasErrors()) {
            return "clientes/form";
        }
        
        try {
            clienteService.save(cliente);
            redirectAttributes.addFlashAttribute("mensaje", "Cliente guardado exitosamente");
            redirectAttributes.addFlashAttribute("tipoMensaje", "success");
        } catch (IllegalArgumentException e) {
            redirectAttributes.addFlashAttribute("mensaje", e.getMessage());
            redirectAttributes.addFlashAttribute("tipoMensaje", "danger");
            return "clientes/form";
        }
        
        return "redirect:/clientes";
    }
    
    @PostMapping("/{id}")
    public String actualizar(@PathVariable Long id, @Valid @ModelAttribute Cliente cliente,
                            BindingResult result, RedirectAttributes redirectAttributes) {
        if (result.hasErrors()) {
            return "clientes/form";
        }
        
        cliente.setId(id);
        try {
            clienteService.save(cliente);
            redirectAttributes.addFlashAttribute("mensaje", "Cliente actualizado exitosamente");
            redirectAttributes.addFlashAttribute("tipoMensaje", "success");
        } catch (IllegalArgumentException e) {
            redirectAttributes.addFlashAttribute("mensaje", e.getMessage());
            redirectAttributes.addFlashAttribute("tipoMensaje", "danger");
            return "clientes/form";
        }
        
        return "redirect:/clientes";
    }
    
    @PostMapping("/{id}/eliminar")
    public String eliminar(@PathVariable Long id, RedirectAttributes redirectAttributes) {
        try {
            clienteService.deleteById(id);
            redirectAttributes.addFlashAttribute("mensaje", "Cliente eliminado exitosamente");
            redirectAttributes.addFlashAttribute("tipoMensaje", "success");
        } catch (Exception e) {
            redirectAttributes.addFlashAttribute("mensaje", "Error al eliminar el cliente: " + e.getMessage());
            redirectAttributes.addFlashAttribute("tipoMensaje", "danger");
        }
        
        return "redirect:/clientes";
    }
}

