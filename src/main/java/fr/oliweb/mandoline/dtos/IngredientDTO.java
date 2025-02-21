package fr.oliweb.mandoline.dtos;

import io.swagger.v3.oas.annotations.media.Schema;

import java.util.List;
import java.util.UUID;

@Schema(description = "Data Transfer Object représentant un ingrédient")
public class IngredientDTO {

    @Schema(description = "Identifiant unique d'un ingrédient", example = "f7a0a0df-5337-4908-bcef-42a069a9ba6d")
    private UUID id;

    @Schema(description = "Nom de l'ingrédient", example = "Fraise")
    private String nom;

    @Schema(description = "Image d'illustration")
    private ImageDTO image;

    @Schema(description = "Recettes")
    private List<RecetteIngredientDTO> recetteIngredients;

    public IngredientDTO(UUID id) {
        this.id = id;
    }

    public IngredientDTO() {
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

    public ImageDTO getImage() {
        return image;
    }

    public void setImage(ImageDTO image) {
        this.image = image;
    }

    public List<RecetteIngredientDTO> getRecetteIngredients() {
        return recetteIngredients;
    }

    public void setRecetteIngredients(List<RecetteIngredientDTO> recetteIngredients) {
        this.recetteIngredients = recetteIngredients;
    }
}
