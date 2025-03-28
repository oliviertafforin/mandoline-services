package fr.oliweb.mandoline.service;

import fr.oliweb.mandoline.dtos.ImageDTO;
import fr.oliweb.mandoline.model.ImageDb;
import fr.oliweb.mandoline.repository.ImageRepository;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

import java.util.Optional;
import java.util.UUID;

@Service
public class ImageService {

    private final ImageRepository repository;

    @Value("${file.upload-dir}")
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
        }).orElseThrow(() -> new RuntimeException("Image introuvable"));
    }

    public void supprimerImage(UUID id) {
        if (!repository.existsById(id)) {
            throw new RuntimeException("Image introuvable");
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
}


