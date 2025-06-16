package es.iesjandula.GestionPublicaciones.config; // Crearemos un paquete 'config'

import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.servlet.config.annotation.ResourceHandlerRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;

import java.nio.file.Path;
import java.nio.file.Paths;

@Configuration
public class MvcConfig implements WebMvcConfigurer {

    @Value("${file.storage.location}")
    private String storageLocation;

    @Value("${file.serving.path}")
    private String servingPath;

    @Override
    public void addResourceHandlers(ResourceHandlerRegistry registry) {
        Path uploadPath = Paths.get(storageLocation).toAbsolutePath().normalize();
        String resolvedStorageLocation = uploadPath.toUri().toString(); // "file:/C:/Users/Usuario/uploads_gestion_publicaciones/"

        // Asegurarse de que el servingPath comience con / y no termine con / para consistencia
        String normalizedServingPath = servingPath.startsWith("/") ? servingPath : "/" + servingPath;
        if (normalizedServingPath.endsWith("/")) {
            normalizedServingPath = normalizedServingPath.substring(0, normalizedServingPath.length() - 1);
        }
        
        // El patrón debe ser, por ejemplo, "/archivos-publicaciones/**"
        registry.addResourceHandler(normalizedServingPath + "/**")
                .addResourceLocations(resolvedStorageLocation.endsWith("/") ? resolvedStorageLocation : resolvedStorageLocation + "/");
    }
}
