package es.iesjandula.GestionPublicaciones.controllers;

import es.iesjandula.GestionPublicaciones.DTO.estadoRequest;
import es.iesjandula.GestionPublicaciones.model.Publicacion;
import es.iesjandula.GestionPublicaciones.repositories.PublicacionRepository;
import es.iesjandula.GestionPublicaciones.services.PublicacionService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Map;
import java.util.Optional;

@RestController
@RequestMapping("/publicacion")
@CrossOrigin(origins = "http://localhost:5173")
public class PublicacionController {

    @Autowired
    private PublicacionService publicacionService;
    @Autowired
    private PublicacionRepository publicacionRepository;

    // Crear publicación
    @PostMapping("/crear")
    public ResponseEntity<Publicacion> crearPublicacion(@RequestBody Publicacion publicacion) {
        try {
            // Crear la publicación llamando al servicio
            Publicacion nuevaPublicacion = publicacionService.crearPublicacion(publicacion);
            return new ResponseEntity<>(nuevaPublicacion, HttpStatus.CREATED);
        } catch (IllegalArgumentException e) {
            return new ResponseEntity<>(null, HttpStatus.BAD_REQUEST);
        }
    }


 

    // Modificar publicación
    @PutMapping("/modificar/{id}")
    public ResponseEntity<Publicacion> modificarPublicacion(@PathVariable Long id, @RequestBody Publicacion publicacion) {
        try {
            Publicacion modificada = publicacionService.modificarPublicacion(id, publicacion);
            return new ResponseEntity<>(modificada, HttpStatus.OK);
        } catch (IllegalArgumentException e) {
            return new ResponseEntity<>(null, HttpStatus.NOT_FOUND);
        }
    }

    // Eliminar publicación
    @DeleteMapping("/eliminar/{id}")
    public ResponseEntity<Void> eliminarPublicacion(@PathVariable Long id) {
        try {
            publicacionService.eliminarPublicacion(id);
            return new ResponseEntity<>(HttpStatus.NO_CONTENT);
        } catch (IllegalArgumentException e) {
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }
    }

    // Ver todas las publicaciones
    @GetMapping
    public ResponseEntity<List<Publicacion>> verTodasLasPublicaciones() {
        List<Publicacion> publicaciones = publicacionService.verTodasLasPublicaciones();
        if (publicaciones.isEmpty()) {
            return new ResponseEntity<>(HttpStatus.NO_CONTENT);
        }
        return new ResponseEntity<>(publicaciones, HttpStatus.OK);
    }

    // Ver publicación por ID
    @GetMapping("/ver/{id}")
    public ResponseEntity<Publicacion> verPublicacionPorId(@PathVariable Long id) {
        Optional<Publicacion> publicacion = publicacionService.verPublicacionPorId(id);

        return publicacion.map(value ->
                new ResponseEntity<>(value, HttpStatus.OK)
        ).orElseGet(() ->
                new ResponseEntity<>(HttpStatus.NOT_FOUND)
        );
    }

    @PostMapping("/publicacion/estado/{id}")
    public ResponseEntity<?> cambiarEstado(
            @PathVariable Long id,
            @RequestBody estadoRequest estadoRequest
    ) {
        Optional<Publicacion> optionalPublicacion = publicacionRepository.findById(id);

        if (optionalPublicacion.isEmpty()) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body("Publicación no encontrada");
        }

        Publicacion publicacion = optionalPublicacion.get();
        publicacion.setEstado(estadoRequest.getEstado());
        publicacionRepository.save(publicacion);

        return ResponseEntity.ok("Estado actualizado a: " + estadoRequest.getEstado());
    }

    @PostMapping("/estado/{id}")
    public ResponseEntity<Publicacion> cambiarEstado(
            @PathVariable Long id,
            @RequestBody Map<String, String> estadoPayload) {
        try {
            String nuevoEstado = estadoPayload.get("estado");
            if (nuevoEstado == null || nuevoEstado.isBlank()) {
                return ResponseEntity.badRequest().build();
            }

            Publicacion publicacionActualizada = publicacionService.cambiarEstado(id, nuevoEstado);
            return ResponseEntity.ok(publicacionActualizada);
        } catch (IllegalArgumentException ex) {
            return ResponseEntity.notFound().build();
        } catch (Exception ex) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).build();
        }
    }

}
