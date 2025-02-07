package fr.oliweb.mandoline.mappers;

import fr.oliweb.mandoline.dtos.RoleDTO;
import fr.oliweb.mandoline.model.RoleDb;

public class RoleMapper {
    // Convert Role entity to RoleDTO
    public static RoleDTO toDto(RoleDb db) {
        if (db == null) {
            return null;
        }

        RoleDTO dto = new RoleDTO();
        dto.setLibelle(db.getLibelle());

        return dto;
    }

    // Convert RoleDTO to Role entity
    public static RoleDb toDb(RoleDTO dto) {
        if (dto == null) {
            return null;
        }

        RoleDb role = new RoleDb();
        role.setLibelle(dto.getLibelle());

        return role;
    }
}
