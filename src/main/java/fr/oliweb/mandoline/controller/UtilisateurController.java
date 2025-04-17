package fr.oliweb.mandoline.controller;

import fr.oliweb.mandoline.dtos.RecetteDTO;
import fr.oliweb.mandoline.dtos.UtilisateurDTO;
import fr.oliweb.mandoline.exceptions.ExceptionMessages;
import fr.oliweb.mandoline.exceptions.RessourceIntrouvableException;
import fr.oliweb.mandoline.service.RecetteLikeeService;
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
    private final RecetteLikeeService recetteLikeeService;

    public UtilisateurController(UtilisateurService utilisateurService, RecetteLikeeService recetteLikeeService) {
        this.utilisateurService = utilisateurService;
        this.recetteLikeeService = recetteLikeeService;
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
                .orElseThrow(()-> new RessourceIntrouvableException(ExceptionMessages.UTILISATEUR_INTROUVABLE +", id : "+id));
    }

    // Récupérer les recettes préférées d'un utilisateur par son ID
    @GetMapping("/{id}/recettes-preferees")
    public ResponseEntity<List<RecetteDTO>> getRecettesPrefereesParUtilisateur(@PathVariable UUID id) {
        return utilisateurService.getUtilisateurParId(id)
                .map(utilisateurDTO -> {
                    List<RecetteDTO> recettesPreferees = recetteLikeeService.getRecettesPreferees(utilisateurDTO);
                    return ResponseEntity.ok(recettesPreferees);
                })
                .orElseThrow(()-> new RessourceIntrouvableException(ExceptionMessages.UTILISATEUR_INTROUVABLE +", id : "+id));
    }

    // Récupérer les recettes préférées d'un utilisateur par son ID
    @PostMapping("/{idUtilisateur}/recettes-preferees/{idRecette}")
    public ResponseEntity<Void> likeRecette(@PathVariable UUID idUtilisateur, @PathVariable UUID idRecette) {
        recetteLikeeService.likeRecette(idUtilisateur, idRecette);
        return ResponseEntity.ok().build();
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
