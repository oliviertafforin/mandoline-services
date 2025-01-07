package fr.oliweb.mandoline.mappers;

import fr.oliweb.mandoline.dtos.IngredientDTO;
import fr.oliweb.mandoline.model.IngredientDb;
import fr.oliweb.mandoline.model.RecetteIngredientDb;
import fr.oliweb.mandoline.repository.ImageRepository;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.stream.Collectors;

public class IngredientMapper {
    // Convert Ingredient to IngredientDTO
    public static IngredientDTO toDto(IngredientDb db, ImageRepository imageRepository) {
        if (db == null) {
            return null;
        }

        IngredientDTO dto = new IngredientDTO();
        dto.setId(db.getId()); // Map the ID
        dto.setNom(db.getNom()); // Map the name

        // Map the Image (assume ImageMapper exists)
        if (db.getImage() != null) {
            dto.setImage(ImageMapper.toDto(imageRepository.findById(db.getImage()).orElse(null)));
        }

//        // Map the list of RecetteIngredients
//        if (db.getRecetteIngredients() != null) {
//            dto.setRecetteIngredients(
//                    new ArrayList<>(db.getRecetteIngredients()) // Convert Set to List
//            );
//        }

        return dto;
    }

    // Convert IngredientDTO to Ingredient
    public static IngredientDb toEntity(IngredientDTO dto) {
        if (dto == null) {
            return null;
        }

        IngredientDb entity = new IngredientDb();
        entity.setId(dto.getId()); // Map the ID
        entity.setNom(dto.getNom()); // Map the name

        // Mappe l'image
        if (dto.getImage() != null) {
            entity.setImage(ImageMapper.toDb(dto.getImage()).getId());
        }

        // Map la liste des RecetteIngredient
//        if (dto.getRecetteIngredients() != null) {
//            entity.setRecetteIngredients(
//                    new HashSet<>(dto.getRecetteIngredients()) // Convert List to Set
//            );
//        }

        return entity;
    }

    // Convert a list of Ingredient entities to a list of IngredientDTOs
    public static List<IngredientDTO> toDtoList(List<IngredientDb> dbs, ImageRepository imageRepository) {
        if (dbs == null || dbs.isEmpty()) {
            return null;
        }

        return dbs.stream().map(e -> toDto(e, imageRepository)).collect(Collectors.toList());
    }

    // Convert a list of IngredientDTOs to a list of Ingredient entities
    public static List<IngredientDb> toDbList(List<IngredientDTO> dtos) {
        if (dtos == null || dtos.isEmpty()) {
            return null;
        }

        return dtos.stream().map(IngredientMapper::toEntity).collect(Collectors.toList());
    }
}
