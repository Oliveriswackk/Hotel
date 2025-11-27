package com.hotel.models;

import jakarta.persistence.*;
import jakarta.validation.constraints.DecimalMin;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import java.math.BigDecimal;

@Entity
@Table(name = "habitaciones")
public class Habitacion {
    
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    
    @NotBlank(message = "El número de habitación es obligatorio")
    @Column(unique = true, nullable = false, length = 10)
    private String numero;
    
    @NotBlank(message = "El tipo de habitación es obligatorio")
    @Column(nullable = false, length = 50)
    private String tipo; // Simple, Doble, Suite
    
    @NotNull(message = "El precio es obligatorio")
    @DecimalMin(value = "0.0", inclusive = false, message = "El precio debe ser mayor a 0")
    @Column(nullable = false, precision = 10, scale = 2)
    private BigDecimal precio;
    
    @Column(nullable = false)
    private Boolean disponible = true;
    
    // Constructors
    public Habitacion() {}
    
    public Habitacion(String numero, String tipo, BigDecimal precio, Boolean disponible) {
        this.numero = numero;
        this.tipo = tipo;
        this.precio = precio;
        this.disponible = disponible;
    }
    
    // Getters and Setters
    public Long getId() {
        return id;
    }
    
    public void setId(Long id) {
        this.id = id;
    }
    
    public String getNumero() {
        return numero;
    }
    
    public void setNumero(String numero) {
        this.numero = numero;
    }
    
    public String getTipo() {
        return tipo;
    }
    
    public void setTipo(String tipo) {
        this.tipo = tipo;
    }
    
    public BigDecimal getPrecio() {
        return precio;
    }
    
    public void setPrecio(BigDecimal precio) {
        this.precio = precio;
    }
    
    public Boolean getDisponible() {
        return disponible;
    }
    
    public void setDisponible(Boolean disponible) {
        this.disponible = disponible;
    }
}

