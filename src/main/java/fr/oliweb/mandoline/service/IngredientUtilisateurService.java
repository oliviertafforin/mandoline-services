package fr.oliweb.mandoline.service;

import fr.oliweb.mandoline.dtos.IngredientUtilisateurDTO;
import fr.oliweb.mandoline.mappers.IngredientMapper;
import fr.oliweb.mandoline.mappers.IngredientUtilisateurMapper;
import fr.oliweb.mandoline.model.*;
import fr.oliweb.mandoline.repository.IngredientRepository;
import fr.oliweb.mandoline.repository.IngredientUtilisateurRepository;
import fr.oliweb.mandoline.repository.RemplacementRepository;
import fr.oliweb.mandoline.repository.UtilisateurRepository;
import jakarta.persistence.EntityNotFoundException;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Optional;
import java.util.UUID;
import java.util.stream.Collectors;

@Service
public class IngredientUtilisateurService {

    private final IngredientUtilisateurRepository repository;
    private final RemplacementRepository remplacementRepository;
    private final IngredientRepository ingredientRepository;
    private final UtilisateurRepository utilisateurRepository;

    public IngredientUtilisateurService(IngredientUtilisateurRepository repository, RemplacementRepository remplacementRepository, IngredientRepository ingredientRepository, UtilisateurRepository utilisateurRepository) {
        this.repository = repository;
        this.remplacementRepository = remplacementRepository;
        this.ingredientRepository = ingredientRepository;
        this.utilisateurRepository = utilisateurRepository;
    }

    public Optional<IngredientUtilisateurDTO> getIngredientUtilisateurParId(UUID id, UUID user) {
        List<RemplacementDb> remplacementDbList = remplacementRepository.findByIngredientIdAndUtilisateurId(id, user);

        Optional<IngredientUtilisateurDTO> ingredientUtilisateurDTO = repository.findById(new IngredientUtilisateurPk(id, user)).map(IngredientUtilisateurMapper::toDto);
        ingredientUtilisateurDTO.ifPresent(iu -> iu.setRemplacements(remplacementDbList.stream()
                .map(r -> IngredientMapper.toDto(r.getRemplacement()))
                .collect(Collectors.toList())));
        return ingredientUtilisateurDTO;
    }

    @Transactional
    public IngredientUtilisateurDTO creerIngredientUtilisateur(IngredientUtilisateurDTO dto) {
        // Valider les données entrantes si nécessaire
        validateDto(dto);

        IngredientUtilisateurDb entity = IngredientUtilisateurMapper.toEntity(dto);
        IngredientUtilisateurDb savedEntity = repository.save(entity);
        return IngredientUtilisateurMapper.toDto(savedEntity);
    }

    private void validateDto(IngredientUtilisateurDTO dto) {
        // Ajoutez ici les validations nécessaires
        if (dto.getPrixUnite() < 0 || dto.getPrixKilo() < 0) {
            throw new IllegalArgumentException("Les prix ne peuvent pas être négatifs.");
        }
        // Ajoutez d'autres validations selon vos besoins
    }

    @Transactional
    public IngredientUtilisateurDTO majOuCreerIngredientUtilisateur(UUID id, UUID userId, IngredientUtilisateurDTO dto) {
        //validation des données entrantes
        validationDto(dto);
        IngredientUtilisateurDb db = IngredientUtilisateurMapper.toEntity(dto);
        // Vérifier si l'entité existe déjà
        Optional<IngredientUtilisateurDb> existingOptional = repository.findById(new IngredientUtilisateurPk(id, userId));

        if (existingOptional.isPresent()) {
            // Mettre à jour l'entité existante
            IngredientUtilisateurDb existing = existingOptional.get();
            existing.setPrixUnite(db.getPrixUnite());
            existing.setPrixKilo(db.getPrixKilo());
            existing.setSaison(db.getSaison());
            existing.setEviter(db.getEviter());
            return IngredientUtilisateurMapper.toDto(repository.save(existing));
        } else {
            // Créer une nouvelle entité si les ids ingredient et utilisateur sont valides
            Optional<UtilisateurDb> utilisateur = utilisateurRepository.findById(userId);
            Optional<IngredientDb> ingredient = ingredientRepository.findById(id);
            if (utilisateur.isPresent() && ingredient.isPresent()) {
                db.setPk(new IngredientUtilisateurPk(id, userId));
                db.setUtilisateur(utilisateur.get());
                db.setIngredient(ingredient.get());
                return IngredientUtilisateurMapper.toDto(repository.save(db));
            } else {
                throw new EntityNotFoundException("Ingrédient/utilisateur introuvable");
            }
        }
    }

    private void validationDto(IngredientUtilisateurDTO dto) {
        if (dto.getPrixUnite() < 0 || dto.getPrixKilo() < 0) {
            throw new IllegalArgumentException("Les prix ne peuvent pas être négatifs.");
        }
    }
}


