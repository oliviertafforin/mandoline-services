package fr.oliweb.mandoline.controller;

import fr.oliweb.mandoline.dtos.IngredientDTO;
import fr.oliweb.mandoline.exceptions.ExceptionMessages;
import fr.oliweb.mandoline.exceptions.RessourceIntrouvableException;
import fr.oliweb.mandoline.service.IngredientService;
import fr.oliweb.mandoline.validation.ValidUUID;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.Valid;
import jakarta.validation.constraints.Max;
import jakarta.validation.constraints.Min;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.UUID;

@RestController
@RequestMapping("/api/ingredients")
@Tag(name = "Ingrédients", description = "API pour gérer les ingrédients")
public class IngredientController {

    private final IngredientService ingredientService;

    public IngredientController(IngredientService ingredientService) {
        this.ingredientService = ingredientService;
    }

    @GetMapping("/all")
    @Operation(summary = "Retourne la liste de tous les ingrédients", description = "Retourne la liste de tous les ingrédients)")
    public ResponseEntity<List<IngredientDTO>> getAllIngredients() {
        List<IngredientDTO> ingredients = ingredientService.getAllIngredients();
        return ResponseEntity.ok(ingredients);
    }

    @GetMapping
    @Operation(summary = "Retourne une liste d'ingrédients", description = "Renvoie la liste des ingrédients avec la pagination demandée")
    public ResponseEntity<Page<IngredientDTO>> getAllIngredientsWithPagination(@RequestParam(defaultValue = "0") @Min(0) int page,
                                                                               @RequestParam(defaultValue = "10") @Min(1) @Max(100) int size) {
        Pageable pageable = PageRequest.of(page, size);
        Page<IngredientDTO> ingredients = ingredientService.getAllIngredients(pageable);
        return ResponseEntity.ok(ingredients);
    }

    // Récupérer un ingredient par ID
    @GetMapping("/{id}")
    @Operation(summary = "Obtenir un ingredient", description = "Renvoie l'ingredient correspondant à l'id donné")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Ingrédient trouvé"),
            @ApiResponse(responseCode = "400", description = "UUID invalide"),
            @ApiResponse(responseCode = "404", description = "Ingrédient introuvable")
    })
    public ResponseEntity<IngredientDTO> getIngredientById(@PathVariable @ValidUUID UUID id) {
        return ingredientService.getIngredientParId(id)
                .map(ResponseEntity::ok)
                .orElseThrow(() -> new RessourceIntrouvableException(ExceptionMessages.INGREDIENT_INTROUVABLE + ", id : " + id));
    }

    @PostMapping
    @Operation(
            summary = "Créer un nouvel ingrédient",
            description = "Crée un nouvel ingrédient dans le système"
    )
    @ApiResponses(value = {
            @ApiResponse(responseCode = "201", description = "Ingrédient créé avec succès"),
            @ApiResponse(responseCode = "400", description = "Données invalides"),
            @ApiResponse(responseCode = "409", description = "Ingrédient déjà existant")
    })
    public ResponseEntity<IngredientDTO> createIngredient(@Valid @RequestBody IngredientDTO ingredientDTO) {
        IngredientDTO ingredient = ingredientService.creerIngredient(ingredientDTO);
        return ResponseEntity.status(HttpStatus.CREATED).body(ingredient);
    }

    @PutMapping("/{id}")
    @Operation(summary = "Mettre à jour un ingredient", description = "Met à jour l'ingrédient correspondant à l'id fourni avec les informations données")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Ingrédient mis à jour"),
            @ApiResponse(responseCode = "400", description = "Données invalides"),
            @ApiResponse(responseCode = "404", description = "Ingrédient introuvable")
    })
    public ResponseEntity<IngredientDTO> updateIngredient(@PathVariable @ValidUUID UUID id, @Valid @RequestBody IngredientDTO ingredientDTO) {
        IngredientDTO ingredient = ingredientService.majIngredient(id, ingredientDTO);
        return ResponseEntity.ok(ingredient);
    }

    @DeleteMapping("/{id}")
    @Operation(summary = "Supprime un ingredient", description = "Supprime l'ingrédient correspondant à l'id fourni")
    public ResponseEntity<Void> deleteIngredient(@PathVariable @ValidUUID UUID id) {
        ingredientService.supprimerIngredient(id);
        return ResponseEntity.noContent().build();
    }
}
