package fr.oliweb.mandoline.repository;

import fr.oliweb.mandoline.model.RecetteDb;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.UUID;

@Repository
public interface RecetteRepository extends JpaRepository<RecetteDb, UUID>, JpaSpecificationExecutor<RecetteDb> {
    List<RecetteDb> findByNomContainingIgnoreCase(String query);

    Page<RecetteDb> findByNomContainingIgnoreCase(String nom, Pageable pageable);
}
