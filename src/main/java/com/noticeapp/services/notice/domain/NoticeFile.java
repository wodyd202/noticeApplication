package com.noticeapp.services.notice.domain;

import lombok.AccessLevel;
import lombok.Getter;
import lombok.NoArgsConstructor;
import org.springframework.web.multipart.MultipartFile;

import javax.persistence.*;

@Entity
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class NoticeFile {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Getter
    private long id;

    @Getter
    private String path;

    @ManyToOne
    private Notice notice;

    @Transient
    @Getter
    private MultipartFile file;

    private NoticeFile(Notice notice, MultipartFile file, String path){
        this.notice = notice;
        this.path = path;
        this.file = file;
    }

    public static NoticeFile of(Notice notice, MultipartFile file, String path) {
        return new NoticeFile(notice, file, path);
    }
}
