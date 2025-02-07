package fr.oliweb.mandoline.controller;

import fr.oliweb.mandoline.dtos.LoginRequeteDTO;
import fr.oliweb.mandoline.dtos.UtilisateurDTO;
import fr.oliweb.mandoline.service.UtilisateurService;
import jakarta.validation.Valid;
import jakarta.validation.ValidationException;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api/auth")
public class AuthController {

    private final UtilisateurService utilisateurService;

    public AuthController(UtilisateurService utilisateurService) {
        this.utilisateurService = utilisateurService;
    }

    @PostMapping("/register")
    public ResponseEntity registerUser(@Valid @RequestBody final LoginRequeteDTO loginRequeteDTO) {
        try{
            utilisateurService.nouvelUtilisateur(loginRequeteDTO);
        } catch (ValidationException ve){
            return ResponseEntity.status(HttpStatus.CONFLICT).body(ve.getMessage());
        }

        return ResponseEntity.ok().build();
    }

    @PostMapping("/login")
    public ResponseEntity<String> login(@RequestBody LoginRequeteDTO loginRequeteDTO) {
        String token = utilisateurService.authentifierUtilisateur(loginRequeteDTO);
        return ResponseEntity.ok(token);
    }

}