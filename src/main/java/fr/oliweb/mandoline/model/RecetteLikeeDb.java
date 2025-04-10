package fr.oliweb.mandoline.model;

import jakarta.persistence.*;

@Entity
@Table(name = "recette_likees")
public class RecetteLikeeDb {
    @EmbeddedId
    private RecetteLikeePk pk;

    @ManyToOne
    @MapsId("utilisateurId")
    @JoinColumn(name = "utilisateur")
    private UtilisateurDb utilisateur;

    @ManyToOne
    @MapsId("recetteId")
    @JoinColumn(name = "recette")
    private RecetteDb recette;


    public RecetteLikeePk getPk() {
        return pk;
    }

    public void setPk(RecetteLikeePk pk) {
        this.pk = pk;
    }

    public UtilisateurDb getUtilisateur() {
        return utilisateur;
    }

    public void setUtilisateur(UtilisateurDb utilisateur) {
        this.utilisateur = utilisateur;
    }

    public RecetteDb getRecette() {
        return recette;
    }

    public void setRecette(RecetteDb recette) {
        this.recette = recette;
    }
}
