package org.wzstudio.spring.finance.data.domain;

public enum ETFIndexConfigType {
    UNKNOWN("Unknown"), ISHARE_US("iShare US"), ISHARE_AU("iShare AU");

    public final String description;
    ETFIndexConfigType(String description) {
        this.description = description;
    }
}
