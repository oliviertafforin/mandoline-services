package fr.oliweb.mandoline.service;

import fr.oliweb.mandoline.dtos.IngredientDTO;
import fr.oliweb.mandoline.mappers.IngredientMapper;
import fr.oliweb.mandoline.model.ImageDb;
import fr.oliweb.mandoline.model.IngredientDb;
import fr.oliweb.mandoline.repository.ImageRepository;
import fr.oliweb.mandoline.repository.IngredientRepository;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;
import java.util.UUID;

@Service
public class IngredientService {

    private final IngredientRepository repository;
    private final ImageRepository imageRepository;

    public IngredientService(IngredientRepository repository, ImageRepository imageRepository) {
        this.repository = repository;
        this.imageRepository = imageRepository;
    }

    public Optional<IngredientDTO> getIngredientParId(UUID id) {
        return repository.findById(id).map(IngredientMapper::toDto);
    }

    public List<IngredientDTO> getAllIngredients() {
        return IngredientMapper.toDtoList(repository.findAll());
    }

    public IngredientDTO creerIngredient(IngredientDTO ingredientDTO) {
        IngredientDb ingredient = IngredientMapper.toDb(ingredientDTO);
        IngredientDb savedIngredient = repository.save(ingredient);
        return IngredientMapper.toDto(savedIngredient);
    }

    public IngredientDTO majIngredient(UUID id, IngredientDTO ingredientDTO) {
        return repository.findById(id).map(updated -> {
            IngredientDb db = IngredientMapper.toDb(ingredientDTO);
            updated.setNom(db.getNom());
            if (!updated.getImage().getPath().equals(db.getImage().getPath())) {
                ImageDb nouvelleImage = new ImageDb();
                nouvelleImage.setLibelle(db.getNom());
                nouvelleImage.setPath(db.getImage().getPath());
                updated.setImage(imageRepository.save(nouvelleImage));
            }
            updated.setRecetteIngredients(db.getRecetteIngredients());
            return IngredientMapper.toDto(repository.save(updated));
        }).orElseThrow(() -> new RuntimeException("Ingrédient introuvable"));
    }

    public void supprimerIngredient(UUID id) {
        if (!repository.existsById(id)) {
            throw new RuntimeException("Ingredient introuvable");
        }
        repository.deleteById(id);
    }

}


