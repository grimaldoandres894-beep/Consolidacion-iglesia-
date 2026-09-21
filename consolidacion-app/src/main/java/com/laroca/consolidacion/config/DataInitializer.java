package com.laroca.consolidacion.config;

import com.laroca.consolidacion.model.Usuario;
import com.laroca.consolidacion.repository.UsuarioRepository;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.CommandLineRunner;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Component;

/**
 * Si la tabla "usuarios" esta vacia (primera vez que corre la app),
 * crea un administrador con las credenciales definidas en
 * application.properties, para que siempre haya una puerta de entrada.
 *
 * IMPORTANTE: cambia app.admin.password en application.properties
 * antes de usar esto en un servidor real, y considera cambiar la
 * contrasena desde la base de datos despues del primer login.
 */
@Component
public class DataInitializer implements CommandLineRunner {

    private final UsuarioRepository usuarioRepository;
    private final PasswordEncoder passwordEncoder;

    @Value("${app.admin.correo}")
    private String adminCorreo;

    @Value("${app.admin.password}")
    private String adminPassword;

    public DataInitializer(UsuarioRepository usuarioRepository, PasswordEncoder passwordEncoder) {
        this.usuarioRepository = usuarioRepository;
        this.passwordEncoder = passwordEncoder;
    }

    @Override
    public void run(String... args) {
        if (usuarioRepository.count() == 0) {
            Usuario admin = new Usuario();
            admin.setNombre("Administrador");
            admin.setCorreo(adminCorreo);
            admin.setContrasenaHash(passwordEncoder.encode(adminPassword));
            admin.setRol("ADMIN");
            usuarioRepository.save(admin);

            System.out.println("========================================================");
            System.out.println(" Usuario administrador creado.");
            System.out.println(" Correo:     " + adminCorreo);
            System.out.println(" Contrasena: la que pusiste en application.properties");
            System.out.println(" Cambia estas credenciales lo antes posible.");
            System.out.println("========================================================");
        }
    }
}
