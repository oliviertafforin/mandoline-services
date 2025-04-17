package fr.oliweb.mandoline.service;

import fr.oliweb.mandoline.dtos.IngredientDTO;
import fr.oliweb.mandoline.exceptions.RessourceIntrouvableException;
import fr.oliweb.mandoline.mappers.IngredientMapper;
import fr.oliweb.mandoline.model.ImageDb;
import fr.oliweb.mandoline.model.IngredientDb;
import fr.oliweb.mandoline.repository.ImageRepository;
import fr.oliweb.mandoline.repository.IngredientRepository;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.Pageable;
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

    public Page<IngredientDTO> getAllIngredients(Pageable pageable) {
        Page<IngredientDb> result = repository.findAll(pageable);
        PageImpl<IngredientDTO> ingredientDTOS = new PageImpl<>(result
                .stream()
                .map(IngredientMapper::toDto)
                .toList(), pageable, result.getTotalElements());
        return ingredientDTOS;
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
        }).orElseThrow(() -> new RessourceIntrouvableException("Impossible de mettre à jour l'ingrédient car il est introuvable"));
    }

    public void supprimerIngredient(UUID id) {
        if (!repository.existsById(id)) {
            throw new RessourceIntrouvableException("Impossible de supprimer l'ingrédient car il est introuvable");
        }
        repository.deleteById(id);
    }

}


