package com.aulkhami.mavenproject1.enums;

public enum EntityStatus {
    ACTIVE,
    INACTIVE,
    SUSPENDED,
    DELETED;

    public boolean isActive() {
        return this == ACTIVE;
    }
}
