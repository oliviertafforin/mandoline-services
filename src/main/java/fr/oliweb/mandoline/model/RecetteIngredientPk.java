package fr.oliweb.mandoline.model;

import jakarta.persistence.Column;
import jakarta.persistence.Embeddable;

import java.io.Serializable;
import java.util.UUID;

@Embeddable
public class RecetteIngredientPk implements Serializable {
    
    @Column(name = "recette")
    private UUID recetteId;

    @Column(name = "ingredient")
    private UUID ingredientId;

    public RecetteIngredientPk(UUID recetteId, UUID ingredientId) {
        this.recetteId = recetteId;
        this.ingredientId = ingredientId;
    }

    public RecetteIngredientPk() {

    }

    public UUID getRecetteId() {
        return recetteId;
    }

    public void setRecetteId(UUID recetteId) {
        this.recetteId = recetteId;
    }

    public UUID getIngredientId() {
        return ingredientId;
    }

    public void setIngredientId(UUID ingredientId) {
        this.ingredientId = ingredientId;
    }
}
