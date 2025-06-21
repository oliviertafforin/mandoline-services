package fr.oliweb.mandoline.controller;

import fr.oliweb.mandoline.dtos.PageResponse;
import fr.oliweb.mandoline.dtos.RecetteDTO;
import fr.oliweb.mandoline.exceptions.ExceptionMessages;
import fr.oliweb.mandoline.exceptions.RessourceIntrouvableException;
import fr.oliweb.mandoline.service.RecetteService;
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
@RequestMapping("/api/recettes")
@Tag(name = "Recettes", description = "API pour gérer les recettes")
public class RecetteController {

    private final RecetteService recetteService;

    public RecetteController(RecetteService recetteService) {
        this.recetteService = recetteService;
    }

    @GetMapping("/all")
    @Operation(summary = "Retourne la liste de toutes les recettes", description = "Retourne la liste de toutes les recettes sans pagination")
    public ResponseEntity<List<RecetteDTO>> getAllRecettes() {
        List<RecetteDTO> recettes = recetteService.getAllRecettes();
        return ResponseEntity.ok(recettes);
    }

    @GetMapping
    @Operation(summary = "Retourne une liste de recettes", description = "Renvoie la liste des recettes avec la pagination demandée")
    public ResponseEntity<PageResponse<RecetteDTO>> getAllRecettesWithPagination(@RequestParam(defaultValue = "0") @Min(0) int page,
                                                                                 @RequestParam(defaultValue = "10") @Min(1) @Max(100) int size,
                                                                                 @RequestParam(required = false) String nom,
                                                                                 @RequestParam(required = false) List<String> criteres) {
        Pageable pageable = PageRequest.of(page, size);
        Page<RecetteDTO> allRecettes = recetteService.getAllRecettes(pageable, nom, criteres);
        return ResponseEntity.ok(new PageResponse<>(allRecettes));
    }

    @GetMapping("/{id}")
    @Operation(summary = "Obtenir une recette", description = "Renvoie la recette correspondant à l'id donné")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Recette trouvée"),
            @ApiResponse(responseCode = "400", description = "UUID invalide"),
            @ApiResponse(responseCode = "404", description = "Recette introuvable")
    })
    public ResponseEntity<RecetteDTO> getRecetteById(@PathVariable @ValidUUID UUID id) {
        return recetteService.getRecetteParId(id)
                .map(ResponseEntity::ok)
                .orElseThrow(()-> new RessourceIntrouvableException(ExceptionMessages.RECETTE_INTROUVABLE + ", id : "+id));
    }

    @PostMapping
    @Operation(
            summary = "Créer une nouvelle recette",
            description = "Crée une nouvelle recette"
    )
    @ApiResponses(value = {
            @ApiResponse(responseCode = "201", description = "Recette créée avec succès"),
            @ApiResponse(responseCode = "400", description = "Données invalides"),
            @ApiResponse(responseCode = "409", description = "Recette déjà existante")
    })
    public ResponseEntity<RecetteDTO> createRecette(@RequestBody @Valid RecetteDTO recetteDTO) {
        RecetteDTO recette = recetteService.creerRecette(recetteDTO);
        return ResponseEntity.status(HttpStatus.CREATED).body(recette);
    }

    @PutMapping("/{id}")
    @Operation(summary = "Mettre à jour une recette", description = "Met à jour la recette correspondante à l'id fourni avec les informations données")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Recette mise à jour"),
            @ApiResponse(responseCode = "400", description = "Données invalides"),
            @ApiResponse(responseCode = "404", description = "Recette introuvable")
    })
    public ResponseEntity<RecetteDTO> majRecette(@PathVariable @ValidUUID UUID id, @RequestBody @Valid RecetteDTO recetteDTO) {
        RecetteDTO recette = recetteService.majRecette(id, recetteDTO);
        return ResponseEntity.ok(recette);
    }

    @DeleteMapping("/{id}")
    @Operation(summary = "Supprime une recette", description = "Supprime la recette correspondante à l'id fourni")
    public ResponseEntity<Void> supprimerRecette(@PathVariable UUID id) {
        recetteService.supprimerRecette(id);
        return ResponseEntity.noContent().build();
    }
}
