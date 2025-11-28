package com.hotel.controllers;

import com.hotel.services.UsuarioService;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

@Controller
public class AuthController {
    
    @Autowired
    private UsuarioService usuarioService;
    
    @GetMapping("/login")
    public String mostrarLogin(@RequestParam(required = false) String error,
                              @RequestParam(required = false) String logout,
                              Model model) {
        if (error != null) {
            model.addAttribute("error", "Email o contraseña incorrectos");
        }
        if (logout != null) {
            model.addAttribute("mensaje", "Has cerrado sesión exitosamente");
        }
        return "auth/login";
    }
    
    @GetMapping("/register")
    public String mostrarRegistro(Model model) {
        model.addAttribute("usuario", new UsuarioRegistroDTO());
        return "auth/register";
    }
    
    @PostMapping("/register")
    public String procesarRegistro(@Valid @ModelAttribute("usuario") UsuarioRegistroDTO usuarioDTO,
                                   BindingResult result,
                                   RedirectAttributes redirectAttributes) {
        if (result.hasErrors()) {
            return "auth/register";
        }
        
        if (usuarioService.existsByEmail(usuarioDTO.getEmail())) {
            result.rejectValue("email", "error.email", "Ya existe un usuario con este email");
            return "auth/register";
        }
        
        try {
            usuarioService.registrarUsuario(
                usuarioDTO.getNombre(),
                usuarioDTO.getEmail(),
                usuarioDTO.getPassword(),
                usuarioDTO.isEsAdmin()
            );
            redirectAttributes.addFlashAttribute("mensaje", 
                "Registro exitoso. Por favor inicia sesión.");
            redirectAttributes.addFlashAttribute("tipoMensaje", "success");
            return "redirect:/login";
        } catch (Exception e) {
            redirectAttributes.addFlashAttribute("mensaje", 
                "Error al registrar: " + e.getMessage());
            redirectAttributes.addFlashAttribute("tipoMensaje", "danger");
            return "redirect:/register";
        }
    }
    
    @GetMapping("/redirect-by-role")
    public String redirectByRole() {
        Authentication auth = SecurityContextHolder.getContext().getAuthentication();
        if (auth != null && auth.getAuthorities().stream()
                .anyMatch(a -> a.getAuthority().equals("ROLE_ADMIN"))) {
            return "redirect:/habitaciones";
        }
        return "redirect:/habitaciones-disponibles";
    }
    
    // DTO para el formulario de registro
    public static class UsuarioRegistroDTO {
        private String nombre;
        private String email;
        private String password;
        private String confirmPassword;
        private boolean esAdmin;
        
        // Getters and Setters
        public String getNombre() {
            return nombre;
        }
        
        public void setNombre(String nombre) {
            this.nombre = nombre;
        }
        
        public String getEmail() {
            return email;
        }
        
        public void setEmail(String email) {
            this.email = email;
        }
        
        public String getPassword() {
            return password;
        }
        
        public void setPassword(String password) {
            this.password = password;
        }
        
        public String getConfirmPassword() {
            return confirmPassword;
        }
        
        public void setConfirmPassword(String confirmPassword) {
            this.confirmPassword = confirmPassword;
        }
        
        public boolean isEsAdmin() {
            return esAdmin;
        }
        
        public void setEsAdmin(boolean esAdmin) {
            this.esAdmin = esAdmin;
        }
    }
}

