package fr.oliweb.mandoline.model;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.Id;
import jakarta.persistence.Table;
import org.springframework.security.core.GrantedAuthority;

@Entity
@Table(name = "role")
public class RoleDb implements GrantedAuthority {

    @Id
    @Column(name="libelle")
    private String libelle;

    @Override
    public String getAuthority() {
        return libelle;
    }

    public String getLibelle() {
        return libelle;
    }

    public void setLibelle(String libelle) {
        this.libelle = libelle;
    }
}
