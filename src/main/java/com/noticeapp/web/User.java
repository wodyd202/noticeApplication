package com.noticeapp.web;

import lombok.Getter;

@Getter
public class User {
    private String identifier;

    private User(String authenciation){
        if(isInvalidAuthenciation(authenciation)){
            throw new EmptyAuthenticationHeaderException();
        }
        this.identifier = authenciation;
    }

    private boolean isInvalidAuthenciation(String authenciation) {
        return authenciation == null;
    }

    public static User fromAuthentication(String authentication) {
        return new User(authentication);
    }
}
