package fr.oliweb.mandoline.repository;

import fr.oliweb.mandoline.model.RoleDb;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface RoleRepository extends JpaRepository<RoleDb, String> {
}
