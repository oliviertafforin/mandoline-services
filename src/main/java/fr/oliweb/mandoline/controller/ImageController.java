package fr.oliweb.mandoline.controller;

import fr.oliweb.mandoline.dtos.ImageDTO;
import fr.oliweb.mandoline.exceptions.ExceptionMessages;
import fr.oliweb.mandoline.exceptions.RessourceIntrouvableException;
import fr.oliweb.mandoline.service.ImageService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.core.io.InputStreamResource;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.UUID;

@RestController
@RequestMapping("/api/image")
@Tag(name = "Images", description = "API pour gérer les images")
public class ImageController {

    private final ImageService imageService;

    @Value("${file.upload-dir}")
    private String uploadDir;

    private Logger logger = LoggerFactory.getLogger(ImageController.class);

    public ImageController(ImageService imageService) {
        this.imageService = imageService;
    }

    // Récupérer une image par ID
    @GetMapping("/{id}")
    @Operation(summary = "Obtenir une image", description = "Renvoie l'image correspondant à l'id donné")
    public ResponseEntity<ImageDTO> getImageParId(@PathVariable UUID id) {
        return imageService.getImageParId(id)
                .map(ResponseEntity::ok)
                .orElseThrow(() -> new RessourceIntrouvableException(ExceptionMessages.IMAGE_INTROUVABLE + ", id : " + id));
    }


    @GetMapping("/download/{id}")
    public ResponseEntity<InputStreamResource> telechargerImage(@PathVariable UUID id) throws FileNotFoundException {

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
    public ResponseEntity<ImageDTO> televerserImage(@RequestParam("file") MultipartFile file, @RequestParam("id") UUID id) throws IOException {

        // Générer un nom de fichier unique ou utiliser le nom d'origine
        String fileName = file.getOriginalFilename();
        Path filePath = Paths.get(uploadDir, fileName);
        try {

            // Sauvegarder le fichier dans le répertoire
            Files.write(filePath, file.getBytes());

            //si on retrouve l'imageDTO
            ImageDTO majDto = imageService.getImageParId(id).map(imageDTO -> {
                //supprimer prec image
                try {
                    Files.delete(Path.of(imageDTO.getPath()));
                } catch (Exception e) {
                    logger.error("Suppression du fichier " + imageDTO.getPath() + " échouée. Celui n'est plus utilisé, il faudrait le supprimer manuellement.");
                }
                //maj path
                imageDTO.setPath(filePath.getFileName().toString());
                imageService.majImage(id, imageDTO);
                return imageDTO;
            }).orElseThrow(FileNotFoundException::new);

            return ResponseEntity.ok().body(majDto);
        } catch (IOException e) {
            throw new IOException("Fichier non trouvé : " + fileName);
        }
    }

    // Créer une image
    @PostMapping
    public ResponseEntity<ImageDTO> creerImage(@RequestBody ImageDTO imageDTO) {
        ImageDTO image = imageService.creerImage(imageDTO);
        return ResponseEntity.status(HttpStatus.CREATED).body(image);
    }

    // Mettre à jour un image
    @PutMapping("/{id}")
    public ResponseEntity<ImageDTO> majImage(@PathVariable UUID id, @RequestBody ImageDTO imageDTO) {
        ImageDTO image = imageService.majImage(id, imageDTO);
        return ResponseEntity.ok(image);
    }

    // Supprimer un image
    @DeleteMapping("/{id}")
    public ResponseEntity<Void> supprimerImage(@PathVariable UUID id) {
        imageService.supprimerImage(id);
        return ResponseEntity.ok().build();
    }
}
