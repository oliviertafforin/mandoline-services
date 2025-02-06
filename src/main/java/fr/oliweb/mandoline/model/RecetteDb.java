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

    @Column(name = "instructions")
    private String instructions;

    @Column(name = "temperature")
    private int temperature;

    @Column(name = "tps_prepa")
    private int tpsPrepa;

    @OneToOne
    @JoinColumn(name = "id")
    private ImageDb image;

    @Column(name = "tps_cuisson")
    private int tpsCuisson;

    @ManyToOne
    @JoinColumn(name = "proprietaire")
    private UtilisateurDb proprietaire;

    @OneToMany(mappedBy = "recette", fetch = FetchType.EAGER)
    private Set<RecetteIngredientDb> recetteIngredients;

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

    public String getInstructions() {
        return instructions;
    }

    public void setInstructions(String instructions) {
        this.instructions = instructions;
    }

    public int getTemperature() {
        return temperature;
    }

    public void setTemperature(int temperature) {
        this.temperature = temperature;
    }

    public int getTpsPrepa() {
        return tpsPrepa;
    }

    public void setTpsPrepa(int tpsPrepa) {
        this.tpsPrepa = tpsPrepa;
    }

    public int getTpsCuisson() {
        return tpsCuisson;
    }

    public void setTpsCuisson(int tpsCuisson) {
        this.tpsCuisson = tpsCuisson;
    }

    public Set<RecetteIngredientDb> getRecetteIngredients() {
        return recetteIngredients;
    }

    public void setRecetteIngredients(Set<RecetteIngredientDb> recetteIngredients) {
        this.recetteIngredients = recetteIngredients;
    }

    public UtilisateurDb getProprietaire() {
        return proprietaire;
    }

    public void setProprietaire(UtilisateurDb proprietaire) {
        this.proprietaire = proprietaire;
    }
}
