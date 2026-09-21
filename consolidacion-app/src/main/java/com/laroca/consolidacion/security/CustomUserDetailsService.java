package com.laroca.consolidacion.security;

import com.laroca.consolidacion.model.Usuario;
import com.laroca.consolidacion.repository.UsuarioRepository;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

/**
 * Cuando alguien intenta iniciar sesion, Spring Security llama a este
 * servicio con el correo que escribio. Aqui lo buscamos en la base de
 * datos y le devolvemos su contrasena (ya encriptada) para que Spring
 * la compare por nosotros.
 */
@Service
public class CustomUserDetailsService implements UserDetailsService {

    private final UsuarioRepository usuarioRepository;

    public CustomUserDetailsService(UsuarioRepository usuarioRepository) {
        this.usuarioRepository = usuarioRepository;
    }

    @Override
    public UserDetails loadUserByUsername(String correo) throws UsernameNotFoundException {
        Usuario usuario = usuarioRepository.findByCorreo(correo)
                .orElseThrow(() -> new UsernameNotFoundException("No existe un usuario con ese correo"));

        return User.builder()
                .username(usuario.getCorreo())
                .password(usuario.getContrasenaHash())
                .roles(usuario.getRol())
                .build();
    }
}
