package es.iesjandula.GestionPublicaciones.services;

import es.iesjandula.GestionPublicaciones.model.Publicacion;
import es.iesjandula.GestionPublicaciones.model.publicacionArchivo;
import es.iesjandula.GestionPublicaciones.repositories.PublicacionArchivoRepository;
import es.iesjandula.GestionPublicaciones.repositories.PublicacionRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class PublicacionArchivoService {
    @Autowired
    private PublicacionArchivoRepository publicacionArchivoRepository;

    @Autowired
    private PublicacionRepository publicacionRepository;

    public publicacionArchivo guardarArchivo(Long publicacionId, String urlArchivo) {
        Publicacion publicacion = publicacionRepository.findById(publicacionId)
                .orElseThrow(() -> new IllegalArgumentException("Publicación no encontrada"));

        publicacionArchivo archivo = new publicacionArchivo();
        archivo.setArchivoUrl(urlArchivo);
        archivo.setPublicacion(publicacion);

        return publicacionArchivoRepository.save(archivo);
    }

}
