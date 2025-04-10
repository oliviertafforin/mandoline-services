package fr.oliweb.mandoline.model;

import jakarta.persistence.Column;
import jakarta.persistence.Embeddable;

import java.io.Serializable;
import java.util.Objects;
import java.util.UUID;

@Embeddable
public class RecetteLikeePk implements Serializable {

    @Column(name = "utilisateur")
    private UUID utilisateurId;

    @Column(name = "recette")
    private UUID recetteId;

    public RecetteLikeePk(UUID utilisateurId, UUID recetteId) {
        this.utilisateurId = utilisateurId;
        this.recetteId = recetteId;
    }

    public RecetteLikeePk() {

    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        RecetteLikeePk that = (RecetteLikeePk) o;
        return utilisateurId.equals(that.utilisateurId) && recetteId.equals(that.recetteId);
    }

    @Override
    public int hashCode() {
        return Objects.hash(utilisateurId, recetteId);
    }

    public UUID getUtilisateurId() {
        return utilisateurId;
    }

    public void setUtilisateurId(UUID utilisateurId) {
        this.utilisateurId = utilisateurId;
    }

    public UUID getRecetteId() {
        return recetteId;
    }

    public void setRecetteId(UUID recetteId) {
        this.recetteId = recetteId;
    }
}
