package com.noticeapp.services.notice.application.model;

import com.noticeapp.services.notice.domain.Notice;
import com.noticeapp.services.notice.domain.NoticeDate;
import com.noticeapp.services.notice.domain.NoticeFile;
import lombok.AccessLevel;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

import java.io.Serializable;
import java.time.LocalDate;
import java.util.List;
import java.util.stream.Collectors;

@Getter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class NoticeModel implements Serializable {
    private long id;
    private String title;
    private String content;
    private NoticeDate noticeDate;
    private LocalDate createDate;
    private List<NoticeFileModel> noticeFiles;

    public NoticeModel(long id,
                       String title,
                       String content,
                       NoticeDate noticeDate,
                       LocalDate createDate){
        this.id = id;
        this.title = title;
        this.content = content;
        this.noticeDate = noticeDate;
        this.createDate = createDate;
    }

    @Builder(access = AccessLevel.PRIVATE)
    public NoticeModel(long id,
                       String title,
                       String content,
                       NoticeDate noticeDate,
                       LocalDate createDate,
                       List<NoticeFile> noticeFiles) {
        this(id,title,content,noticeDate,createDate);
        if(noticeFiles != null){
            this.noticeFiles = noticeFiles.stream().map(NoticeFileModel::mapFrom).collect(Collectors.toList());
        }
    }

    public static NoticeModel mapFrom(Notice notice) {
        return NoticeModel.builder()
                .id(notice.getId())
                .title(notice.getTitle())
                .content(notice.getContent())
                .noticeDate(notice.getNoticeDate())
                .createDate(notice.getCreateDate())
                .noticeFiles(notice.getFiles())
                .build();
    }
}
