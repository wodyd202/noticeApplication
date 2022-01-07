package com.noticeapp.services.notice.domain;

import lombok.AccessLevel;
import lombok.Getter;
import lombok.NoArgsConstructor;
import org.springframework.data.annotation.CreatedDate;
import org.springframework.data.jpa.domain.support.AuditingEntityListener;

import javax.persistence.*;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

@Entity
@Table(indexes = {
        @Index(columnList = "startDate, endDate")
})
@Getter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@EntityListeners(AuditingEntityListener.class)
public class Notice {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private long id;
    private String title;
    private String content;

    @Embedded
    @AttributeOverride(name = "id", column = @Column(name = "writer_id", nullable = false))
    private Writer writer;

    @Embedded
    private NoticeDate noticeDate;

    @OneToMany(cascade = CascadeType.PERSIST, mappedBy = "notice")
    private List<NoticeFile> files = new ArrayList<>();

    @CreatedDate
    private LocalDate createDate;

    private Notice(String title, String content, Writer writer, NoticeDate noticeDate) {
        this.title = title;
        this.content = content;
        this.writer = writer;
        this.noticeDate = noticeDate;
    }

    public static Notice of(String title, String content, Writer writer, NoticeDate noticeDate){
        return new Notice(title, content, writer, noticeDate);
    }

    public void addFiles(List<NoticeFile> noticeFiles) {
        files.addAll(noticeFiles);
    }
}
