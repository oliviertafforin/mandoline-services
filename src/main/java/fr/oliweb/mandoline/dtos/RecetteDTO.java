package fr.oliweb.mandoline.dtos;

import io.swagger.v3.oas.annotations.media.Schema;

import java.util.List;
import java.util.UUID;

@Schema(description = "Data Transfer Object représentant une recette")
public class RecetteDTO {

    @Schema(description = "Identifiant unique d'une recette", example = "f7a0a0df-5337-4908-bcef-42a069a9ba6d")
    private UUID id;

    @Schema(description = "Nom de la recette", example = "Cake à la fraise")
    private String nom;
    @Schema(description = "Instructions de préparation de la recette", example = "lorem ipsum dolor sit amet")
    private String instructions;
    @Schema(description = "Température du plat (chaude : 1, froide : 2, les deux : 0)", example = "2")
    private Integer temperature;
    @Schema(description = "Temps de préparation de la recette", example = "20")
    private Integer tpsPrepa;
    @Schema(description = "Temps de cuisson de la recette", example = "45")
    private Integer tpsCuisson;
    @Schema(description = "Utilisateur propriétaire de la recette")
    private UtilisateurDTO proprietaire;
    @Schema(description = "Image de couverture de la recette")
    private ImageDTO image;

    @Schema(description = "Ingredients")
    private List<RecetteIngredientDTO> recetteIngredients;

    public UtilisateurDTO getProprietaire() {
        return proprietaire;
    }

    public void setProprietaire(UtilisateurDTO proprietaire) {
        this.proprietaire = proprietaire;
    }

    public ImageDTO getImage() {
        return image;
    }

    public void setImage(ImageDTO image) {
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

    public String getInstructions() {
        return instructions;
    }

    public void setInstructions(String instructions) {
        this.instructions = instructions;
    }

    public Integer getTemperature() {
        return temperature;
    }

    public void setTemperature(Integer temperature) {
        this.temperature = temperature;
    }

    public List<RecetteIngredientDTO> getRecetteIngredients() {
        return recetteIngredients;
    }

    public void setRecetteIngredients(List<RecetteIngredientDTO> recetteIngredients) {
        this.recetteIngredients = recetteIngredients;
    }

    public Integer getTpsPrepa() {
        return tpsPrepa;
    }

    public void setTpsPrepa(Integer tpsPrepa) {
        this.tpsPrepa = tpsPrepa;
    }

    public Integer getTpsCuisson() {
        return tpsCuisson;
    }

    public void setTpsCuisson(Integer tpsCuisson) {
        this.tpsCuisson = tpsCuisson;
    }
}
