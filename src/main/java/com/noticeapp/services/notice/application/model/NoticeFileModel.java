package com.noticeapp.services.notice.application.model;

import com.noticeapp.services.notice.domain.NoticeFile;
import lombok.Getter;

@Getter
public class NoticeFileModel {
    private long fileId;
    private String path;

    private NoticeFileModel(long fileId, String path) {
        this.fileId = fileId;
        this.path = path;
    }

    public static NoticeFileModel mapFrom(NoticeFile noticeFile){
        return new NoticeFileModel(noticeFile.getId(), noticeFile.getPath());
    }
}
