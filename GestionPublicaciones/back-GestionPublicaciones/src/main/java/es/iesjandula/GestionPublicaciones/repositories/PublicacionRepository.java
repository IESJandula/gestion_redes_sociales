package es.iesjandula.GestionPublicaciones.repositories;

import es.iesjandula.GestionPublicaciones.model.Publicacion;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface PublicacionRepository extends JpaRepository<Publicacion, Long> {
}
