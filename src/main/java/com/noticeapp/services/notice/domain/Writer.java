package com.noticeapp.services.notice.domain;

import lombok.AccessLevel;
import lombok.Getter;
import lombok.NoArgsConstructor;

import javax.persistence.Embeddable;

@Getter
@Embeddable
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class Writer {
    private String id;

    private Writer(String id){
        this.id = id;
    }

    public static Writer of(String id) {
        return new Writer(id);
    }
}
