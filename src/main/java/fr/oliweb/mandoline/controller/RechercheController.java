package fr.oliweb.mandoline.controller;

import fr.oliweb.mandoline.dtos.ResultatRechercheDTO;
import fr.oliweb.mandoline.service.RechercheService;
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
    public List<ResultatRechercheDTO> recherche(@PathVariable String query) {
        return searchService.recherche(query);
    }

    @GetMapping
    public List<ResultatRechercheDTO> prechargement() {
        return searchService.prechargement();
    }
}