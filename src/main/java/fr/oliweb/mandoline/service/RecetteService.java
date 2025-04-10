package fr.oliweb.mandoline.service;

import fr.oliweb.mandoline.dtos.RecetteDTO;
import fr.oliweb.mandoline.mappers.RecetteMapper;
import fr.oliweb.mandoline.model.RecetteDb;
import fr.oliweb.mandoline.repository.RecetteRepository;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;
import java.util.UUID;

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

    public Page<RecetteDTO> getAllRecettes(Pageable pageable) {
        Page<RecetteDb> result = repository.findAll(pageable);
        PageImpl<RecetteDTO> recetteDTOS = new PageImpl<>(result
                .stream()
                .map(RecetteMapper::toDto)
                .toList(), pageable, result.getTotalElements());
        return recetteDTOS;
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
        }).orElseThrow(() -> new RuntimeException("Recette introuvable"));
    }

    public void supprimerRecette(UUID id) {
        if (!repository.existsById(id)) {
            throw new RuntimeException("Recette introuvable");
        }
        repository.deleteById(id);
    }
}


