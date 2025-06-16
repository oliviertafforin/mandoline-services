package fr.oliweb.mandoline.controller;

import fr.oliweb.mandoline.dtos.RecetteDTO;
import fr.oliweb.mandoline.dtos.UtilisateurDTO;
import fr.oliweb.mandoline.exceptions.ExceptionMessages;
import fr.oliweb.mandoline.exceptions.RessourceIntrouvableException;
import fr.oliweb.mandoline.service.RecetteLikeeService;
import fr.oliweb.mandoline.service.UtilisateurService;
import fr.oliweb.mandoline.validation.ValidUUID;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import jakarta.validation.Valid;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.UUID;

@RestController
@RequestMapping("/api/utilisateurs")
public class UtilisateurController {

    private final UtilisateurService utilisateurService;
    private final RecetteLikeeService recetteLikeeService;

    public UtilisateurController(UtilisateurService utilisateurService, RecetteLikeeService recetteLikeeService) {
        this.utilisateurService = utilisateurService;
        this.recetteLikeeService = recetteLikeeService;
    }

    @GetMapping
    @Operation(summary = "Retourne la liste de tous les utilisateurs", description = "Retourne la liste de tous les utilisateurs enregistrés)")
    public ResponseEntity<List<UtilisateurDTO>> getAllUtilisateurs() {
        List<UtilisateurDTO> users = utilisateurService.getAllUtilisateurs();
        return ResponseEntity.ok(users);
    }

    @GetMapping("/{id}")
    @Operation(summary = "Obtenir un utilisateur", description = "Renvoie l'utilisateur correspondant à l'id donné")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Utilisateur trouvé"),
            @ApiResponse(responseCode = "400", description = "UUID invalide"),
            @ApiResponse(responseCode = "404", description = "Utilisateur introuvable")
    })
    public ResponseEntity<UtilisateurDTO> getUtilisateurById(@PathVariable @ValidUUID UUID id) {
        return utilisateurService.getUtilisateurParId(id)
                .map(ResponseEntity::ok)
                .orElseThrow(() -> new RessourceIntrouvableException(ExceptionMessages.UTILISATEUR_INTROUVABLE + ", id : " + id));
    }

    @GetMapping("/{id}/recettes-preferees")
    @Operation(summary = "Obtenir les recettes préférées d'un utilisateur", description = "Permet de lister toutes les recettes likées par un utilisateur donné")
    public ResponseEntity<List<RecetteDTO>> getRecettesPrefereesByUtilisateurId(@PathVariable @ValidUUID UUID id) {
        return ResponseEntity.ok(utilisateurService.getRecettePrefereesByUtilisateur(id));

    }

    @PostMapping("/{idUtilisateur}/recettes-preferees/{idRecette}")
    @Operation(summary = "Like/Unlike une recette", description = "Permet à un utilisateur de liker une recette, ou de la supprimer de ses likes si déjà existante")
    public ResponseEntity<Void> likeRecette(@PathVariable @ValidUUID UUID idUtilisateur, @PathVariable @ValidUUID UUID idRecette) {
        recetteLikeeService.likeRecette(idUtilisateur, idRecette);
        return ResponseEntity.ok().build();
    }

    @PostMapping
    @Operation(
            summary = "Créer un nouvel utilisateur",
            description = "Crée un nouvel utilisateur dans le système"
    )
    @ApiResponses(value = {
            @ApiResponse(responseCode = "201", description = "Utilisateur créé avec succès"),
            @ApiResponse(responseCode = "400", description = "Données invalides"),
            @ApiResponse(responseCode = "409", description = "Utilisateur déjà existant")
    })
    public ResponseEntity<UtilisateurDTO> createUtilisateur(@RequestBody @Valid UtilisateurDTO userDTO) {
        UtilisateurDTO utilisateur = utilisateurService.creerUtilisateur(userDTO);
        return ResponseEntity.status(HttpStatus.CREATED).body(utilisateur);
    }

    @PutMapping("/{id}")
    @Operation(summary = "Mettre à jour un utilisateur", description = "Met à jour l'utilisateur correspondant à l'id fourni avec les informations données")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "utilisateur mis à jour"),
            @ApiResponse(responseCode = "400", description = "Données invalides"),
            @ApiResponse(responseCode = "404", description = "utilisateur introuvable")
    })
    public ResponseEntity<UtilisateurDTO> updateUtilisateur(@PathVariable @ValidUUID UUID id, @RequestBody @Valid UtilisateurDTO userDTO) {
        UtilisateurDTO utilisateur = utilisateurService.majUtilisateur(id, userDTO);
        return ResponseEntity.ok(utilisateur);
    }

    @DeleteMapping("/{id}")
    @Operation(summary = "Supprime un utilisateur", description = "Supprime l'utilisateur correspondant à l'id fourni")
    public ResponseEntity<Void> deleteUtilisateur(@PathVariable @ValidUUID UUID id) {
        utilisateurService.supprimerUtilisateur(id);
        return ResponseEntity.ok().build();
    }
}
