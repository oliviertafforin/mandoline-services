package fr.oliweb.mandoline.service;

import fr.oliweb.mandoline.config.JwtUtil;
import fr.oliweb.mandoline.dtos.LoginRequeteDTO;
import fr.oliweb.mandoline.dtos.RecetteDTO;
import fr.oliweb.mandoline.dtos.RoleDTO;
import fr.oliweb.mandoline.dtos.UtilisateurDTO;
import fr.oliweb.mandoline.enums.RoleEnum;
import fr.oliweb.mandoline.exceptions.ExceptionMessages;
import fr.oliweb.mandoline.exceptions.RessourceIntrouvableException;
import fr.oliweb.mandoline.mappers.RoleMapper;
import fr.oliweb.mandoline.mappers.UtilisateurMapper;
import fr.oliweb.mandoline.model.UtilisateurDb;
import fr.oliweb.mandoline.repository.RoleRepository;
import fr.oliweb.mandoline.repository.UtilisateurRepository;
import jakarta.validation.ValidationException;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;
import java.util.UUID;

@Service
public class UtilisateurService {

    private final UtilisateurRepository repository;
    private final RoleRepository roleRepository;
    private final PasswordEncoder passwordEncoder;
    private final AuthenticationManager authenticationManager;
    private final JwtUtil jwtUtil;
    private final RecetteLikeeService recetteLikeeService;

    public UtilisateurService(RecetteLikeeService recetteLikeeService, UtilisateurRepository repository, RoleRepository roleRepository, PasswordEncoder passwordEncoder, AuthenticationManager authenticationManager, JwtUtil jwtUtil) {
        this.repository = repository;
        this.recetteLikeeService = recetteLikeeService;
        this.roleRepository = roleRepository;
        this.passwordEncoder = passwordEncoder;
        this.authenticationManager = authenticationManager;
        this.jwtUtil = jwtUtil;
    }

    public List<UtilisateurDTO> getAllUtilisateurs() {
        return repository.findAll().stream()
                .map(UtilisateurMapper::toDto)
                .toList();
    }

    public Optional<UtilisateurDTO> getUtilisateurParId(UUID id) {
        return repository.findById(id).map(UtilisateurMapper::toDto);
    }

    /**
     * Requete d'enregistrement d'un nouvel utilisateur
     *
     * @param loginRequeteDTO
     * @return
     */
    public UtilisateurDTO nouvelUtilisateur(LoginRequeteDTO loginRequeteDTO) {
        if (repository.existsByPseudo(loginRequeteDTO.getPseudo())) {
            throw new ValidationException("Nom déjà pris !");
        }
        UtilisateurDTO utilisateurDTO = new UtilisateurDTO();
        RoleDTO roleUtilisateurDefaut = roleRepository.findById(RoleEnum.DEFAUT.getRole())
                .map(RoleMapper::toDto)
                .orElseThrow(() -> new IllegalStateException("Le rôle utilisateur par défaut est introuvable !"));
        utilisateurDTO.setPseudo(loginRequeteDTO.getPseudo());
        utilisateurDTO.setRole(roleUtilisateurDefaut);
        utilisateurDTO.setMdp(passwordEncoder.encode(loginRequeteDTO.getMdp()));
        return creerUtilisateur(utilisateurDTO);
    }

    /**
     * Requete d'authentification
     *
     * @param loginRequest
     * @return
     */
    public String authentifierUtilisateur(LoginRequeteDTO loginRequest) {
        // Vérification des credentials
        authenticationManager.authenticate(new UsernamePasswordAuthenticationToken(
                loginRequest.getPseudo(), loginRequest.getMdp()));

        // Récupération de l'utilisateur
        UtilisateurDb utilisateurDb = repository.findByPseudo(loginRequest.getPseudo())
                .orElseThrow(() -> new RessourceIntrouvableException(ExceptionMessages.UTILISATEUR_INTROUVABLE + ", pseudo : " + loginRequest.getPseudo()));

        // Génération du token JWT
        return jwtUtil.generateToken(utilisateurDb);
    }

    /**
     * Enregistrement d'un objet utilisateur en base
     *
     * @param utilisateurDTO
     * @return
     */
    public UtilisateurDTO creerUtilisateur(UtilisateurDTO utilisateurDTO) {
        UtilisateurDb utilisateur = UtilisateurMapper.toDb(utilisateurDTO);
        UtilisateurDb utilisateurEnregistre = repository.save(utilisateur);
        return UtilisateurMapper.toDto(utilisateurEnregistre);
    }

    public UtilisateurDTO majUtilisateur(UUID id, UtilisateurDTO utilisateurDTO) {
        return repository.findById(id).map(utilisateur -> {
            UtilisateurDb utilisateurMaj = UtilisateurMapper.toDb(utilisateurDTO);
            utilisateurMaj.setId(utilisateur.getId());
            return UtilisateurMapper.toDto(repository.save(utilisateur));
        }).orElseThrow(() -> new RessourceIntrouvableException(ExceptionMessages.UTILISATEUR_INTROUVABLE + ", id : " + id));
    }

    public void supprimerUtilisateur(UUID id) {
        if (!repository.existsById(id)) {
            throw new RessourceIntrouvableException(ExceptionMessages.UTILISATEUR_INTROUVABLE + ", id : " + id);
        }
        repository.deleteById(id);
    }

    public List<RecetteDTO> getRecettePrefereesByUtilisateur(UUID id) {
        return getUtilisateurParId(id)
                .map(recetteLikeeService::getRecettesPreferees)
                .orElseThrow(() -> new RessourceIntrouvableException(ExceptionMessages.UTILISATEUR_INTROUVABLE + ", id : " + id));
    }
}


