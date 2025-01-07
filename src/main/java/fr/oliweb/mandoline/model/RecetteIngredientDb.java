package fr.oliweb.mandoline.model;

import jakarta.persistence.*;

@Entity
@Table(name = "recette_ingredient")
public class RecetteIngredientDb {
    @EmbeddedId
    private RecetteIngredientPk pk;

    @ManyToOne
    @MapsId("recetteId")
    @JoinColumn(name = "recette")
    private RecetteDb recette;

    @ManyToOne
    @MapsId("ingredientId")
    @JoinColumn(name = "ingredient")
    private IngredientDb ingredient;

    private int quantite;

    private String unite;

    private Boolean optionnel;

    public RecetteIngredientPk getPk() {
        return pk;
    }

    public void setPk(RecetteIngredientPk pk) {
        this.pk = pk;
    }

    public RecetteDb getRecette() {
        return recette;
    }

    public void setRecette(RecetteDb recette) {
        this.recette = recette;
    }

    public IngredientDb getIngredient() {
        return ingredient;
    }

    public void setIngredient(IngredientDb ingredient) {
        this.ingredient = ingredient;
    }

    public int getQuantite() {
        return quantite;
    }

    public void setQuantite(int quantite) {
        this.quantite = quantite;
    }

    public String getUnite() {
        return unite;
    }

    public void setUnite(String unite) {
        this.unite = unite;
    }

    public Boolean getOptionnel() {
        return optionnel;
    }

    public void setOptionnel(Boolean optionnel) {
        this.optionnel = optionnel;
    }
}
