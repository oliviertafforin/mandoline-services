package fr.oliweb.mandoline.mappers;

import fr.oliweb.mandoline.dtos.RecetteLikeeDTO;
import fr.oliweb.mandoline.model.RecetteLikeeDb;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

public class RecetteLikeeMapper {

    // Convert RecetteLikeeDb to RecetteLikeeDTO
    public static RecetteLikeeDTO toDto(RecetteLikeeDb db) {
        if (db == null) {
            return null;
        }

        RecetteLikeeDTO dto = new RecetteLikeeDTO();
        dto.setRecette(RecetteMapper.toDto(db.getRecette()));
        dto.setUtilisateur(UtilisateurMapper.toDto(db.getUtilisateur()));
        return dto;
    }

    // Convert RecetteLikeeDTO to RecetteLikee
    public static RecetteLikeeDb toEntity(RecetteLikeeDTO dto) {
        if (dto == null) {
            return null;
        }

        RecetteLikeeDb entity = new RecetteLikeeDb();
        entity.setRecette(RecetteMapper.toDb(dto.getRecette())); // Map Recette
        entity.setUtilisateur(UtilisateurMapper.toDb(dto.getUtilisateur()));
        return entity;
    }

    // Convert a List of RecetteLikee to a List of RecetteLikeeDTO
    public static List<RecetteLikeeDTO> toDtoList(List<RecetteLikeeDb> dbs) {
        if (dbs == null || dbs.isEmpty()) {
            return new ArrayList<>();
        }

        return dbs.stream().map(RecetteLikeeMapper::toDto).collect(Collectors.toList());
    }

    // Convert a List of RecetteLikeeDTO to a List of RecetteLikee
    public static List<RecetteLikeeDb> toEntityList(List<RecetteLikeeDTO> dtos) {
        if (dtos == null || dtos.isEmpty()) {
            return new ArrayList<>();
        }

        return dtos.stream().map(RecetteLikeeMapper::toEntity).collect(Collectors.toList());
    }
}
