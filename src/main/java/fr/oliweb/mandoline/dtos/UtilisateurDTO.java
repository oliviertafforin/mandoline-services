package fr.oliweb.mandoline.dtos;

import java.util.UUID;

public class UtilisateurDTO {

    private UUID id;
    private String pseudo;

    public UUID getId() {
        return id;
    }

    public void setId(UUID id) {
        this.id = id;
    }

    public String getPseudo() {
        return pseudo;
    }

    public void setPseudo(String pseudo) {
        this.pseudo = pseudo;
    }
}
