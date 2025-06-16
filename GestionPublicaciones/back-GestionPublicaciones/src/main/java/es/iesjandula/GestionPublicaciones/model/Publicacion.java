package es.iesjandula.GestionPublicaciones.model;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import jakarta.persistence.*;
import java.time.LocalDateTime;
import java.util.List;


@Entity
public class Publicacion {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private String titulo;
    private String descripcion;
    private LocalDateTime fechaPublicacion;
    private String cancion;


    private String estado = "PENDIENTE";



    @ElementCollection
    @CollectionTable(name = "publicacion_archivos", joinColumns = @JoinColumn(name = "publicacion_id"))
    @Column(name = "archivo_url")
    private List<String> archivosUrl;




    public Publicacion() {}

    public Publicacion(Long id, String titulo,String estado, String descripcion, LocalDateTime fechaPublicacion, String cancion,
                     List<String> archivosUrl) {
        this.id = id;
        this.titulo = titulo;
        this.estado=estado;
        this.descripcion = descripcion;
        this.fechaPublicacion = fechaPublicacion;
        this.cancion = cancion;
        this.archivosUrl = archivosUrl;
    }

    // Getters y setters
    public Long getId() { return id; }
    public void setId(Long id) { this.id = id; }

    public String getTitulo() { return titulo; }
    public void setTitulo(String titulo) { this.titulo = titulo; }

    public String getDescripcion() { return descripcion; }
    public void setDescripcion(String descripcion) { this.descripcion = descripcion; }

    public LocalDateTime getFechaPublicacion() { return fechaPublicacion; }
    public void setFechaPublicacion(LocalDateTime fechaPublicacion) { this.fechaPublicacion = fechaPublicacion; }

    public String getCancion() { return cancion; }
    public void setCancion(String cancion) { this.cancion = cancion; }


    public List<String> getArchivosUrl() { return archivosUrl; }
    public void setArchivosUrl(List<String> archivosUrl) { this.archivosUrl = archivosUrl; }

    public String getEstado() {
        return estado;
    }

    public void setEstado(String estado) {
        this.estado = estado;
    }
}
