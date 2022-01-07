package com.noticeapp.services.notice.application;

import com.noticeapp.services.notice.domain.Notice;
import com.noticeapp.services.notice.domain.NoticeDate;
import lombok.AccessLevel;
import lombok.Builder;
import lombok.Getter;

import java.time.LocalDate;

@Getter
public class NoticeModel {
    private long id;
    private String title;
    private String content;
    private NoticeDate noticeDate;
    private LocalDate createDate;

    @Builder(access = AccessLevel.PRIVATE)
    public NoticeModel(long id, String title, String content, NoticeDate noticeDate, LocalDate createDate) {
        this.id = id;
        this.title = title;
        this.content = content;
        this.noticeDate = noticeDate;
        this.createDate = createDate;
    }

    public static NoticeModel mapFrom(Notice notice) {
        return NoticeModel.builder()
                .id(notice.getId())
                .title(notice.getTitle())
                .content(notice.getContent())
                .noticeDate(notice.getNoticeDate())
                .createDate(notice.getCreateDate())
                .build();
    }
}
