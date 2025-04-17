package fr.oliweb.mandoline.service;

import fr.oliweb.mandoline.dtos.RecetteDTO;
import fr.oliweb.mandoline.exceptions.ExceptionMessages;
import fr.oliweb.mandoline.exceptions.RessourceIntrouvableException;
import fr.oliweb.mandoline.mappers.RecetteMapper;
import fr.oliweb.mandoline.model.RecetteDb;
import fr.oliweb.mandoline.repository.RecetteRepository;
import org.apache.logging.log4j.util.Strings;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.stereotype.Service;

import java.util.*;

import static fr.oliweb.mandoline.mappers.RecetteMapper.toDb;
import static fr.oliweb.mandoline.mappers.RecetteMapper.toDto;

@Service
public class RecetteService {

    private final RecetteRepository repository;

    public RecetteService(RecetteRepository repository) {
        this.repository = repository;
    }

    public List<RecetteDTO> getAllRecettes() {
        return repository.findAll().stream()
                .map(RecetteMapper::toDto)
                .toList();
    }

    public Page<RecetteDTO> getAllRecettes(Pageable pageable, String nom, List<String> criteres) {
        List<RecetteDb> result;
        if ((criteres == null || criteres.isEmpty()) && Strings.isBlank(nom)) {
            result = repository.findAll(pageable).stream().toList();
        } else {
            List<String> criteresSimples = criteres != null ? criteres.stream()
                    .filter(critere -> Set.of("Apéritif", "Boissons", "Bases", "Plats", "Entrées", "Desserts").contains(critere))
                    .toList() : new ArrayList<>();

            List<String> criteresAvances = criteres != null ? criteres.stream()
                    .filter(critere -> !criteresSimples.contains(critere))
                    .toList() : new ArrayList<>();

            Specification<RecetteDb> spec = RecetteSpecificationBuilder.build(criteresSimples);

            if (Strings.isNotBlank(nom)) {
                spec = spec.and((root, query, builder) -> builder.like(builder.lower(root.get("nom")), "%" + nom.toLowerCase() + "%"));
            }

            Page<RecetteDb> page = repository.findAll(spec, pageable);


            result = page.stream()
                    .filter(r -> FiltreAvanceFactory.getFiltres(criteresAvances).stream().allMatch(f -> f.match(r)))
                    .toList();
        }

        List<RecetteDTO> dtos = result.stream().map(RecetteMapper::toDto).toList();
        return new PageImpl<>(dtos, pageable, result.size());
    }


    public Optional<RecetteDTO> getRecetteParId(UUID id) {
        return repository.findById(id).map(RecetteMapper::toDto);
    }

    public RecetteDTO creerRecette(RecetteDTO recetteDTO) {
        RecetteDb recette = RecetteMapper.toDb(recetteDTO);
        RecetteDb savedRecette = repository.save(recette);
        return toDto(savedRecette);
    }

    public RecetteDTO majRecette(UUID id, RecetteDTO recetteDTO) {
        return repository.findById(id).map(recette -> {
            RecetteDb recetteMaj = toDb(recetteDTO);
            return toDto(repository.save(recetteMaj));
        }).orElseThrow(() -> new RessourceIntrouvableException(ExceptionMessages.RECETTE_INTROUVABLE + ", id : "+id));
    }

    public void supprimerRecette(UUID id) {
        if (!repository.existsById(id)) {
            throw new RessourceIntrouvableException(ExceptionMessages.RECETTE_INTROUVABLE + ", id : "+id);
        }
        repository.deleteById(id);
    }
}


