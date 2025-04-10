package fr.oliweb.mandoline.repository;

import fr.oliweb.mandoline.model.RecetteLikeeDb;
import fr.oliweb.mandoline.model.RecetteLikeePk;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.UUID;

@Repository
public interface RecetteLikeeRepository extends JpaRepository<RecetteLikeeDb, RecetteLikeePk> {
    List<RecetteLikeeDb> findByUtilisateurId(UUID utilisateur);
}
