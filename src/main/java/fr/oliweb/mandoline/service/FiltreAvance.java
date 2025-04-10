package fr.oliweb.mandoline.service;

import fr.oliweb.mandoline.model.RecetteDb;

@FunctionalInterface
public interface FiltreAvance {
    boolean match(RecetteDb recette);
}
