package fr.oliweb.mandoline.model;

import com.fasterxml.jackson.annotation.JsonProperty;
import jakarta.persistence.*;

import java.awt.*;
import java.util.Set;
import java.util.UUID;

@Entity
@Table(name = "ingredient")
public class IngredientDb {
    @Id
    @JsonProperty(value = "Id")
    @Column(name = "id")
    @GeneratedValue(strategy = GenerationType.UUID)
    private UUID id;

    @Column(name = "nom")
    private String nom;

    @OneToOne
    @JoinColumn(name = "image", referencedColumnName = "id")
    private ImageDb image;

    @OneToMany(mappedBy = "ingredient")
    private Set<RecetteIngredientDb> recetteIngredients;

    public void setId(UUID id) {
        this.id = id;
    }

    public UUID getId() {
        return id;
    }

    public String getNom() {
        return nom;
    }

    public void setNom(String nom) {
        this.nom = nom;
    }

    public ImageDb getImage() {
        return image;
    }

    public void setImage(ImageDb image) {
        this.image = image;
    }

    public Set<RecetteIngredientDb> getRecetteIngredients() {
        return recetteIngredients;
    }

    public void setRecetteIngredients(Set<RecetteIngredientDb> recetteIngredients) {
        this.recetteIngredients = recetteIngredients;
    }
}
