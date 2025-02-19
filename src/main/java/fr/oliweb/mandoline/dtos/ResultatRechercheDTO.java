package fr.oliweb.mandoline.dtos;

import java.util.UUID;

public class ResultatRechercheDTO {
    private UUID id;
    private String nom;
    private String type;
    private String description;
    private ImageDTO image;

    public ResultatRechercheDTO(UUID id,  String nom, String type, String description, ImageDTO image) {
        this.id = id;
        this.nom = nom;
        this.type = type;
        this.description = description;
        this.image = image;
    }

    public UUID getId() {
        return id;
    }

    public void setId(UUID id) {
        this.id = id;
    }

    public String getNom() {
        return nom;
    }

    public void setNom(String nom) {
        this.nom = nom;
    }

    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public ImageDTO getImage() {
        return image;
    }

    public void setImage(ImageDTO image) {
        this.image = image;
    }
}
