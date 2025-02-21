package fr.oliweb.mandoline.mappers;

import fr.oliweb.mandoline.dtos.IngredientUtilisateurDTO;
import fr.oliweb.mandoline.model.IngredientUtilisateurDb;
import fr.oliweb.mandoline.repository.RemplacementRepository;
import org.springframework.util.StringUtils;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.stream.Collectors;

public class IngredientUtilisateurMapper {

    // Convert IngredientUtilisateurDb to IngredientUtilisateurDTO
    public static IngredientUtilisateurDTO toDto(IngredientUtilisateurDb db) {
        if (db == null) {
            return null;
        }

        IngredientUtilisateurDTO dto = new IngredientUtilisateurDTO();
        dto.setEviter(db.getEviter());
        dto.setUtilisateur(UtilisateurMapper.toDto(db.getUtilisateur()));
        dto.setIngredient(IngredientMapper.toDto(db.getIngredient()));
        dto.setPrixUnite(db.getPrixUnite());
        dto.setPrixKilo(db.getPrixKilo());
        if (StringUtils.hasLength(db.getSaison())) {
            dto.setSaison(Arrays.stream(db.getSaison().split(","))
                    .map(Integer::parseInt)
                    .toList());
        } else {
            dto.setSaison(new ArrayList<>());
        }

        return dto;
    }

    // Convert IngredientUtilisateurDTO to IngredientUtilisateurDb
    public static IngredientUtilisateurDb toEntity(IngredientUtilisateurDTO dto) {
        if (dto == null) {
            return null;
        }

        IngredientUtilisateurDb db = new IngredientUtilisateurDb();
        db.setEviter(dto.getEviter());
        db.setPrixKilo(dto.getPrixKilo());
        db.setPrixUnite(dto.getPrixUnite());
        db.setUtilisateur(UtilisateurMapper.toDb(dto.getUtilisateur()));
        db.setIngredient(IngredientMapper.toDb(dto.getIngredient()));
        db.setSaison(dto.getSaison()
                .stream()
                .map(String::valueOf)
                .collect(Collectors.joining(",")));
        return db;
    }

    // Convert a List of IngredientUtilisateurDb to a List of IngredientUtilisateurDTO
    public static List<IngredientUtilisateurDTO> toDtoList(List<IngredientUtilisateurDb> dbs, RemplacementRepository remplacementRepository) {
        if (dbs == null || dbs.isEmpty()) {
            return new ArrayList<>();
        }

        return dbs.stream().map(IngredientUtilisateurMapper::toDto).collect(Collectors.toList());
    }

    // Convert a List of IngredientUtilisateurDTO to a List of IngredientUtilisateurDb
    public static List<IngredientUtilisateurDb> toEntityList(List<IngredientUtilisateurDTO> dtos) {
        if (dtos == null || dtos.isEmpty()) {
            return new ArrayList<>();
        }

        return dtos.stream().map(IngredientUtilisateurMapper::toEntity).collect(Collectors.toList());
    }
}
