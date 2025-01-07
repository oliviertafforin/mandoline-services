package fr.oliweb.mandoline.dtos;

import io.swagger.v3.oas.annotations.media.Schema;

import java.util.UUID;

@Schema(description = "Data Transfer Object représentant une image")
public class ImageDTO {

    @Schema(description = "Identifiant unique d'une image", example = "f7a0a0df-5337-4908-bcef-42a069a9ba6d")
    private UUID id;

    @Schema(description = "Libellé de l'image", example = "Cake à la fraise")
    private String libelle;

    @Schema(description = "URL de l'image", example = "https://liliebakery.fr/layer-cake-fraise/")
    private String url;

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

    public String getUrl() {
        return url;
    }

    public void setUrl(String url) {
        this.url = url;
    }
}
