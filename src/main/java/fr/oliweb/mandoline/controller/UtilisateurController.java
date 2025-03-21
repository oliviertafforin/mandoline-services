package fr.oliweb.mandoline.controller;

import fr.oliweb.mandoline.dtos.UtilisateurDTO;
import fr.oliweb.mandoline.service.UtilisateurService;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.UUID;

@RestController
@RequestMapping("/api/utilisateur")
public class UtilisateurController {

    private final UtilisateurService utilisateurService;

    public UtilisateurController(UtilisateurService utilisateurService) {
        this.utilisateurService = utilisateurService;
    }

    // Récupérer tous les utilisateurs
    @GetMapping
    public ResponseEntity<List<UtilisateurDTO>> getAllUtilisateurs() {
        List<UtilisateurDTO> users = utilisateurService.getAllUtilisateurs();
        return ResponseEntity.ok(users);
    }

    // Récupérer un utilisateur par ID
    @GetMapping("/{id}")
    public ResponseEntity<UtilisateurDTO> getUtilisateurParId(@PathVariable UUID id) {
        return utilisateurService.getUtilisateurParId(id)
                .map(ResponseEntity::ok)
                .orElse(ResponseEntity.notFound().build());
    }

    // Créer un utilisateur
    @PostMapping
    public ResponseEntity<UtilisateurDTO> creerUtilisateur(@RequestBody UtilisateurDTO userDTO) {
        UtilisateurDTO utilisateur = utilisateurService.creerUtilisateur(userDTO);
        return ResponseEntity.status(HttpStatus.CREATED).body(utilisateur);
    }

    // Mettre à jour un utilisateur
    @PutMapping("/{id}")
    public ResponseEntity<UtilisateurDTO> majUtilisateur(@PathVariable UUID id, @RequestBody UtilisateurDTO userDTO) {
        UtilisateurDTO utilisateur = utilisateurService.majUtilisateur(id, userDTO);
        return ResponseEntity.ok(utilisateur);
    }

    // Supprimer un utilisateur
    @DeleteMapping("/{id}")
    public ResponseEntity<Void> supprimerUtilisateur(@PathVariable UUID id) {
        utilisateurService.supprimerUtilisateur(id);
        return ResponseEntity.ok().build();
    }
}
