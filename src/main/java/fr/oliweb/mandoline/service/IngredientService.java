package fr.oliweb.mandoline.service;

import fr.oliweb.mandoline.dto.IngredientDTO;
import fr.oliweb.mandoline.dto.UtilisateurDTO;
import fr.oliweb.mandoline.model.Image;
import fr.oliweb.mandoline.model.Ingredient;
import fr.oliweb.mandoline.model.Recette;
import fr.oliweb.mandoline.repository.IngredientRepository;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;
import java.util.UUID;

@Service
public class IngredientService {

    private final IngredientRepository repository;

    public IngredientService(IngredientRepository repository) {
        this.repository = repository;
    }

    public Optional<IngredientDTO> getIngredientParId(UUID id) {
        return repository.findById(id).map(this::toDTO);
    }

    public List<IngredientDTO> getAllIngredients() {
        return repository.findAll().stream()
                .map(this::toDTO)
                .toList();
    }


    public IngredientDTO creerIngredient(IngredientDTO ingredientDTO) {
        Ingredient ingredient = toEntity(ingredientDTO);
        Ingredient savedIngredient = repository.save(ingredient);
        return toDTO(savedIngredient);
    }

    public IngredientDTO majIngredient(UUID id, IngredientDTO ingredientDTO) {
        return repository.findById(id).map(ingredient -> {
            Ingredient ingredientMaj = toEntity(ingredientDTO);
            ingredientMaj.setId(ingredient.getId());
            return toDTO(repository.save(ingredient));
        }).orElseThrow(() -> new RuntimeException("Recette introuvable"));
    }

    public void supprimerIngredient(UUID id) {
        if (!repository.existsById(id)) {
            throw new RuntimeException("Ingredient introuvable");
        }
        repository.deleteById(id);
    }

    // Mapper pour transformer une entité en DTO
    private IngredientDTO toDTO(Ingredient ingredient) {
        IngredientDTO ingredientDTO = new IngredientDTO();
        ingredientDTO.setId(ingredient.getId());
        ingredientDTO.setNom(ingredient.getNom());
        getIngredientParId(ingredient.getId()).ifPresent(i -> ingredientDTO.setImage(i.getImage()));
        return ingredientDTO;
    }

    // Mapper pour transformer un DTO en entité
    private Ingredient toEntity(IngredientDTO ingredientDTO) {
        Ingredient ingredient = new Ingredient();
        if(ingredientDTO.getId() != null){
            ingredient.setId(ingredientDTO.getId());
        }
        ingredient.setImage(ingredientDTO.getImage().getId());
        ingredient.setNom(ingredientDTO.getNom());
        return ingredient;
    }
}


