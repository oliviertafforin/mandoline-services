package fr.oliweb.mandoline.mappers;

import fr.oliweb.mandoline.dtos.UtilisateurDTO;
import fr.oliweb.mandoline.model.UtilisateurDb;

public class UtilisateurMapper {
    // Convert Utilisateur entity to UtilisateurDTO
    public static UtilisateurDTO toDto(UtilisateurDb db) {
        if (db == null) {
            return null;
        }

        UtilisateurDTO dto = new UtilisateurDTO();
        dto.setId(db.getId());
        dto.setPseudo(db.getPseudo());

        return dto;
    }

    // Convert UtilisateurDTO to Utilisateur entity
    public static UtilisateurDb toDb(UtilisateurDTO dto) {
        if (dto == null) {
            return null;
        }

        UtilisateurDb utilisateur = new UtilisateurDb();
        utilisateur.setId(dto.getId());

        utilisateur.setPseudo(dto.getPseudo());
        return utilisateur;
    }
}
