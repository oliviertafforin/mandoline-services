package fr.oliweb.mandoline.dto;

import io.swagger.v3.oas.annotations.media.Schema;

import java.util.UUID;

@Schema(description = "Data Transfer Object représentant une recette")
public class RecetteDTO {

    @Schema(description = "Identifiant unique d'une recette", example = "f7a0a0df-5337-4908-bcef-42a069a9ba6d")
    private UUID id;

    @Schema(description = "Nom de la recette", example = "Cake à la fraise")
    private String nom;
    @Schema(description = "Instructions de préparation de la recette", example = "lorem ipsum dolor sit amet")
    private String instructions;
    @Schema(description = "Température du plat (chaude : 1, froide : 2, les deux : 0)", example = "2")
    private int temperature;
    @Schema(description = "Temps de préparation de la recette", example = "20")
    private int tpsPrepa;
    @Schema(description = "Temps de cuisson de la recette", example = "45")
    private int tpsCuisson;

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
}
