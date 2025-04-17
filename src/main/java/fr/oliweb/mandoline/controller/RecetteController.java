package fr.oliweb.mandoline.controller;

import fr.oliweb.mandoline.dtos.RecetteDTO;
import fr.oliweb.mandoline.exceptions.ExceptionMessages;
import fr.oliweb.mandoline.exceptions.RessourceIntrouvableException;
import fr.oliweb.mandoline.service.RecetteService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.UUID;

@RestController
@RequestMapping("/api/recette")
@Tag(name = "Ingrédients", description = "API pour gérer les ingrédients")
public class RecetteController {

    private final RecetteService recetteService;

    public RecetteController(RecetteService recetteService) {
        this.recetteService = recetteService;
    }

    // Récupérer toutes les recettes
    @GetMapping
    public ResponseEntity<List<RecetteDTO>> getAllRecettes() {
        List<RecetteDTO> recettes = recetteService.getAllRecettes();
        return ResponseEntity.ok(recettes);
    }

    //Toutes les recettes, avec pagination
    @GetMapping("/pagination")
    public Page<RecetteDTO> getAllRecettesPaginees(@RequestParam(defaultValue = "0") int page,
                                                   @RequestParam(defaultValue = "10") int size,
                                                   @RequestParam(required = false) String nom,
                                                   @RequestParam(required = false) List<String> criteres,
                                                   Pageable pageable) {
        return recetteService.getAllRecettes(pageable, nom, criteres);
    }

    // Récupérer une recette par ID
    @GetMapping("/{id}")
    @Operation(summary = "Obtenir une recette", description = "Renvoie la recette correspondant à l'id donné")
    public ResponseEntity<RecetteDTO> getRecetteParId(@PathVariable UUID id) {
        return recetteService.getRecetteParId(id)
                .map(ResponseEntity::ok)
                .orElseThrow(()-> new RessourceIntrouvableException(ExceptionMessages.RECETTE_INTROUVABLE + ", id : "+id));
    }

    // Créer une recette
    @PostMapping
    public ResponseEntity<RecetteDTO> creerRecette(@RequestBody RecetteDTO recetteDTO) {
        RecetteDTO recette = recetteService.creerRecette(recetteDTO);
        return ResponseEntity.status(HttpStatus.CREATED).body(recette);
    }

    // Mettre à jour une recette
    @PutMapping("/{id}")
    public ResponseEntity<RecetteDTO> majRecette(@PathVariable UUID id, @RequestBody RecetteDTO recetteDTO) {
        RecetteDTO recette = recetteService.majRecette(id, recetteDTO);
        return ResponseEntity.ok(recette);
    }

    // Supprimer une recette
    @DeleteMapping("/{id}")
    public ResponseEntity<Void> supprimerRecette(@PathVariable UUID id) {
        recetteService.supprimerRecette(id);
        return ResponseEntity.ok().build();
    }
}
