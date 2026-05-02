package com.aulkhami.mavenproject1.enums;

public enum UserRole {
    ADMIN("ADMIN", "Administrator"),
    MANAGER("MANAGER", "Manager"),
    CASHIER("CASHIER", "Cashier"),
    STAFF("STAFF", "Staff");

    private final String code;
    private final String label;

    UserRole(String code, String label) {
        this.code = code;
        this.label = label;
    }

    public String getCode() {
        return code;
    }

    public String getLabel() {
        return label;
    }

    public static UserRole fromCode(String code) {
        if (code == null || code.isBlank()) {
            throw new IllegalArgumentException("Role code cannot be null or blank");
        }

        for (UserRole role : values()) {
            if (role.code.equalsIgnoreCase(code.trim())) {
                return role;
            }
        }

        throw new IllegalArgumentException("Unknown role code: " + code);
    }
}
