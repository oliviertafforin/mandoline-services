package fr.oliweb.mandoline.repository;

import fr.oliweb.mandoline.model.RecetteDb;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.UUID;

@Repository
public interface RecetteRepository extends JpaRepository<RecetteDb, UUID> {
}
