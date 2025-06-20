package fr.oliweb.mandoline.controller;

import fr.oliweb.mandoline.dtos.ResultatRechercheDTO;
import fr.oliweb.mandoline.service.RechercheService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@RequestMapping("/api/recherche")
@Tag(name = "Résultats de recherche", description = "API pour gérer les recherches de résultats dans l'application")
public class RechercheController {

    @Autowired
    private RechercheService searchService;

    @GetMapping("/{query}")
    @Operation(summary = "Effectue une recherche", description = "Effectue une recherche en base (ingrédients, recettes et utilisateurs) à partir d'une chaîne de caractères")
    public List<ResultatRechercheDTO> recherche(@PathVariable String query) {
        return searchService.recherche(query);
    }

    @GetMapping
    @Operation(summary = "Précharge la liste des résultats", description = "Précharge une liste de toutes les valeurs possibles à chercher (recettes, ingrédients, utilisateurs)")
    public List<ResultatRechercheDTO> prechargement() {
        return searchService.prechargement();
    }
}