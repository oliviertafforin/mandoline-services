package fr.oliweb.mandoline.dtos;


import io.swagger.v3.oas.annotations.media.Schema;

import java.time.Month;
import java.util.List;

public class IngredientUtilisateurDTO {

    private UtilisateurDTO utilisateur;

    private IngredientDTO ingredient;

    private int prixKilo;

    private int prixUnite;

    private Boolean eviter;

    @Schema(description = "Liste des ingrédients de remplacement possible")
    private List<IngredientDTO> remplacements;

    @Schema(description = "Mois où le produit est dit 'de saison'")
    private List<Integer> saison;

    public List<Integer> getSaison() {
        return saison;
    }

    public void setSaison(List<Integer> saison) {
        this.saison = saison;
    }


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

    public List<IngredientDTO> getRemplacements() {
        return remplacements;
    }

    public void setRemplacements(List<IngredientDTO> remplacements) {
        this.remplacements = remplacements;
    }
}
