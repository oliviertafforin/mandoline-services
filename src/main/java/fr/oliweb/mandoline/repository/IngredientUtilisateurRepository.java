package fr.oliweb.mandoline.repository;

import fr.oliweb.mandoline.model.IngredientUtilisateurDb;
import fr.oliweb.mandoline.model.IngredientUtilisateurPk;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface IngredientUtilisateurRepository extends JpaRepository<IngredientUtilisateurDb, IngredientUtilisateurPk> {
}
