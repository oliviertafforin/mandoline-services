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
    @Schema(description = "Texte d'intro de la recette", example = "lorem ipsum dolor sit amet")
    private String introduction;
    @Schema(description = "Température du plat (chaude : 1, froide : 2, les deux : 0)", example = "2")
    private Integer temperature;
    @Schema(description = "Nombre de personnes pour laquelle la recette est prévue", example = "2")
    private Integer nbPersonnes;
    @Schema(description = "Temps de préparation de la recette", example = "20")
    private Integer tpsPrepa;
    @Schema(description = "Temps de cuisson de la recette", example = "45")
    private Integer tpsCuisson;
    @Schema(description = "Utilisateur propriétaire de la recette")
    private UtilisateurDTO proprietaire;
    @Schema(description = "Image de couverture de la recette")
    private ImageDTO image;
    @Schema(description = "Tableau JSON détaillant chaque étape de la préparation")
    private String etapes;
    @Schema(description = "Catégorie de la recette", examples = {  "Apéritif","Boissons","Bases","Plats","Entrées","Desserts"})
    private String categorie;

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

    public String getCategorie() {
        return categorie;
    }

    public void setCategorie(String categorie) {
        this.categorie = categorie;
    }

    public String getNom() {
        return nom;
    }

    public void setNom(String nom) {
        this.nom = nom;
    }

    public String getIntroduction() {
        return introduction;
    }

    public void setIntroduction(String introduction) {
        this.introduction = introduction;
    }

    public String getEtapes() {
        return etapes;
    }

    public void setEtapes(String etapes) {
        this.etapes = etapes;
    }

    public Integer getNbPersonnes() {
        return nbPersonnes;
    }

    public void setNbPersonnes(Integer nbPersonnes) {
        this.nbPersonnes = nbPersonnes;
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
