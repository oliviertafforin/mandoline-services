package fr.oliweb.mandoline.exceptions;

public enum ExceptionMessages {
    ERREUR_INTERNE("Erreur interne"),

    RESSOURCE_INTROUVABLE("Ressource introuvable"),
    RECETTE_INTROUVABLE("Recette introuvable"),
    IMAGE_INTROUVABLE("Image introuvable"),
    INGREDIENT_INTROUVABLE("Ingrédient introuvable"),
    UTILISATEUR_INTROUVABLE("Utilisateur introuvable"),

    VIOLATION_CONTRAINTE("Violation de contrainte"),
    METHODE_NON_AUTORISEE("Méthode non autorisée"),
    REQUETE_INVALIDE("Requête invalide"),
    INGREDIENT_UTILISATEUR_INTROUVABLE("Ingrédient/utilisateur introuvable"),
    VALIDATION_ECHOUEE("Validation échouée"), PARAMETRE_MANQUANT("Paramètre manquant");

    private final String message;

    ExceptionMessages(String message) {
        this.message = message;
    }

    @Override
    public String toString() {
        return message;
    }
}
