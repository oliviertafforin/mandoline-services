package fr.oliweb.mandoline.dtos;


public class RecetteLikeeDTO {

    private UtilisateurDTO utilisateur;

    private RecetteDTO recette;

    public UtilisateurDTO getUtilisateur() {
        return utilisateur;
    }

    public void setUtilisateur(UtilisateurDTO utilisateur) {
        this.utilisateur = utilisateur;
    }

    public RecetteDTO getRecette() {
        return recette;
    }

    public void setRecette(RecetteDTO recette) {
        this.recette = recette;
    }
}
