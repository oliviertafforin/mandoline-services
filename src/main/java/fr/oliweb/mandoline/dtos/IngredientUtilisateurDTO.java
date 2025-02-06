package fr.oliweb.mandoline.dtos;


public class IngredientUtilisateurDTO {

    private UtilisateurDTO utilisateur;

    private IngredientDTO ingredient;

    private int prixKilo;

    private int prixUnite;

    private Boolean eviter;


    public IngredientDTO getIngredient() {
        return ingredient;
    }

    public void setIngredient(IngredientDTO ingredient) {
        this.ingredient = ingredient;
    }

    public UtilisateurDTO getUtilisateur() {
        return utilisateur;
    }

    public void setUtilisateur(UtilisateurDTO utilisateur) {
        this.utilisateur = utilisateur;
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
