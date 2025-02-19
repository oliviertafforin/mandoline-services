package fr.oliweb.mandoline.model;

import com.fasterxml.jackson.annotation.JsonProperty;
import jakarta.persistence.*;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;

import java.util.Collection;
import java.util.List;
import java.util.UUID;

@Entity
@Table(name = "utilisateur")
public class UtilisateurDb implements UserDetails {
    @Id
    @JsonProperty(value = "Id")
    @Column(name = "id")
    @GeneratedValue(strategy = GenerationType.UUID)
    private UUID id;

    @Column(name = "pseudo", nullable = false, unique = true)
    private String pseudo;

    @Column(nullable = false)
    private String mdp;

    @ManyToOne
    @JoinColumn(name = "role")
    private RoleDb role;

    @OneToOne
    @JoinColumn(name = "avatar", referencedColumnName = "id")
    private ImageDb avatar;

    public ImageDb getAvatar() {
        return avatar;
    }

    public void setAvatar(ImageDb avatar) {
        this.avatar = avatar;
    }

    public void setId(UUID id) {
        this.id = id;
    }

    public UUID getId() {
        return id;
    }

    public String getPseudo() {
        return pseudo;
    }

    public void setPseudo(String pseudo) {
        this.pseudo = pseudo;
    }

    public String getMdp() {
        return mdp;
    }

    public void setMdp(String mdp) {
        this.mdp = mdp;
    }

    public RoleDb getRole() {
        return role;
    }

    public void setRole(RoleDb role) {
        this.role = role;
    }

    @Override
    public Collection<? extends GrantedAuthority> getAuthorities() {
        return List.of(role);
    }

    @Override
    public String getPassword() {
        return mdp;
    }

    @Override
    public String getUsername() {
        return pseudo;
    }

    @Override
    public boolean isAccountNonExpired() {
        return true;
    }

    @Override
    public boolean isAccountNonLocked() {
        return true;
    }

    @Override
    public boolean isCredentialsNonExpired() {
        return true;
    }

    @Override
    public boolean isEnabled() {
        return true;
    }
}
