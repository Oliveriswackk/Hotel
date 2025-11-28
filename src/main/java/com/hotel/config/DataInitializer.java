package com.hotel.config;

import com.hotel.models.Usuario;
import com.hotel.repositories.UsuarioRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.CommandLineRunner;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Component;

@Component
public class DataInitializer implements CommandLineRunner {
    
    @Autowired
    private UsuarioRepository usuarioRepository;
    
    @Autowired
    private PasswordEncoder passwordEncoder;
    
    @Override
    public void run(String... args) throws Exception {
        // Crear usuario admin si no existe
        if (!usuarioRepository.existsByEmail("admin@hotel.com")) {
            Usuario admin = new Usuario();
            admin.setNombre("Administrador");
            admin.setEmail("admin@hotel.com");
            admin.setPassword(passwordEncoder.encode("admin123"));
            admin.setRol("ADMIN");
            admin.setActivo(true);
            usuarioRepository.save(admin);
            System.out.println("Usuario administrador creado: admin@hotel.com / admin123");
        }
        
        // Crear usuario de prueba si no existe
        if (!usuarioRepository.existsByEmail("user@hotel.com")) {
            Usuario user = new Usuario();
            user.setNombre("Usuario Test");
            user.setEmail("user@hotel.com");
            user.setPassword(passwordEncoder.encode("user123"));
            user.setRol("USER");
            user.setActivo(true);
            usuarioRepository.save(user);
            System.out.println("Usuario de prueba creado: user@hotel.com / user123");
        }
    }
}

