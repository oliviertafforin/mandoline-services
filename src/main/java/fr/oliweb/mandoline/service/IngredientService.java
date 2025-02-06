package fr.oliweb.mandoline.service;

import fr.oliweb.mandoline.dtos.IngredientDTO;
import fr.oliweb.mandoline.mappers.ImageMapper;
import fr.oliweb.mandoline.model.IngredientDb;
import fr.oliweb.mandoline.repository.IngredientRepository;
import org.springframework.stereotype.Service;

import java.util.HashSet;
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
        IngredientDb ingredient = toEntity(ingredientDTO);
        IngredientDb savedIngredient = repository.save(ingredient);
        return toDTO(savedIngredient);
    }

    public IngredientDTO majIngredient(UUID id, IngredientDTO ingredientDTO) {
        return repository.findById(id).map(ingredient -> {
            IngredientDb ingredientMaj = toEntity(ingredientDTO);
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
    private IngredientDTO toDTO(IngredientDb ingredient) {
        IngredientDTO ingredientDTO = new IngredientDTO();
        ingredientDTO.setId(ingredient.getId());
        ingredientDTO.setNom(ingredient.getNom());
        if (ingredient.getImage() != null) {
            ingredientDTO.setImage(ImageMapper.toDto(ingredient.getImage()));
        }
        return ingredientDTO;
    }

    // Mapper pour transformer un DTO en entité
    private IngredientDb toEntity(IngredientDTO ingredientDTO) {
        IngredientDb ingredient = new IngredientDb();
        if (ingredientDTO.getId() != null) {
            ingredient.setId(ingredientDTO.getId());
        }
        ingredient.setImage(ImageMapper.toDb(ingredientDTO.getImage()));
        ingredient.setNom(ingredientDTO.getNom());
        ingredient.setRecetteIngredients(new HashSet<>(ingredientDTO.getRecetteIngredients()));
        return ingredient;
    }
}


