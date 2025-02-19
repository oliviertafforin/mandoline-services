package fr.oliweb.mandoline.mappers;

import fr.oliweb.mandoline.dtos.UtilisateurDTO;
import fr.oliweb.mandoline.model.UtilisateurDb;

import java.util.List;
import java.util.stream.Collectors;

public class UtilisateurMapper {
    // Convert Utilisateur entity to UtilisateurDTO
    public static UtilisateurDTO toDto(UtilisateurDb db) {
        if (db == null) {
            return null;
        }

        UtilisateurDTO dto = new UtilisateurDTO();
        dto.setId(db.getId());
        dto.setPseudo(db.getPseudo());
        dto.setAvatar(ImageMapper.toDto(db.getAvatar()));
        dto.setMdp(db.getMdp());
        dto.setRole(RoleMapper.toDto(db.getRole()));
        return dto;
    }

    // Convert UtilisateurDTO to Utilisateur entity
    public static UtilisateurDb toDb(UtilisateurDTO dto) {
        if (dto == null) {
            return null;
        }

        UtilisateurDb utilisateur = new UtilisateurDb();
        utilisateur.setId(dto.getId());
        utilisateur.setMdp(dto.getMdp());
        utilisateur.setAvatar(ImageMapper.toDb(dto.getAvatar()));
        utilisateur.setRole(RoleMapper.toDb(dto.getRole()));
        utilisateur.setPseudo(dto.getPseudo());
        return utilisateur;
    }

    // Convert a list of UtilisateurDb entities to a list of UtilisateurDtos
    public static List<UtilisateurDTO> toDtoList(List<UtilisateurDb> dbs) {
        if (dbs == null || dbs.isEmpty()) {
            return null;
        }
        return dbs.stream().map(UtilisateurMapper::toDto).collect(Collectors.toList());
    }
}