package fr.oliweb.mandoline.dtos;

import java.util.UUID;

public class UtilisateurDTO {

    private UUID id;
    private String pseudo;
    private RoleDTO role;
    private String mdp;
    private ImageDTO avatar;

    public RoleDTO getRole() {
        return role;
    }

    public void setRole(RoleDTO role) {
        this.role = role;
    }

    public String getMdp() {
        return mdp;
    }

    public void setMdp(String mdp) {
        this.mdp = mdp;
    }

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

    public ImageDTO getAvatar() {
        return avatar;
    }

    public void setAvatar(ImageDTO avatar) {
        this.avatar = avatar;
    }
}
