package fr.oliweb.mandoline.service;

import fr.oliweb.mandoline.dtos.RecetteDTO;
import fr.oliweb.mandoline.mappers.RecetteMapper;
import fr.oliweb.mandoline.model.RecetteDb;
import fr.oliweb.mandoline.repository.ImageRepository;
import fr.oliweb.mandoline.repository.RecetteRepository;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;
import java.util.UUID;

import static fr.oliweb.mandoline.mappers.RecetteMapper.toDb;
import static fr.oliweb.mandoline.mappers.RecetteMapper.toDto;

@Service
public class RecetteService {

    private final RecetteRepository repository;
    private final ImageRepository imageRepository;

    public RecetteService(RecetteRepository repository,
                          ImageRepository imageRepository) {
        this.repository = repository;
        this.imageRepository = imageRepository;
    }

    public List<RecetteDTO> getAllRecettes() {
        return repository.findAll().stream()
                .map(r -> toDto(r, imageRepository))
                .toList();
    }


    public Optional<RecetteDTO> getRecetteParId(UUID id) {
        return repository.findById(id).map(r -> toDto(r, imageRepository));
    }

    public RecetteDTO creerRecette(RecetteDTO recetteDTO) {
        RecetteDb recette = RecetteMapper.toDb(recetteDTO);
        RecetteDb savedRecette = repository.save(recette);
        return toDto(savedRecette, imageRepository);
    }

    public RecetteDTO majRecette(UUID id, RecetteDTO recetteDTO) {
        return repository.findById(id).map(recette -> {
            RecetteDb recetteMaj = toDb(recetteDTO);
            recetteMaj.setId(recette.getId());
            return toDto(repository.save(recette), imageRepository);
        }).orElseThrow(() -> new RuntimeException("Recette introuvable"));
    }

    public void supprimerRecette(UUID id) {
        if (!repository.existsById(id)) {
            throw new RuntimeException("Recette introuvable");
        }
        repository.deleteById(id);
    }
}


