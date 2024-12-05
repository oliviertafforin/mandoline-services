package fr.oliweb.mandoline.controller;

import fr.oliweb.mandoline.dto.ImageDTO;
import fr.oliweb.mandoline.service.ImageService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.UUID;

@RestController
@RequestMapping("/api/image")
@Tag(name = "Images", description = "API pour gérer les images")
public class ImageController {

    private final ImageService imageService;

    public ImageController(ImageService imageService) {
        this.imageService = imageService;
    }

    // Récupérer une image par ID
    @GetMapping("/{id}")
    @Operation(summary = "Obtenir une image", description = "Renvoie l'image correspondant à l'id donné")
    public ResponseEntity<ImageDTO> getImageParId(@PathVariable UUID id) {
        return imageService.getImageParId(id)
                .map(ResponseEntity::ok)
                .orElse(ResponseEntity.notFound().build());
    }

    // Créer un image
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
