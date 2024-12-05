package fr.oliweb.mandoline.service;

import fr.oliweb.mandoline.dto.RecetteDTO;
import fr.oliweb.mandoline.dto.UtilisateurDTO;
import fr.oliweb.mandoline.model.Recette;
import fr.oliweb.mandoline.repository.RecetteRepository;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;
import java.util.UUID;

@Service
public class RecetteService {

    private final RecetteRepository repository;

    public RecetteService(RecetteRepository repository) {
        this.repository = repository;
    }

    public List<RecetteDTO> getAllRecettes() {
        return repository.findAll().stream()
                .map(this::toDTO)
                .toList();
    }


    public Optional<RecetteDTO> getRecetteParId(UUID id) {
        return repository.findById(id).map(this::toDTO);
    }

    public RecetteDTO creerRecette(RecetteDTO recetteDTO) {
        Recette recette = toEntity(recetteDTO);
        Recette savedRecette = repository.save(recette);
        return toDTO(savedRecette);
    }

    public RecetteDTO majRecette(UUID id, RecetteDTO recetteDTO) {
        return repository.findById(id).map(recette -> {
            Recette recetteMaj = toEntity(recetteDTO);
            recetteMaj.setId(recette.getId());
            return toDTO(repository.save(recette));
        }).orElseThrow(() -> new RuntimeException("Recette introuvable"));
    }

    public void supprimerRecette(UUID id) {
        if (!repository.existsById(id)) {
            throw new RuntimeException("Recette introuvable");
        }
        repository.deleteById(id);
    }

    // Mapper pour transformer une entité en DTO
    private RecetteDTO toDTO(Recette recette) {
        RecetteDTO recetteDTO = new RecetteDTO();
        recetteDTO.setId(recette.getId());
        recetteDTO.setNom(recette.getNom());
        recette.setInstructions(recette.getInstructions());
        recette.setTpsPrepa(recette.getTpsPrepa());
        recette.setTpsCuisson(recette.getTpsCuisson());
        recette.setTemperature(recette.getTemperature());
        return recetteDTO;
    }

    // Mapper pour transformer un DTO en entité
    private Recette toEntity(RecetteDTO recetteDTO) {
        Recette recette = new Recette();
        if(recetteDTO.getId() != null){
            recette.setId(recetteDTO.getId());
        }
        recette.setTemperature(recetteDTO.getTemperature());
        recette.setNom(recetteDTO.getNom());
        recette.setInstructions(recetteDTO.getInstructions());
        recette.setTpsCuisson(recetteDTO.getTpsCuisson());
        recette.setTpsPrepa(recetteDTO.getTpsPrepa());
        return recette;
    }
}


