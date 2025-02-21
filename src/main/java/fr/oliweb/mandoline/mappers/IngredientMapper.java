package fr.oliweb.mandoline.mappers;

import fr.oliweb.mandoline.dtos.IngredientDTO;
import fr.oliweb.mandoline.model.IngredientDb;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.stream.Collectors;

public class IngredientMapper {

    // Convert Ingredient to IngredientDTO
    public static IngredientDTO toDto(IngredientDb db) {
        if (db == null) {
            return null;
        }

        IngredientDTO dto = new IngredientDTO();
        dto.setId(db.getId()); // Map the ID
        dto.setNom(db.getNom()); // Map the name

        // Map the Image (assume ImageMapper exists)
        if (db.getImage() != null) {
            dto.setImage(ImageMapper.toDto(db.getImage()));
        }

        if (db.getRecetteIngredients() != null) {
            dto.setRecetteIngredients(RecetteIngredientMapper.toDtoList(db.getRecetteIngredients().stream().toList()));
        }

        return dto;
    }

    // Convert IngredientDTO to IngredientDb
    public static IngredientDb toDb(IngredientDTO dto) {
        if (dto == null) {
            return null;
        }
        IngredientDb db = new IngredientDb();
        if (dto.getId() != null) {
            db.setId(dto.getId());
        }
        db.setNom(dto.getNom()); // Map the name

        // Mappe l'image
        if (dto.getImage() != null) {
            db.setImage(ImageMapper.toDb(dto.getImage()));
        }

        if (dto.getRecetteIngredients() != null) {
            db.setRecetteIngredients(new HashSet<>(RecetteIngredientMapper.toEntityList(new ArrayList<>(dto.getRecetteIngredients()))));
        }
        return db;
    }

    // Convert a list of Ingredient entities to a list of IngredientDTOs
    public static List<IngredientDTO> toDtoList(List<IngredientDb> dbs) {
        if (dbs == null || dbs.isEmpty()) {
            return new ArrayList<>();
        }

        return dbs.stream().map(IngredientMapper::toDto).collect(Collectors.toList());
    }

    // Convert a list of IngredientDTOs to a list of Ingredient entities
    public static List<IngredientDb> toDbList(List<IngredientDTO> dtos) {
        if (dtos == null || dtos.isEmpty()) {
            return new ArrayList<>();
        }

        return dtos.stream().map(IngredientMapper::toDb).collect(Collectors.toList());
    }
}
