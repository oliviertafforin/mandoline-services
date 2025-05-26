package fr.oliweb.mandoline.controller;

import fr.oliweb.mandoline.dtos.IngredientDTO;
import fr.oliweb.mandoline.dtos.IngredientUtilisateurDTO;
import fr.oliweb.mandoline.dtos.UtilisateurDTO;
import fr.oliweb.mandoline.exceptions.RessourceIntrouvableException;
import fr.oliweb.mandoline.service.IngredientUtilisateurService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.UUID;

@RestController
@RequestMapping("/api/utilisateurs/{userId}/ingredients")
@Tag(name = "Ingrédients par utilisateur", description = "API pour gérer les données d'ingrédients spécifiques par utilisateur")
public class IngredientUtilisateurController {

    private final IngredientUtilisateurService ingredientUtilisateurService;

    public IngredientUtilisateurController(IngredientUtilisateurService ingredientUtilisateurService) {
        this.ingredientUtilisateurService = ingredientUtilisateurService;
    }

    // Récupérer un ingredient d'utilisateur par ID et utilisateur
    @GetMapping("/{id}")
    @Operation(summary = "Obtenir un ingredient d'utilisateur", description = "Renvoie l'ingredient d'utilisateur correspondant à l'id donné")
    public ResponseEntity<IngredientUtilisateurDTO> getIngredientUtilisateurParId(@PathVariable UUID id, @PathVariable UUID userId) {
        return ingredientUtilisateurService.getIngredientUtilisateurParId(id, userId)
                .map(ResponseEntity::ok)
                .orElseThrow(() -> new RessourceIntrouvableException("Ingrédient id " + id + " introuvable pour l'utilisateur " + userId));
    }

    // Mettre à jour un ingredient d'utilisateur par ID et utilisateur
    @PutMapping("/{id}")
    @Operation(summary = "Mettre à jour un ingredient d'utilisateur", description = "Met à jour l'ingredient d'utilisateur correspondant à l'id donné")
    public ResponseEntity<IngredientUtilisateurDTO> majIngredientUtilisateurParId(@PathVariable UUID id, @PathVariable UUID userId, @RequestBody IngredientUtilisateurDTO ingredientUtilisateurDTO) {
        IngredientUtilisateurDTO ingredientUtilisateur = ingredientUtilisateurService.majOuCreerIngredientUtilisateur(id, userId, ingredientUtilisateurDTO);
        return ResponseEntity.ok(ingredientUtilisateur);
    }

    // Créer un ingredient utilisateur
    @PostMapping("/{id}")
    public ResponseEntity<IngredientUtilisateurDTO> creerIngredientUtilisateur(@PathVariable UUID id, @PathVariable UUID userId, @RequestBody IngredientUtilisateurDTO ingredientUtilisateurDTO) {
        ingredientUtilisateurDTO.setUtilisateur(new UtilisateurDTO(userId));
        ingredientUtilisateurDTO.setIngredient(new IngredientDTO(id));
        IngredientUtilisateurDTO ingredient = ingredientUtilisateurService.creerIngredientUtilisateur(ingredientUtilisateurDTO);
        return ResponseEntity.status(HttpStatus.CREATED).body(ingredient);
    }
}
