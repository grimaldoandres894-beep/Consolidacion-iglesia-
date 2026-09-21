package com.laroca.consolidacion.controller;

import com.laroca.consolidacion.model.Asistente;
import com.laroca.consolidacion.repository.AsistenteRepository;
import jakarta.validation.Valid;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Map;

/**
 * Expone /api/asistentes para que el frontend (consolidacion.html)
 * reemplace las llamadas a window.storage por llamadas reales a la
 * base de datos.
 */
@RestController
@RequestMapping("/api/asistentes")
public class AsistenteController {

    private final AsistenteRepository repositorio;

    public AsistenteController(AsistenteRepository repositorio) {
        this.repositorio = repositorio;
    }

    // GET /api/asistentes            -> todos los registros
    // GET /api/asistentes?mes=Enero%202026 -> solo los de ese mes
    @GetMapping
    public List<Asistente> listar(@RequestParam(required = false) String mes) {
        if (mes != null && !mes.isBlank()) {
            return repositorio.findByMes(mes);
        }
        return repositorio.findAll();
    }

    // GET /api/asistentes/5
    @GetMapping("/{id}")
    public ResponseEntity<Asistente> obtenerUno(@PathVariable Long id) {
        return repositorio.findById(id)
                .map(ResponseEntity::ok)
                .orElse(ResponseEntity.notFound().build());
    }

    // POST /api/asistentes  (cuerpo: JSON con los campos del formulario)
    @PostMapping
    @ResponseStatus(HttpStatus.CREATED)
    public Asistente registrar(@Valid @RequestBody Asistente nuevoAsistente) {
        // el id y fecha_registro los pone la base de datos, no vienen del frontend
        nuevoAsistente.setId(null);
        return repositorio.save(nuevoAsistente);
    }

    // PATCH /api/asistentes/5/contactado   (cuerpo: { "contactado": true })
    @PatchMapping("/{id}/contactado")
    public ResponseEntity<Asistente> actualizarEstado(
            @PathVariable Long id,
            @RequestBody Map<String, Boolean> cuerpo) {

        return repositorio.findById(id)
                .map(asistente -> {
                    asistente.setContactado(Boolean.TRUE.equals(cuerpo.get("contactado")));
                    return ResponseEntity.ok(repositorio.save(asistente));
                })
                .orElse(ResponseEntity.notFound().build());
    }

    // DELETE /api/asistentes/5
    @DeleteMapping("/{id}")
    public ResponseEntity<Void> eliminar(@PathVariable Long id) {
        if (!repositorio.existsById(id)) {
            return ResponseEntity.notFound().build();
        }
        repositorio.deleteById(id);
        return ResponseEntity.noContent().build();
    }
}
