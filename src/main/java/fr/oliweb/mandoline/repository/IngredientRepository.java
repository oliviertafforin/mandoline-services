package fr.oliweb.mandoline.repository;

import fr.oliweb.mandoline.model.IngredientDb;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.UUID;

@Repository
public interface IngredientRepository extends JpaRepository<IngredientDb, UUID> {
}
