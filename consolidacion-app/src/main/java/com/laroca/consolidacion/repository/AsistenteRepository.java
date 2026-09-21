package com.laroca.consolidacion.repository;

import com.laroca.consolidacion.model.Asistente;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

/**
 * Spring genera automaticamente la implementacion de esta interfaz.
 * No hay que escribir SQL a mano para lo basico (guardar, listar, borrar).
 */
public interface AsistenteRepository extends JpaRepository<Asistente, Long> {

    // Spring traduce el nombre del metodo en la consulta SQL automaticamente:
    // "SELECT * FROM asistentes WHERE mes = ?"
    List<Asistente> findByMes(String mes);
}
