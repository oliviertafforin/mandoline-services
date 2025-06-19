package fr.oliweb.mandoline.service;

import fr.oliweb.mandoline.dtos.ImageDTO;
import fr.oliweb.mandoline.exceptions.ExceptionMessages;
import fr.oliweb.mandoline.exceptions.RessourceIntrouvableException;
import fr.oliweb.mandoline.model.ImageDb;
import fr.oliweb.mandoline.repository.ImageRepository;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.io.FileNotFoundException;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.Optional;
import java.util.UUID;

@Service
public class ImageService {
    private Logger logger = LoggerFactory.getLogger(ImageService.class);

    private final ImageRepository repository;

    @Value("${FILE_UPLOAD_DIR}")
    private String uploadDir;

    public ImageService(ImageRepository repository) {
        this.repository = repository;
    }

    public Optional<ImageDTO> getImageParId(UUID id) {
        return repository.findById(id).map(this::toDTO);
    }

    public ImageDTO creerImage(ImageDTO imageDTO) {
        ImageDb image = toEntity(imageDTO);
        ImageDb savedImage = repository.save(image);
        return toDTO(savedImage);
    }

    public ImageDTO majImage(UUID id, ImageDTO imageDTO) {
        return repository.findById(id).map(image -> {
            ImageDb imageMaj = toEntity(imageDTO);
            imageMaj.setId(image.getId());
            return toDTO(repository.save(imageMaj));
        }).orElseThrow(() -> new RessourceIntrouvableException(ExceptionMessages.IMAGE_INTROUVABLE + ", id : " + id));
    }

    public void supprimerImage(UUID id) {
        if (!repository.existsById(id)) {
            throw new RessourceIntrouvableException(ExceptionMessages.IMAGE_INTROUVABLE + ", id : " + id);
        }
        repository.deleteById(id);
    }

    // Mapper pour transformer une entité en DTO
    private ImageDTO toDTO(ImageDb image) {
        ImageDTO imageDTO = new ImageDTO();
        imageDTO.setId(image.getId());
        imageDTO.setLibelle(image.getLibelle());
        imageDTO.setPath(image.getPath());
        return imageDTO;
    }

    // Mapper pour transformer un DTO en entité
    private ImageDb toEntity(ImageDTO imageDTO) {
        ImageDb image = new ImageDb();
        if (imageDTO.getId() != null) {
            image.setId(imageDTO.getId());
        }
        image.setPath(imageDTO.getPath());
        image.setLibelle(imageDTO.getLibelle());
        return image;
    }

    /**
     * Charge le fichier image dans la mémoire de l'application
     *
     * @param id   id de l'object ImageDTO
     * @param file fichier image
     * @return imageDTO
     * @throws IOException
     */
    public ImageDTO uploadImage(UUID id, MultipartFile file) throws IOException {
        // Générer un nom de fichier unique ou utiliser le nom d'origine
        String fileName = file.getOriginalFilename();
        //Création du path à partir du nouveau nom et du path du répertoire d'images
        Path filePath = Paths.get(uploadDir, fileName);
        try {
            //si on retrouve l'imageDTO
            ImageDTO majDto = getImageParId(id).map(imageDTO -> {
                //on supprime l'image prec
                try {
                    Path prevImagePath = Paths.get(uploadDir, imageDTO.getPath());
                    Files.delete(prevImagePath);
                } catch (Exception e) {
                    logger.error("Suppression du fichier " + imageDTO.getPath() + " échouée. Celui n'est plus utilisé, il faudrait le supprimer manuellement.");
                }
                //maj path
                imageDTO.setPath(filePath.getFileName().toString());
                majImage(id, imageDTO);
                return imageDTO;
            }).orElseThrow(FileNotFoundException::new);

            // Sauvegarder le fichier dans le répertoire
            Files.write(filePath, file.getBytes());
            return majDto;
        } catch (IOException e) {
            throw new IOException("Fichier non trouvé : " + fileName);
        }
    }
}


