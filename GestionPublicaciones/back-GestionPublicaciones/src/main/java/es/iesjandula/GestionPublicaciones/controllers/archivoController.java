package es.iesjandula.GestionPublicaciones.controllers;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.util.StringUtils;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.nio.file.*;
import java.util.Map;
import java.util.UUID;

@RestController
@RequestMapping("/archivos")
@CrossOrigin(origins = "http://localhost:5173")
public class archivoController {

    private final Path fileStorageLocation;
    private final String fileServingPath;

    public archivoController(@Value("${file.storage.location}") String storageLocation,
                             @Value("${file.serving.path}") String servingPath) {
        this.fileStorageLocation = Paths.get(storageLocation).toAbsolutePath().normalize();
        this.fileServingPath = servingPath;

        try {
            Files.createDirectories(this.fileStorageLocation);
        } catch (Exception ex) {
            throw new RuntimeException("No se pudo crear el directorio donde se guardarán los archivos subidos.", ex);
        }
    }

    @PostMapping("/upload")
    public ResponseEntity<?> uploadFile(@RequestParam("file") MultipartFile file) {
        if (file.isEmpty()) {
            return ResponseEntity.badRequest().body("Archivo vacío");
        }

        try {
            String originalFilename = file.getOriginalFilename();
            String safeOriginalFilename = (originalFilename != null) ? originalFilename.replaceAll("[^a-zA-Z0-9._-]", "") : "unknownfile";
            String filename = UUID.randomUUID().toString() + "_" + safeOriginalFilename;
            String cleanedFilename = StringUtils.cleanPath(filename);

            if (cleanedFilename.contains("..")) {
                return ResponseEntity.status(HttpStatus.BAD_REQUEST).body("Nombre de archivo inválido: contiene secuencia de path no permitida.");
            }

            if (cleanedFilename.length() > 200) {
                 return ResponseEntity.status(HttpStatus.BAD_REQUEST).body("Nombre de archivo inválido: demasiado largo.");
            }

            Path targetLocation = this.fileStorageLocation.resolve(cleanedFilename);
            Files.copy(file.getInputStream(), targetLocation, StandardCopyOption.REPLACE_EXISTING);

            String normalizedServingPath = this.fileServingPath.endsWith("/") ? this.fileServingPath : this.fileServingPath + "/";
            String url = normalizedServingPath + cleanedFilename;

            return ResponseEntity.ok(Map.of("url", url, "filename", cleanedFilename));
        } catch (IOException ex) {
            ex.printStackTrace();
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body("Error al subir archivo: " + ex.getMessage());
        } catch (Exception ex) {
            ex.printStackTrace();
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body("Error inesperado durante la subida del archivo: " + ex.getMessage());
        }
    }
}
