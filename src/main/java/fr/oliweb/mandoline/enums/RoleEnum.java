package fr.oliweb.mandoline.enums;

public enum RoleEnum {
    DEFAUT("defaut"),
    ADMIN("admin");

    private final String role;

    RoleEnum(String role) {
        this.role = role;
    }

    public String getRole() {
        return role;
    }

    @Override
    public String toString() {
        return role;
    }
}