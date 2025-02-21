package fr.oliweb.mandoline.service;

import fr.oliweb.mandoline.config.JwtUtil;
import fr.oliweb.mandoline.dtos.LoginRequeteDTO;
import fr.oliweb.mandoline.dtos.RoleDTO;
import fr.oliweb.mandoline.dtos.UtilisateurDTO;
import fr.oliweb.mandoline.enums.RoleEnum;
import fr.oliweb.mandoline.mappers.RoleMapper;
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

    public UtilisateurService(UtilisateurRepository repository, RoleRepository roleRepository, PasswordEncoder passwordEncoder, AuthenticationManager authenticationManager, JwtUtil jwtUtil) {
        this.repository = repository;
        this.roleRepository = roleRepository;
        this.passwordEncoder = passwordEncoder;
        this.authenticationManager = authenticationManager;
        this.jwtUtil = jwtUtil;
    }

    public List<UtilisateurDTO> getAllUtilisateurs() {
        return repository.findAll().stream()
                .map(this::toDTO)
                .toList();
    }

    public Optional<UtilisateurDTO> getUtilisateurParId(UUID id) {
        return repository.findById(id).map(this::toDTO);
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
                .orElseThrow(() -> new ValidationException("Utilisateur non trouvé"));

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
        utilisateurDTO.setMdp(utilisateur.getMdp());
        utilisateurDTO.setRole(RoleMapper.toDto(utilisateur.getRole()));
        return utilisateurDTO;
    }

    // Mapper pour transformer un DTO en entité
    private UtilisateurDb toEntity(UtilisateurDTO utilisateurDTO) {
        UtilisateurDb utilisateur = new UtilisateurDb();
        if (utilisateurDTO.getId() != null) {
            utilisateur.setId(utilisateurDTO.getId());
        }
        utilisateur.setPseudo(utilisateurDTO.getPseudo());
        utilisateur.setMdp(utilisateurDTO.getMdp());
        utilisateur.setRole(RoleMapper.toDb(utilisateurDTO.getRole()));
        return utilisateur;
    }
}


