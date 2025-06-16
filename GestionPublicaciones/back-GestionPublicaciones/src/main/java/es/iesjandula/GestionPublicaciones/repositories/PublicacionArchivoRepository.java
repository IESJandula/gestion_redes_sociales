package es.iesjandula.GestionPublicaciones.repositories;

import es.iesjandula.GestionPublicaciones.model.publicacionArchivo;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface PublicacionArchivoRepository extends JpaRepository<publicacionArchivo, Long> {

    List<publicacionArchivo> findByPublicacionId(Long publicacionId);

}
