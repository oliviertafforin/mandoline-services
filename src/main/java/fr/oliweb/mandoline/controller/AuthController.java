package fr.oliweb.mandoline.controller;

import fr.oliweb.mandoline.dtos.LoginRequeteDTO;
import fr.oliweb.mandoline.dtos.UtilisateurDTO;
import fr.oliweb.mandoline.exceptions.RessourceEnDoubleException;
import fr.oliweb.mandoline.service.UtilisateurService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.Valid;
import jakarta.validation.ValidationException;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api/auth")
@Tag(name = "Authentification", description = "API pour l'authentification des utilisateurs")
public class AuthController {

    private final UtilisateurService utilisateurService;

    public AuthController(UtilisateurService utilisateurService) {
        this.utilisateurService = utilisateurService;
    }

    @PostMapping("/register")
    @Operation(summary = "Créer un nouveau compte utilisateur",
            description = "Enregistre un nouvel utilisateur dans le système")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "201", description = "Utilisateur créé avec succès"),
            @ApiResponse(responseCode = "400", description = "Données invalides"),
            @ApiResponse(responseCode = "409", description = "Utilisateur déjà existant")
    })
    public ResponseEntity<UtilisateurDTO> registerUser(@Valid @RequestBody final LoginRequeteDTO loginRequeteDTO) {
        try {
            return ResponseEntity.ok(utilisateurService.nouvelUtilisateur(loginRequeteDTO));
        } catch (ValidationException ve) {
            throw new RessourceEnDoubleException(ve.getMessage());
        }
    }

    @PostMapping("/login")
    @Operation(summary = "Authentifier un utilisateur",
            description = "Authentifie un utilisateur et retourne un token JWT")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Authentification réussie"),
            @ApiResponse(responseCode = "400", description = "Données invalides"),
            @ApiResponse(responseCode = "401", description = "Identifiants incorrects")
    })
    public ResponseEntity<String> login(@Valid @RequestBody final LoginRequeteDTO loginRequeteDTO) {
        String token = utilisateurService.authentifierUtilisateur(loginRequeteDTO);
        return ResponseEntity.ok(token);
    }

}