package com.noticeapp.services.notice.application.model;

import lombok.Getter;

@Getter
public class NoticeResponse {
    private NoticeModel notice;
    private long totalHit;

    private NoticeResponse(NoticeModel notice, long totalHit) {
        this.notice = notice;
        this.totalHit = totalHit + 1;
    }

    public static NoticeResponse of(NoticeModel noticeModel, long totalHit) {
        return new NoticeResponse(noticeModel, totalHit);
    }
}
