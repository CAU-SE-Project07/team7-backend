package com.SEProject.SEP.user;

import lombok.Getter;

@Getter
public enum UserRole {
    ADMIN("ROLE_ADMIN"),  //어드민
    USER("ROLE_USER");   //어드민 제외 나머지

    UserRole(String value) {
        this.value = value;
    }

    private String value;
}
