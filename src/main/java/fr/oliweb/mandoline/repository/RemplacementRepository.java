package fr.oliweb.mandoline.repository;

import fr.oliweb.mandoline.model.IngredientDb;
import fr.oliweb.mandoline.model.RemplacementDb;
import fr.oliweb.mandoline.model.RemplacementPk;
import fr.oliweb.mandoline.model.UtilisateurDb;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.UUID;

@Repository
public interface RemplacementRepository extends JpaRepository<RemplacementDb, RemplacementPk> {
    List<RemplacementDb> findByIngredientAndUtilisateur(IngredientDb ingredient, UtilisateurDb utilisateur);
    List<RemplacementDb> findByIngredientIdAndUtilisateurId(UUID ingredient, UUID utilisateur);
}
