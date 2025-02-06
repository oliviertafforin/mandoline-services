package fr.oliweb.mandoline.service;

import fr.oliweb.mandoline.dtos.IngredientUtilisateurDTO;
import fr.oliweb.mandoline.mappers.IngredientUtilisateurMapper;
import fr.oliweb.mandoline.model.IngredientUtilisateurPk;
import fr.oliweb.mandoline.repository.IngredientUtilisateurRepository;
import org.springframework.stereotype.Service;

import java.util.Optional;
import java.util.UUID;

@Service
public class IngredientUtilisateurService {

    private final IngredientUtilisateurRepository repository;

    public IngredientUtilisateurService(IngredientUtilisateurRepository repository) {
        this.repository = repository;
    }

    public Optional<IngredientUtilisateurDTO> getIngredientUtilisateurParId(UUID id, UUID user) {
        return repository.findById(new IngredientUtilisateurPk(id, user)).map(IngredientUtilisateurMapper::toDto);
    }
}


