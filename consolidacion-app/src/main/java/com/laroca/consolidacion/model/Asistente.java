package com.laroca.consolidacion.model;

import jakarta.persistence.*;
import jakarta.validation.constraints.NotBlank;

import java.time.LocalDate;
import java.time.LocalDateTime;

/**
 * Representa a una persona registrada por el equipo de consolidacion.
 * Cada fila de esta tabla equivale a una fila en la hoja de calculo
 * que se usaba antes: un asistente, un mes, un servicio.
 */
@Entity
@Table(name = "asistentes")
public class Asistente {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @NotBlank(message = "El nombre es obligatorio")
    @Column(nullable = false)
    private String nombre;

    private String telefono;

    @Column(name = "fecha_nacimiento")
    private LocalDate fechaNacimiento;

    private String direccion;

    @Column(nullable = false)
    private String servicio;

    /** Ej: "Enero 2026". Se guarda como texto para que coincida
     *  directamente con las pestañas de mes que ya usa el frontend. */
    @Column(nullable = false)
    private String mes;

    @Column(name = "como_conocio")
    private String comoConocio;

    @Column(name = "peticion_oracion", columnDefinition = "TEXT")
    private String peticionOracion;

    @Column(nullable = false)
    private boolean contactado = false;

    @Column(name = "fecha_registro", updatable = false)
    private LocalDateTime fechaRegistro;

    @PrePersist
    protected void alGuardar() {
        this.fechaRegistro = LocalDateTime.now();
    }

    // ---------- Getters y setters ----------

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

    public String getTelefono() {
        return telefono;
    }

    public void setTelefono(String telefono) {
        this.telefono = telefono;
    }

    public LocalDate getFechaNacimiento() {
        return fechaNacimiento;
    }

    public void setFechaNacimiento(LocalDate fechaNacimiento) {
        this.fechaNacimiento = fechaNacimiento;
    }

    public String getDireccion() {
        return direccion;
    }

    public void setDireccion(String direccion) {
        this.direccion = direccion;
    }

    public String getServicio() {
        return servicio;
    }

    public void setServicio(String servicio) {
        this.servicio = servicio;
    }

    public String getMes() {
        return mes;
    }

    public void setMes(String mes) {
        this.mes = mes;
    }

    public String getComoConocio() {
        return comoConocio;
    }

    public void setComoConocio(String comoConocio) {
        this.comoConocio = comoConocio;
    }

    public String getPeticionOracion() {
        return peticionOracion;
    }

    public void setPeticionOracion(String peticionOracion) {
        this.peticionOracion = peticionOracion;
    }

    public boolean isContactado() {
        return contactado;
    }

    public void setContactado(boolean contactado) {
        this.contactado = contactado;
    }

    public LocalDateTime getFechaRegistro() {
        return fechaRegistro;
    }

    public void setFechaRegistro(LocalDateTime fechaRegistro) {
        this.fechaRegistro = fechaRegistro;
    }
}
