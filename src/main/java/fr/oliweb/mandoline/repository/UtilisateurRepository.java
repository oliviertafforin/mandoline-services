package fr.oliweb.mandoline.repository;

import fr.oliweb.mandoline.model.UtilisateurDb;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;
import java.util.UUID;

@Repository
public interface UtilisateurRepository extends JpaRepository<UtilisateurDb, UUID> {
    boolean existsByPseudo(String pseudo);
    Optional<UtilisateurDb> findByPseudo(String pseudo);
}
