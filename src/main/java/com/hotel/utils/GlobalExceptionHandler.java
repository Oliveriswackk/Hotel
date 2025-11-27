package com.hotel.utils;

import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

@ControllerAdvice
public class GlobalExceptionHandler {
    
    @ExceptionHandler(IllegalArgumentException.class)
    public String handleIllegalArgumentException(IllegalArgumentException e, 
                                                RedirectAttributes redirectAttributes) {
        redirectAttributes.addFlashAttribute("mensaje", e.getMessage());
        redirectAttributes.addFlashAttribute("tipoMensaje", "danger");
        return "redirect:/";
    }
    
    @ExceptionHandler(IllegalStateException.class)
    public String handleIllegalStateException(IllegalStateException e, 
                                            RedirectAttributes redirectAttributes) {
        redirectAttributes.addFlashAttribute("mensaje", e.getMessage());
        redirectAttributes.addFlashAttribute("tipoMensaje", "danger");
        return "redirect:/";
    }
    
    @ExceptionHandler(Exception.class)
    public String handleGenericException(Exception e, RedirectAttributes redirectAttributes) {
        redirectAttributes.addFlashAttribute("mensaje", 
            "Ocurrió un error inesperado: " + e.getMessage());
        redirectAttributes.addFlashAttribute("tipoMensaje", "danger");
        return "redirect:/";
    }
}

