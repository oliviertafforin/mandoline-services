package fr.oliweb.mandoline.controller;

import fr.oliweb.mandoline.dtos.IngredientDTO;
import fr.oliweb.mandoline.dtos.IngredientUtilisateurDTO;
import fr.oliweb.mandoline.service.IngredientService;
import fr.oliweb.mandoline.service.IngredientUtilisateurService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.UUID;

@RestController
@RequestMapping("/api/ingredient")
@Tag(name = "Ingrédients", description = "API pour gérer les ingrédients")
public class IngredientController {

    private final IngredientService ingredientService;
    private final IngredientUtilisateurService ingredientUtilisateurService;

    public IngredientController(IngredientService ingredientService, IngredientUtilisateurService ingredientUtilisateurService) {
        this.ingredientService = ingredientService;
        this.ingredientUtilisateurService = ingredientUtilisateurService;
    }

    // Récupérer tous les ingrédients
    @GetMapping
    public ResponseEntity<List<IngredientDTO>> getAllIngredients() {
        List<IngredientDTO> ingredients = ingredientService.getAllIngredients();
        return ResponseEntity.ok(ingredients);
    }

    // Récupérer un ingredient par ID
    @GetMapping("/{id}")
    @Operation(summary = "Obtenir une ingredient", description = "Renvoie l'ingredient correspondant à l'id donné")
    public ResponseEntity<IngredientDTO> getIngredientParId(@PathVariable UUID id) {
        return ingredientService.getIngredientParId(id)
                .map(ResponseEntity::ok)
                .orElse(ResponseEntity.notFound().build());
    }

    // Récupérer un ingredient d'utilisateur par ID et utilisateur
    @GetMapping("/{id}/utilisateur/{userId}")
    @Operation(summary = "Obtenir un ingredient d'utilisateur", description = "Renvoie l'ingredient d'utilisateur correspondant à l'id donné")
    public ResponseEntity<IngredientUtilisateurDTO> getIngredientUtilisateurParId(@PathVariable UUID id, @PathVariable UUID userId) {
        return ingredientUtilisateurService.getIngredientUtilisateurParId(id, userId)
                .map(ResponseEntity::ok)
                .orElse(ResponseEntity.noContent().build());
    }

    // Créer un ingredient
    @PostMapping
    public ResponseEntity<IngredientDTO> creerIngredient(@RequestBody IngredientDTO ingredientDTO) {
        IngredientDTO ingredient = ingredientService.creerIngredient(ingredientDTO);
        return ResponseEntity.status(HttpStatus.CREATED).body(ingredient);
    }

    // Mettre à jour un ingredient
    @PutMapping("/{id}")
    public ResponseEntity<IngredientDTO> majIngredient(@PathVariable UUID id, @RequestBody IngredientDTO ingredientDTO) {
        IngredientDTO ingredient = ingredientService.majIngredient(id, ingredientDTO);
        return ResponseEntity.ok(ingredient);
    }

    // Supprimer un ingredient
    @DeleteMapping("/{id}")
    public ResponseEntity<Void> supprimerIngredient(@PathVariable UUID id) {
        ingredientService.supprimerIngredient(id);
        return ResponseEntity.ok().build();
    }
}
