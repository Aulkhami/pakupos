package com.aulkhami.pakupos.enums;

public enum EntityStatus {
    ACTIVE,
    INACTIVE,
    SUSPENDED,
    DELETED;

    public boolean isActive() {
        return this == ACTIVE;
    }
}
