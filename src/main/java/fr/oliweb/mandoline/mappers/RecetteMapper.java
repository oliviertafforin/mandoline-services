package fr.oliweb.mandoline.mappers;

import fr.oliweb.mandoline.dtos.RecetteDTO;
import fr.oliweb.mandoline.model.RecetteDb;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.stream.Collectors;

public class RecetteMapper {

    // Convert Recette to RecetteDTO
    public static RecetteDTO toDto(RecetteDb db) {
        if (db == null) {
            return null;
        }

        RecetteDTO dto = new RecetteDTO();
        dto.setNom(db.getNom());
        dto.setId(db.getId());
        dto.setIntroduction(db.getIntroduction());
        dto.setCategorie(db.getCategorie());
        dto.setEtapes(db.getEtapes());
        dto.setNbPersonnes(db.getNbPersonnes());
        dto.setTemperature(db.getTemperature());
        dto.setTpsPrepa(db.getTpsPrepa());
        dto.setTpsCuisson(db.getTpsCuisson());
        dto.setProprietaire(UtilisateurMapper.toDto(db.getProprietaire()));
        dto.setImage(ImageMapper.toDto(db.getImage()));

        if (db.getRecetteIngredients() != null) {
            dto.setRecetteIngredients(
                    RecetteIngredientMapper.toDtoList(
                            new ArrayList<>(db.getRecetteIngredients())
                    )
            );
        }

        return dto;
    }

    // Convert RecetteDTO to Recette
    public static RecetteDb toDb(RecetteDTO dto) {
        if (dto == null) {
            return null;
        }
        RecetteDb recette = new RecetteDb();
        if (dto.getId() != null) {
            recette.setId(dto.getId());
        }
        recette.setNom(dto.getNom());
        recette.setIntroduction(dto.getIntroduction());
        recette.setNbPersonnes(dto.getNbPersonnes());
        recette.setTemperature(dto.getTemperature());
        recette.setTpsPrepa(dto.getTpsPrepa());
        recette.setEtapes(dto.getEtapes());
        recette.setCategorie(dto.getCategorie());
        recette.setTpsCuisson(dto.getTpsCuisson());
        recette.setProprietaire(UtilisateurMapper.toDb(dto.getProprietaire()));
        recette.setImage(ImageMapper.toDb(dto.getImage()));
        if (dto.getRecetteIngredients() != null) {
            recette.setRecetteIngredients(
                    new HashSet<>(RecetteIngredientMapper.toEntityList(dto.getRecetteIngredients()))
            );
        }

        return recette;
    }

    // Convert a list of RecetteDb entities to a list of RecetteDTO
    public static List<RecetteDTO> toDtoList(List<RecetteDb> dbs) {
        if (dbs == null || dbs.isEmpty()) {
            return new ArrayList<>();
        }
        return dbs.stream().map(RecetteMapper::toDto).collect(Collectors.toList());
    }
}