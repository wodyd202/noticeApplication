package com.noticeapp.services.notice.application.event;

import lombok.Getter;
import org.springframework.web.multipart.MultipartFile;

@Getter
public class SaveNoticeFile {
    private long noticeId;
    private String path;
    private MultipartFile file;

    private SaveNoticeFile(long noticeId, String path, MultipartFile file) {
        this.noticeId = noticeId;
        this.path = path;
        this.file = file;
    }

    public static SaveNoticeFile of(long noticeId, String path, MultipartFile file){
        return new SaveNoticeFile(noticeId, path,file);
    }
}
