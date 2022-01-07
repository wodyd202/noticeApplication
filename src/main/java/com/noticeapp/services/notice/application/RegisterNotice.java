package com.noticeapp.services.notice.application;

import lombok.Getter;
import org.springframework.web.multipart.MultipartFile;

import java.time.LocalDate;
import java.util.List;

@Getter
public class RegisterNotice {
    private String title;
    private String content;
    private LocalDate startDate;
    private LocalDate endDate;
    private List<MultipartFile> files;

    private RegisterNotice(String title, String content, LocalDate startDate, LocalDate endDate, List<MultipartFile> files) {
        this.title = title;
        this.content = content;
        this.startDate = startDate;
        this.endDate = endDate;
        this.files = files;
    }

    public static RegisterNotice of(String title, String content, LocalDate startDate, LocalDate endDate, List<MultipartFile> files){
        return new RegisterNotice(title,content,startDate,endDate, files);
    }

    public boolean hasFiles() {
        return files != null && !files.isEmpty();
    }
}
