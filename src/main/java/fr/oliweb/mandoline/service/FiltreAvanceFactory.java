package fr.oliweb.mandoline.service;

import java.util.List;
import java.util.Objects;
import java.util.Optional;

public class FiltreAvanceFactory {

    public static List<FiltreAvance> getFiltres(List<String> criteres) {
        return criteres.stream()
                .map(FiltreAvanceFactory::getFiltre)
                .filter(Objects::nonNull)
                .toList();
    }

    private static FiltreAvance getFiltre(String critere) {
        return switch (critere) {
            case "Rapide" -> recette -> {
                int total = Optional.ofNullable(recette.getTpsPrepa()).orElse(0)
                        + Optional.ofNullable(recette.getTpsCuisson()).orElse(0);
                return total <= 30;
            };
            case "Pas cher" -> recette -> recette.getRecetteIngredients().stream()
                    .mapToDouble(ing -> Optional.ofNullable(ing.getIngredient())
                            .map(i -> 0.0)//TODO calculer prix ingredient
                            .orElse(0.0))
                    .sum() < 10.0;
            default -> null;
        };
    }
}

