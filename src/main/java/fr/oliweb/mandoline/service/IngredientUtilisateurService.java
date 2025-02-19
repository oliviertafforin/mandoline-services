package fr.oliweb.mandoline.service;

import fr.oliweb.mandoline.dtos.IngredientUtilisateurDTO;
import fr.oliweb.mandoline.mappers.IngredientMapper;
import fr.oliweb.mandoline.mappers.IngredientUtilisateurMapper;
import fr.oliweb.mandoline.model.IngredientUtilisateurPk;
import fr.oliweb.mandoline.model.RemplacementDb;
import fr.oliweb.mandoline.repository.IngredientUtilisateurRepository;
import fr.oliweb.mandoline.repository.RemplacementRepository;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;
import java.util.UUID;
import java.util.stream.Collectors;

@Service
public class IngredientUtilisateurService {

    private final IngredientUtilisateurRepository repository;
    private final RemplacementRepository remplacementRepository;

    public IngredientUtilisateurService(IngredientUtilisateurRepository repository, RemplacementRepository remplacementRepository) {
        this.repository = repository;
        this.remplacementRepository = remplacementRepository;
    }

    public Optional<IngredientUtilisateurDTO> getIngredientUtilisateurParId(UUID id, UUID user) {
        List<RemplacementDb> remplacementDbList = remplacementRepository.findByIngredientIdAndUtilisateurId(id, user);

        Optional<IngredientUtilisateurDTO> ingredientUtilisateurDTO = repository.findById(new IngredientUtilisateurPk(id, user)).map(IngredientUtilisateurMapper::toDto);
        ingredientUtilisateurDTO.ifPresent(iu -> iu.setRemplacements(remplacementDbList.stream()
                .map(r -> IngredientMapper.toDto(r.getRemplacement()))
                .collect(Collectors.toList())));
        return ingredientUtilisateurDTO;
    }
}


