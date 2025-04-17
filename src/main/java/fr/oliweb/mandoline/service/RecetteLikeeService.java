package fr.oliweb.mandoline.service;

import fr.oliweb.mandoline.dtos.RecetteDTO;
import fr.oliweb.mandoline.dtos.UtilisateurDTO;
import fr.oliweb.mandoline.exceptions.ExceptionMessages;
import fr.oliweb.mandoline.exceptions.RessourceIntrouvableException;
import fr.oliweb.mandoline.mappers.RecetteMapper;
import fr.oliweb.mandoline.model.RecetteDb;
import fr.oliweb.mandoline.model.RecetteLikeeDb;
import fr.oliweb.mandoline.model.RecetteLikeePk;
import fr.oliweb.mandoline.model.UtilisateurDb;
import fr.oliweb.mandoline.repository.RecetteLikeeRepository;
import fr.oliweb.mandoline.repository.RecetteRepository;
import fr.oliweb.mandoline.repository.UtilisateurRepository;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.*;
import java.util.stream.Collectors;

@Service
public class RecetteLikeeService {

    private final RecetteLikeeRepository repository;
    private final RecetteRepository recetteRepository;
    private final UtilisateurRepository utilisateurRepository;

    public RecetteLikeeService(RecetteLikeeRepository repository, RecetteRepository recetteRepository, UtilisateurRepository utilisateurRepository) {
        this.repository = repository;
        this.recetteRepository = recetteRepository;
        this.utilisateurRepository = utilisateurRepository;
    }

    public List<RecetteDTO> getRecettesPreferees(UtilisateurDTO utilisateurDTO) {

        List<RecetteLikeeDb> recetteLikeeList = repository.findByUtilisateurId(utilisateurDTO.getId());

        return recetteLikeeList
                .stream()
                .map(r -> RecetteMapper.toDto(r.getRecette()))
                .toList();
    }

    @Transactional
    public void likeRecette(UUID idUtilisateur, UUID idRecette) {
        RecetteLikeePk pk = new RecetteLikeePk(idUtilisateur, idRecette);

        repository.findById(pk).ifPresentOrElse(
                repository::delete,
                () -> saveNewRecetteLikee(idUtilisateur, idRecette, pk)
        );
    }

    private void saveNewRecetteLikee(UUID idUtilisateur, UUID idRecette, RecetteLikeePk pk) {
        UtilisateurDb utilisateurDb = utilisateurRepository.findById(idUtilisateur)
                .orElseThrow(() -> new RessourceIntrouvableException(ExceptionMessages.UTILISATEUR_INTROUVABLE + ", id : "+idUtilisateur));
        RecetteDb recetteDb = recetteRepository.findById(idRecette)
                .orElseThrow(() -> new RessourceIntrouvableException(ExceptionMessages.RECETTE_INTROUVABLE + ", id : "+idUtilisateur));
        RecetteLikeeDb recetteLikeeDb = createRecetteLikeeDb(pk, utilisateurDb, recetteDb);
        repository.save(recetteLikeeDb);
    }

    private RecetteLikeeDb createRecetteLikeeDb(RecetteLikeePk pk, UtilisateurDb utilisateurDb, RecetteDb recetteDb) {
        RecetteLikeeDb recetteLikeeDb = new RecetteLikeeDb();
        recetteLikeeDb.setPk(pk);
        recetteLikeeDb.setUtilisateur(utilisateurDb);
        recetteLikeeDb.setRecette(recetteDb);
        return recetteLikeeDb;
    }


    private Set<UUID> parseRecettesLikees(String recettesLikees) {
        if (recettesLikees == null || recettesLikees.isEmpty()) {
            return new HashSet<>();
        }
        return Arrays.stream(recettesLikees.split(", "))
                .map(UUID::fromString)
                .collect(Collectors.toSet());
    }

    private void toggleRecetteLike(Set<UUID> recettesSet, UUID idRecette) {
        if (recettesSet.contains(idRecette)) {
            recettesSet.remove(idRecette);
        } else {
            recettesSet.add(idRecette);
        }
    }

    private String convertSetToString(Set<UUID> recettesSet) {
        return recettesSet.stream()
                .map(UUID::toString)
                .collect(Collectors.joining(", "));
    }
}


