package fr.oliweb.mandoline.service;

import fr.oliweb.mandoline.dtos.IngredientDTO;
import fr.oliweb.mandoline.dtos.RecetteDTO;
import fr.oliweb.mandoline.dtos.ResultatRechercheDTO;
import fr.oliweb.mandoline.dtos.UtilisateurDTO;
import fr.oliweb.mandoline.mappers.IngredientMapper;
import fr.oliweb.mandoline.mappers.RecetteMapper;
import fr.oliweb.mandoline.mappers.UtilisateurMapper;
import fr.oliweb.mandoline.repository.IngredientRepository;
import fr.oliweb.mandoline.repository.RecetteRepository;
import fr.oliweb.mandoline.repository.UtilisateurRepository;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

@Service
public class RechercheService {

    private final RecetteRepository recetteRepository;
    private final IngredientRepository ingredientRepository;
    private final UtilisateurRepository utilisateurRepository;

    public RechercheService(RecetteRepository recetteRepository, IngredientRepository ingredientRepository, UtilisateurRepository utilisateurRepository) {
        this.recetteRepository = recetteRepository;
        this.ingredientRepository = ingredientRepository;
        this.utilisateurRepository = utilisateurRepository;
    }

    public List<ResultatRechercheDTO> prechargement() {

        // Rechercher les ingrédients correspondants
        List<IngredientDTO> ingredients = IngredientMapper.toDtoList(ingredientRepository.findAll());
        List<ResultatRechercheDTO> results = new ArrayList<>(ingredients.stream()
                .map(ingredient -> new ResultatRechercheDTO(
                        ingredient.getId(),
                        ingredient.getNom(),
                        "ingredients",
                        ingredient.getNom(),
                        ingredient.getImage()
                )).toList());

        // Rechercher les recettes correspondantes
        List<RecetteDTO> recettes = RecetteMapper.toDtoList(recetteRepository.findAll());
        results.addAll(recettes.stream()
                .map(recette -> new ResultatRechercheDTO(
                        recette.getId(),
                        recette.getNom(),
                        "recettes",
                        recette.getInstructions().length() > 50 ? recette.getInstructions().substring(0,50) + "..." : recette.getInstructions(),
                        recette.getImage()
                )).toList());

        // Rechercher les utilisateurs
        List<UtilisateurDTO> utilisateurs = UtilisateurMapper.toDtoList(utilisateurRepository.findAll());
        results.addAll(utilisateurs.stream()
                .map(utilisateur -> new ResultatRechercheDTO(
                        utilisateur.getId(),
                        utilisateur.getPseudo(),
                        "utilisateurs",
                        "",
                        utilisateur.getAvatar()
                )).toList());

        return results;
    }

    public List<ResultatRechercheDTO> recherche(String query) {

        // Rechercher les ingrédients correspondants
        List<IngredientDTO> ingredients = IngredientMapper.toDtoList(ingredientRepository.findByNomContainingIgnoreCase(query));
        List<ResultatRechercheDTO> results = new ArrayList<>(ingredients.stream()
                .map(ingredient -> new ResultatRechercheDTO(
                        ingredient.getId(),
                        ingredient.getNom(),
                        "ingredients",
                        ingredient.getNom(),
                        ingredient.getImage()
                )).toList());

        // Rechercher les recettes correspondantes
        List<RecetteDTO> recettes = RecetteMapper.toDtoList(recetteRepository.findByNomContainingIgnoreCase(query));
        results.addAll(recettes.stream()
                .map(recette -> new ResultatRechercheDTO(
                        recette.getId(),
                        recette.getNom(),
                        "recettes",
                        recette.getInstructions().length() > 50 ? recette.getInstructions().substring(50) + "..." : recette.getInstructions(),
                        recette.getImage()
                )).toList());

        // Rechercher les utilisateurs
        List<UtilisateurDTO> utilisateurs = UtilisateurMapper.toDtoList(utilisateurRepository.findByPseudoContainingIgnoreCase(query));
        results.addAll(utilisateurs.stream()
                .map(utilisateur -> new ResultatRechercheDTO(
                        utilisateur.getId(),
                        utilisateur.getPseudo(),
                        "utilisateurs",
                       "",
                        utilisateur.getAvatar()
                )).toList());

        return results;
    }
}


