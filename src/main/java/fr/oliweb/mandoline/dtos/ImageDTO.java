package fr.oliweb.mandoline.dtos;

import io.swagger.v3.oas.annotations.media.Schema;

import java.util.UUID;

@Schema(description = "Data Transfer Object représentant une image")
public class ImageDTO {

    @Schema(description = "Identifiant unique d'une image", example = "f7a0a0df-5337-4908-bcef-42a069a9ba6d")
    private UUID id;

    @Schema(description = "Libellé de l'image", example = "Cake à la fraise")
    private String libelle;

    @Schema(description = "chemin d'accès de l'image", example = "avatars/123_456_789.png")
    private String path;

    public UUID getId() {
        return id;
    }

    public void setId(UUID id) {
        this.id = id;
    }

    public String getLibelle() {
        return libelle;
    }

    public void setLibelle(String libelle) {
        this.libelle = libelle;
    }

    public String getPath() {
        return path;
    }

    public void setPath(String path) {
        this.path = path;
    }
}
