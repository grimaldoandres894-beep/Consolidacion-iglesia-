package com.laroca.consolidacion.model;

import jakarta.persistence.*;
import jakarta.validation.constraints.NotBlank;

/**
 * Una persona del equipo que puede entrar al panel de administracion.
 * La contrasena nunca se guarda en texto plano: contrasenaHash guarda
 * el resultado de aplicarle BCrypt.
 */
@Entity
@Table(name = "usuarios")
public class Usuario {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @NotBlank
    private String nombre;

    @NotBlank
    @Column(nullable = false, unique = true)
    private String correo;

    @NotBlank
    @Column(name = "contrasena_hash", nullable = false)
    private String contrasenaHash;

    /** Por ahora: "ADMIN". Deja espacio para futuros roles
     *  (ej. "CONSOLIDACION" con menos permisos). */
    @Column(nullable = false)
    private String rol = "ADMIN";

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getNombre() {
        return nombre;
    }

    public void setNombre(String nombre) {
        this.nombre = nombre;
    }

    public String getCorreo() {
        return correo;
    }

    public void setCorreo(String correo) {
        this.correo = correo;
    }

    public String getContrasenaHash() {
        return contrasenaHash;
    }

    public void setContrasenaHash(String contrasenaHash) {
        this.contrasenaHash = contrasenaHash;
    }

    public String getRol() {
        return rol;
    }

    public void setRol(String rol) {
        this.rol = rol;
    }
}
