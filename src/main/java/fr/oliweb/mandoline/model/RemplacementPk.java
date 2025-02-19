package fr.oliweb.mandoline.model;

import jakarta.persistence.Column;
import jakarta.persistence.Embeddable;

import java.io.Serializable;
import java.util.Objects;
import java.util.UUID;

@Embeddable
public class RemplacementPk implements Serializable {

    @Column(name = "utilisateur")
    private UUID utilisateurId;

    @Column(name = "ingredient")
    private UUID ingredientId;

    @Column(name = "remplacement")
    private UUID remplacementId;

    public RemplacementPk(UUID ingredientId, UUID utilisateurId, UUID remplacementId) {
        this.utilisateurId = utilisateurId;
        this.ingredientId = ingredientId;
        this.remplacementId = remplacementId;
    }

    public RemplacementPk() {

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

    public UUID getRemplacementId() {
        return remplacementId;
    }

    public void setRemplacementId(UUID remplacementId) {
        this.remplacementId = remplacementId;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        RemplacementPk that = (RemplacementPk) o;
        return utilisateurId.equals(that.utilisateurId) && ingredientId.equals(that.ingredientId) && remplacementId.equals(that.remplacementId);
    }

    @Override
    public int hashCode() {
        return Objects.hash(utilisateurId, ingredientId, remplacementId);
    }
}
