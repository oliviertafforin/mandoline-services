package fr.oliweb.mandoline.repository;

import fr.oliweb.mandoline.model.ImageDb;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.UUID;

@Repository
public interface ImageRepository extends JpaRepository<ImageDb, UUID> {
}
