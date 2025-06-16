package es.iesjandula.GestionPublicaciones.controllers;

import es.iesjandula.GestionPublicaciones.model.publicacionArchivo;
import es.iesjandula.GestionPublicaciones.repositories.PublicacionArchivoRepository;
import es.iesjandula.GestionPublicaciones.services.PublicacionArchivoService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Map;

@RestController
@RequestMapping("/publicacion_archivos")
@CrossOrigin(origins = "http://localhost:5173")
public class PublicacionArchivoController {

    @Autowired
    private PublicacionArchivoService publicacionArchivoService;

    @Autowired
    private PublicacionArchivoRepository publicacionArchivoRepository;

    @PostMapping("/crear")
    public ResponseEntity<publicacionArchivo> crear(@RequestBody publicacionArchivo publicacionArchivo) {
        // Asegúrate de que publicacion.id no sea null
        if (publicacionArchivo.getPublicacion() == null || publicacionArchivo.getPublicacion().getId() == null) {
            return ResponseEntity.badRequest().body(null);
        }

        publicacionArchivo guardado = publicacionArchivoService.guardarArchivo(
                publicacionArchivo.getPublicacion().getId(),
                publicacionArchivo.getArchivoUrl()
        );

        return ResponseEntity.ok(guardado);
    }
    @GetMapping("/{publicacionId}/archivos")
    public List<publicacionArchivo> obtenerArchivosPorPublicacion(@PathVariable Long publicacionId) {
        return publicacionArchivoRepository.findByPublicacionId(publicacionId);
    }
}

