package fr.oliweb.mandoline.controller;

import fr.oliweb.mandoline.dtos.IngredientDTO;
import fr.oliweb.mandoline.service.IngredientService;
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

    public IngredientController(IngredientService ingredientService) {
        this.ingredientService = ingredientService;
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
