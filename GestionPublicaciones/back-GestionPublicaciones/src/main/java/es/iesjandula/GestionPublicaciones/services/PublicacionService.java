package es.iesjandula.GestionPublicaciones.services;

import es.iesjandula.GestionPublicaciones.model.Publicacion;
import es.iesjandula.GestionPublicaciones.repositories.PublicacionRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;

@Service
public class PublicacionService {

    @Autowired
    private PublicacionRepository publicacionRepository;

    // Crear publicación
    public Publicacion crearPublicacion(Publicacion publicacion) {
        // Validaciones previas
        if (publicacion.getTitulo() == null || publicacion.getDescripcion() == null) {
            throw new IllegalArgumentException("El título y la descripción son obligatorios.");
        }

        if (publicacion.getArchivosUrl() == null) {
            publicacion.setArchivosUrl(List.of()); // Si no hay archivos, se asigna una lista vacía
        }

        // Asignar la fecha de publicación actual
        publicacion.setFechaPublicacion(LocalDateTime.now());

        // Guardar la publicación en la base de datos
        return publicacionRepository.save(publicacion);
    }

    // Modificar publicación
    public Publicacion modificarPublicacion(Long id, Publicacion publicacion) {
        Optional<Publicacion> existingPublicacion = publicacionRepository.findById(id);

        if (!existingPublicacion.isPresent()) {
            throw new IllegalArgumentException("Publicación no encontrada.");
        }

        Publicacion existing = existingPublicacion.get();

        if (publicacion.getTitulo() != null) existing.setTitulo(publicacion.getTitulo());
        if (publicacion.getDescripcion() != null) existing.setDescripcion(publicacion.getDescripcion());
        if (publicacion.getArchivosUrl() != null) existing.setArchivosUrl(publicacion.getArchivosUrl());
        if (publicacion.getFechaPublicacion() != null) existing.setFechaPublicacion(publicacion.getFechaPublicacion());
        if (publicacion.getCancion() != null) existing.setCancion(publicacion.getCancion());


        return publicacionRepository.save(existing);
    }

    // Eliminar publicación
    public void eliminarPublicacion(Long id) {
        Optional<Publicacion> existingPublicacion = publicacionRepository.findById(id);

        if (!existingPublicacion.isPresent()) {
            throw new IllegalArgumentException("Publicación no encontrada.");
        }

        publicacionRepository.deleteById(id);
    }
    public Publicacion cambiarEstado(Long id, String nuevoEstado) {
        Optional<Publicacion> optionalPublicacion = publicacionRepository.findById(id);
        if (!optionalPublicacion.isPresent()) {
            throw new IllegalArgumentException("Publicación no encontrada.");
        }

        Publicacion publicacion = optionalPublicacion.get();
        publicacion.setEstado(nuevoEstado);
        return publicacionRepository.save(publicacion);
    }


    // Ver todas las publicaciones
    public List<Publicacion> verTodasLasPublicaciones() {
        return publicacionRepository.findAll();
    }

    // Ver una publicación
    public Optional<Publicacion> verPublicacionPorId(Long id) {
        return publicacionRepository.findById(id);
    }
}
