package fr.oliweb.mandoline.service;

import fr.oliweb.mandoline.dtos.UtilisateurDTO;
import fr.oliweb.mandoline.model.UtilisateurDb;
import fr.oliweb.mandoline.repository.UtilisateurRepository;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;
import java.util.UUID;

@Service
public class UtilisateurService {

    private final UtilisateurRepository repository;

    public UtilisateurService(UtilisateurRepository repository) {
        this.repository = repository;
    }

    public List<UtilisateurDTO> getAllUtilisateurs() {
        return repository.findAll().stream()
                .map(this::toDTO)
                .toList();
    }

    public Optional<UtilisateurDTO> getUtilisateurParId(UUID id) {
        return repository.findById(id).map(this::toDTO);
    }

    public UtilisateurDTO creerUtilisateur(UtilisateurDTO utilisateurDTO) {
        UtilisateurDb utilisateur = toEntity(utilisateurDTO);
        UtilisateurDb utilisateurEnregistre = repository.save(utilisateur);
        return toDTO(utilisateurEnregistre);
    }

    public UtilisateurDTO majUtilisateur(UUID id, UtilisateurDTO utilisateurDTO) {
        return repository.findById(id).map(utilisateur -> {
            UtilisateurDb utilisateurMaj = toEntity(utilisateurDTO);
            utilisateurMaj.setId(utilisateur.getId());
            return toDTO(repository.save(utilisateur));
        }).orElseThrow(() -> new RuntimeException("Recette introuvable"));
    }

    public void supprimerUtilisateur(UUID id) {
        if (!repository.existsById(id)) {
            throw new RuntimeException("Utilisateur introuvable");
        }
        repository.deleteById(id);
    }

    // Mapper pour transformer une entité en DTO
    private UtilisateurDTO toDTO(UtilisateurDb utilisateur) {
        UtilisateurDTO utilisateurDTO = new UtilisateurDTO();
        utilisateurDTO.setId(utilisateur.getId());
        utilisateurDTO.setPseudo(utilisateur.getPseudo());
        return utilisateurDTO;
    }

    // Mapper pour transformer un DTO en entité
    private UtilisateurDb toEntity(UtilisateurDTO utilisateurDTO) {
        UtilisateurDb utilisateur = new UtilisateurDb();
        if(utilisateurDTO.getId() != null){
            utilisateur.setId(utilisateurDTO.getId());
        }
        utilisateur.setPseudo(utilisateurDTO.getPseudo());
        return utilisateur;
    }
}


