package fr.oliweb.mandoline.model;

import jakarta.persistence.*;

@Entity
@Table(name = "ingredient_utilisateur_remplacement")
public class RemplacementDb {

    @EmbeddedId
    private RemplacementPk pk;

    @ManyToOne
    @MapsId("utilisateurId")
    @JoinColumn(name = "utilisateur")
    private UtilisateurDb utilisateur;

    @ManyToOne
    @MapsId("ingredientId")
    @JoinColumn(name = "ingredient")
    private IngredientDb ingredient;

    @ManyToOne
    @MapsId("remplacementId")
    @JoinColumn(name = "remplacement")
    private IngredientDb remplacement;

    @Column(name = "note")
    private String note;


    public RemplacementPk getPk() {
        return pk;
    }

    public void setPk(RemplacementPk pk) {
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

    public IngredientDb getRemplacement() {
        return remplacement;
    }

    public void setRemplacement(IngredientDb remplacement) {
        this.remplacement = remplacement;
    }

    public String getNote() {
        return note;
    }

    public void setNote(String note) {
        this.note = note;
    }
}
