package fr.oliweb.mandoline.controller;

import fr.oliweb.mandoline.dtos.ImageDTO;
import fr.oliweb.mandoline.exceptions.ExceptionMessages;
import fr.oliweb.mandoline.exceptions.RessourceIntrouvableException;
import fr.oliweb.mandoline.service.ImageService;
import fr.oliweb.mandoline.validation.ValidUUID;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.Parameter;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.annotation.PostConstruct;
import jakarta.validation.Valid;
import jakarta.validation.constraints.NotNull;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.core.io.InputStreamResource;
import org.springframework.http.HttpHeaders;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.UUID;

@RestController
@RequestMapping("/api/images")
@Tag(name = "Images", description = "API pour gérer les images")
public class ImageController {
    @Value("${file.upload-dir}")
    private String uploadDir;

    private final ImageService imageService;

    private final Logger logger = LoggerFactory.getLogger(ImageController.class);

    public ImageController(ImageService imageService) {
        this.imageService = imageService;
    }

    @PostConstruct
    public void init() {
        logger.info("Upload directory configured: {}", uploadDir);
    }

    // Récupérer une image par ID
    @GetMapping("/{id}")
    @Operation(summary = "Obtenir les métadonnées d'une image",
            description = "Renvoie les informations d'une image")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Image trouvée"),
            @ApiResponse(responseCode = "404", description = "Image introuvable")
    })
    public ResponseEntity<ImageDTO> getImageParId(@PathVariable @ValidUUID UUID id) {
        return imageService.getImageParId(id)
                .map(ResponseEntity::ok)
                .orElseThrow(() -> new RessourceIntrouvableException(ExceptionMessages.IMAGE_INTROUVABLE + ", id : " + id));
    }


    @GetMapping("/{id}/download")
    @Operation(summary = "Télécharger une image",
            description = "Télécharge le fichier image correspondant à l'ID")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Image téléchargée"),
            @ApiResponse(responseCode = "404", description = "Image introuvable")
    })
    public ResponseEntity<InputStreamResource> downloadImage(@PathVariable @ValidUUID UUID id) throws IOException {
        ImageDTO image = imageService.getImageParId(id).orElseThrow(FileNotFoundException::new);

        // Construire le chemin du fichier en utilisant l'UUID
        Path filePath = Paths.get(uploadDir, image.getPath());
        File file = filePath.toFile();

        if (!file.exists() || !file.isFile()) {
            throw new RessourceIntrouvableException(ExceptionMessages.IMAGE_INTROUVABLE + ", id : " + id);
        }

        try {
            InputStreamResource resource = new InputStreamResource(new FileInputStream(file));

            return ResponseEntity.ok()
                    .header(HttpHeaders.CONTENT_DISPOSITION, "attachment;filename=" + file.getName())
                    .contentType(MediaType.APPLICATION_OCTET_STREAM)
                    .contentLength(file.length())
                    .body(resource);
        } catch (IOException e) {
            logger.error("Error reading file", e);
            throw new FileNotFoundException(e.getMessage());
        }
    }

    @PostMapping("/upload")
    @Operation(summary = "Uploader un fichier image",
            description = "Upload un fichier pour une image existante")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Image uploadée avec succès"),
            @ApiResponse(responseCode = "400", description = "Fichier invalide"),
            @ApiResponse(responseCode = "404", description = "Image introuvable"),
            @ApiResponse(responseCode = "413", description = "Fichier trop volumineux")
    })
    public ResponseEntity<ImageDTO> uploadImage(@RequestParam @ValidUUID UUID id,
                                                @Parameter(description = "Fichier image à uploader")
                                                @RequestParam("file") @NotNull MultipartFile file) throws IOException {
        ImageDTO response = imageService.uploadImage(id, file);
        return ResponseEntity.ok(response);
    }

    @PostMapping
    @Operation(summary = "Créer une nouvelle image",
            description = "Crée une nouvelle entrée d'image sans fichier")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "201", description = "Image créée"),
            @ApiResponse(responseCode = "400", description = "Données invalides")
    })
    public ResponseEntity<ImageDTO> createImage(@Valid @RequestBody ImageDTO imageDTO) {
        ImageDTO createdImage = imageService.creerImage(imageDTO);
        return ResponseEntity.ok().body(createdImage);
    }

    @PutMapping("/{id}")
    @Operation(summary = "Mettre à jour une image",
            description = "Met à jour les métadonnées d'une image")
    public ResponseEntity<ImageDTO> updateImage(
            @PathVariable @ValidUUID UUID id,
            @Valid @RequestBody ImageDTO imageDTO) {
        ImageDTO updatedImage = imageService.majImage(id, imageDTO);
        return ResponseEntity.ok(updatedImage);
    }

    @DeleteMapping("/{id}")
    @Operation(summary = "Supprimer une image",
            description = "Supprime une image et son fichier associé")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "204", description = "Image supprimée"),
            @ApiResponse(responseCode = "404", description = "Image introuvable")
    })
    public ResponseEntity<Void> deleteImage(@PathVariable @ValidUUID UUID id) {
        imageService.supprimerImage(id);
        return ResponseEntity.noContent().build();
    }
}
