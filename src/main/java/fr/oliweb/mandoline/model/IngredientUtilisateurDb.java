package fr.oliweb.mandoline.model;

import jakarta.persistence.*;

@Entity
@Table(name = "ingredient_utilisateur")
public class IngredientUtilisateurDb {
    @EmbeddedId
    private IngredientUtilisateurPk pk;

    @ManyToOne
    @MapsId("utilisateurId")
    @JoinColumn(name = "utilisateur")
    private UtilisateurDb utilisateur;

    @ManyToOne
    @MapsId("ingredientId")
    @JoinColumn(name = "ingredient")
    private IngredientDb ingredient;

    @Column(name = "prix_kilo")
    private int prixKilo;

    @Column(name = "prix_unitaire")
    private int prixUnite;

    @Column(name = "eviter")
    private Boolean eviter;

    public IngredientUtilisateurPk getPk() {
        return pk;
    }

    public void setPk(IngredientUtilisateurPk pk) {
        this.pk = pk;
    }

    public UtilisateurDb getUtilisateur() {
        return utilisateur;
    }

    public void setUtilisateur(UtilisateurDb utilisateur) {
        this.utilisateur = utilisateur;
    }

    public IngredientDb getIngredient() {
        return ingredient;
    }

    public void setIngredient(IngredientDb ingredient) {
        this.ingredient = ingredient;
    }

    public int getPrixKilo() {
        return prixKilo;
    }

    public void setPrixKilo(int prixKilo) {
        this.prixKilo = prixKilo;
    }

    public int getPrixUnite() {
        return prixUnite;
    }

    public void setPrixUnite(int prixUnite) {
        this.prixUnite = prixUnite;
    }

    public Boolean getEviter() {
        return eviter;
    }

    public void setEviter(Boolean eviter) {
        this.eviter = eviter;
    }
}
