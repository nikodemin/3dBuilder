package com.niko.prokat.Model.enums;

public enum UserRole {
    ADMIN("ROLE_ADMIN"),
    USER("ROLE_USER");

    private String role;

    UserRole(String role){
        this.role = role;
    }

    @Override
    public String toString() {
        return role;
    }
}
