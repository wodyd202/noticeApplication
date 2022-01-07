package com.noticeapp.services.notice.domain;

import lombok.AccessLevel;
import lombok.Getter;
import lombok.NoArgsConstructor;
import org.hibernate.annotations.DynamicUpdate;
import org.springframework.data.annotation.CreatedDate;
import org.springframework.data.jpa.domain.support.AuditingEntityListener;

import javax.persistence.*;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

@Entity
@Table(indexes = {
        @Index(columnList = "startDate, endDate")
})
@Getter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@EntityListeners(AuditingEntityListener.class)
@DynamicUpdate
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

    @OneToMany(cascade = CascadeType.PERSIST, mappedBy = "notice", orphanRemoval = true)
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

    public void addFiles(Writer writer, List<NoticeFile> noticeFiles) {
        verifyHasUpdatePermission(writer);
        files.addAll(noticeFiles);
    }

    public void removeFile(Writer writer, long fileId) {
        verifyHasUpdatePermission(writer);
        for (NoticeFile file : files) {
            if(file.getId() == fileId){
                files.remove(file);
                return;
            }
        }
        throw new NoticeFileNotFoundException();
    }

    public boolean updateTitle(Writer updater, String title) {
        verifyHasUpdatePermission(updater);
        if(this.title.equals(title)){
            return false;
        }
        this.title = title;
        return true;
    }

    public boolean updateContent(Writer updater, String content) {
        verifyHasUpdatePermission(updater);
        if(this.content.equals(content)){
            return false;
        }
        this.content = content;
        return true;
    }

    public boolean updateNoticeDate(Writer updater, NoticeDate noticeDate) {
        verifyHasUpdatePermission(updater);
        if(this.noticeDate.equals(noticeDate)){
            return false;
        }
        this.noticeDate = noticeDate;
        return true;
    }

    private void verifyHasUpdatePermission(Writer updater) {
        if(!hasUpdatePermission(updater)){
            throw new NoUpdateNoticePermissionException();
        }
    }

    private boolean hasUpdatePermission(Writer updater) {
        return this.writer.equals(updater);
    }
}
