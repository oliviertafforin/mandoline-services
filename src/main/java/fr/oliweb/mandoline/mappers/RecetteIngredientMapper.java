package fr.oliweb.mandoline.mappers;

import fr.oliweb.mandoline.dtos.RecetteIngredientDTO;
import fr.oliweb.mandoline.model.RecetteIngredientDb;
import fr.oliweb.mandoline.repository.ImageRepository;

import java.util.List;
import java.util.stream.Collectors;

public class RecetteIngredientMapper {

    // Convert RecetteIngredient to RecetteIngredientDTO
    public static RecetteIngredientDTO toDto(RecetteIngredientDb db, ImageRepository imageRepository) {
        if (db == null) {
            return null;
        }

        RecetteIngredientDTO dto = new RecetteIngredientDTO();
//        dto.setRecette(RecetteMapper.toDto(db.getRecette(), imageRepository)); // Map Recette
//        dto.setIngredient(IngredientMapper.toDto(db.getIngredient(), imageRepository)); // Map Ingredient
        dto.setQuantite(db.getQuantite());
        dto.setUnite(db.getUnite());
        dto.setOptionnel(db.getOptionnel());

        return dto;
    }

    // Convert RecetteIngredientDTO to RecetteIngredient
    public static RecetteIngredientDb toEntity(RecetteIngredientDTO dto) {
        if (dto == null) {
            return null;
        }

        RecetteIngredientDb entity = new RecetteIngredientDb();
        entity.setRecette(RecetteMapper.toDb(dto.getRecette())); // Map Recette
        entity.setIngredient(IngredientMapper.toEntity(dto.getIngredient())); // Map Ingredient
        entity.setQuantite(dto.getQuantite());
        entity.setUnite(dto.getUnite());
        entity.setOptionnel(dto.getOptionnel());

        return entity;
    }

    // Convert a List of RecetteIngredient to a List of RecetteIngredientDTO
    public static List<RecetteIngredientDTO> toDtoList(List<RecetteIngredientDb> dbs, ImageRepository imageRepository) {
        if (dbs == null || dbs.isEmpty()) {
            return null;
        }

        return dbs.stream().map(e -> toDto(e, imageRepository)).collect(Collectors.toList());
    }

    // Convert a List of RecetteIngredientDTO to a List of RecetteIngredient
    public static List<RecetteIngredientDb> toEntityList(List<RecetteIngredientDTO> dtos) {
        if (dtos == null || dtos.isEmpty()) {
            return null;
        }

        return dtos.stream().map(RecetteIngredientMapper::toEntity).collect(Collectors.toList());
    }
}
