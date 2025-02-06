package fr.oliweb.mandoline.model;

import jakarta.persistence.Column;
import jakarta.persistence.Embeddable;

import java.io.Serializable;
import java.util.Objects;
import java.util.UUID;

@Embeddable
public class IngredientUtilisateurPk implements Serializable {

    @Column(name = "utilisateur")
    private UUID utilisateurId;

    @Column(name = "ingredient")
    private UUID ingredientId;

    public IngredientUtilisateurPk(UUID ingredientId, UUID utilisateurId) {
        this.utilisateurId = utilisateurId;
        this.ingredientId = ingredientId;
    }

    public IngredientUtilisateurPk() {

    }

    public UUID getUtilisateurId() {
        return utilisateurId;
    }

    public void setUtilisateurId(UUID utilisateurId) {
        this.utilisateurId = utilisateurId;
    }

    public UUID getIngredientId() {
        return ingredientId;
    }

    public void setIngredientId(UUID ingredientId) {
        this.ingredientId = ingredientId;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        IngredientUtilisateurPk that = (IngredientUtilisateurPk) o;
        return utilisateurId.equals(that.utilisateurId) && ingredientId.equals(that.ingredientId);
    }

    @Override
    public int hashCode() {
        return Objects.hash(utilisateurId, ingredientId);
    }
}
