package fr.oliweb.mandoline.model;

import com.fasterxml.jackson.annotation.JsonProperty;
import jakarta.persistence.*;

import java.util.Set;
import java.util.UUID;

@Entity
@Table(name = "recette")
public class RecetteDb {
    @Id
    @JsonProperty(value = "Id")
    @Column(name = "id")
    @GeneratedValue(strategy = GenerationType.UUID)
    private UUID id;

    @Column(name = "nom")
    private String nom;

    @Column(name = "introduction")
    private String introduction;

    @Column(name = "temperature")
    private Integer temperature;

    @Column(name = "nb_personnes")
    private Integer nbPersonnes;

    @Column(name = "tps_prepa")
    private Integer tpsPrepa;

    @OneToOne(cascade = {CascadeType.MERGE})
    @JoinColumn(name = "image", referencedColumnName = "id")
    private ImageDb image;

    @Column(name = "tps_cuisson")
    private Integer tpsCuisson;

    @Column(name = "etapes")
    private String etapes;

    @Column(name = "categorie")
    private String categorie;

    @ManyToOne
    @JoinColumn(name = "proprietaire")
    private UtilisateurDb proprietaire;

    @OneToMany(mappedBy = "recette", fetch = FetchType.EAGER)
    private Set<RecetteIngredientDb> recetteIngredients;

    public String getCategorie() {
        return categorie;
    }

    public void setCategorie(String categorie) {
        this.categorie = categorie;
    }

    public String getIntroduction() {
        return introduction;
    }

    public void setIntroduction(String introduction) {
        this.introduction = introduction;
    }

    public Integer getNbPersonnes() {
        return nbPersonnes;
    }

    public void setNbPersonnes(Integer nbPersonnes) {
        this.nbPersonnes = nbPersonnes;
    }

    public ImageDb getImage() {
        return image;
    }

    public void setImage(ImageDb image) {
        this.image = image;
    }

    public UUID getId() {
        return id;
    }

    public void setId(UUID id) {
        this.id = id;
    }

    public String getNom() {
        return nom;
    }

    public void setNom(String nom) {
        this.nom = nom;
    }

    public Integer getTemperature() {
        return temperature;
    }

    public void setTemperature(Integer temperature) {
        this.temperature = temperature;
    }

    public Integer getTpsPrepa() {
        return tpsPrepa;
    }

    public void setTpsPrepa(Integer tpsPrepa) {
        this.tpsPrepa = tpsPrepa;
    }

    public Integer getTpsCuisson() {
        return tpsCuisson;
    }

    public void setTpsCuisson(Integer tpsCuisson) {
        this.tpsCuisson = tpsCuisson;
    }

    public Set<RecetteIngredientDb> getRecetteIngredients() {
        return recetteIngredients;
    }

    public void setRecetteIngredients(Set<RecetteIngredientDb> recetteIngredients) {
        this.recetteIngredients = recetteIngredients;
    }

    public String getEtapes() {
        return etapes;
    }

    public void setEtapes(String etapes) {
        this.etapes = etapes;
    }

    public UtilisateurDb getProprietaire() {
        return proprietaire;
    }

    public void setProprietaire(UtilisateurDb proprietaire) {
        this.proprietaire = proprietaire;
    }
}
