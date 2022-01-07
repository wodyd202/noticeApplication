package com.noticeapp.services.notice.application.model;

import lombok.Getter;

import java.time.LocalDate;

@Getter
public class UpdateNotice {
    private String title;
    private String content;
    private LocalDate startDate;
    private LocalDate endDate;

    private UpdateNotice(String title, String content, LocalDate startDate, LocalDate endDate) {
        this.title = title;
        this.content = content;
        this.startDate = startDate;
        this.endDate = endDate;
    }

    public static UpdateNotice of(String title, String content, LocalDate startDate, LocalDate endDate) {
        return new UpdateNotice(title, content, startDate, endDate);
    }
}
