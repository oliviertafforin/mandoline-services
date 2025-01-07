package fr.oliweb.mandoline.mappers;

import fr.oliweb.mandoline.dtos.ImageDTO;
import fr.oliweb.mandoline.model.ImageDb;

public class ImageMapper {
    // Convert Image entity to ImageDTO
    public static ImageDTO toDto(ImageDb db) {
        if (db == null) {
            return null;
        }

        ImageDTO dto = new ImageDTO();
        dto.setId(db.getId());
        dto.setLibelle(db.getLibelle());
        dto.setUrl(db.getUrl());

        return dto;
    }

    // Convert ImageDTO to Image entity
    public static ImageDb toDb(ImageDTO dto) {
        if (dto == null) {
            return null;
        }

        ImageDb image = new ImageDb();
        image.setId(dto.getId());
        image.setLibelle(dto.getLibelle());
        image.setUrl(dto.getUrl());

        return image;
    }
}
