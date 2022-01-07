package com.noticeapp.services.notice.application;

import com.noticeapp.services.notice.domain.Notice;
import com.noticeapp.services.notice.domain.NoticeDate;
import com.noticeapp.services.notice.domain.NoticeFile;
import com.noticeapp.services.notice.domain.Writer;
import org.springframework.stereotype.Component;

import java.util.List;

@Component
public class NoticeFactory {
    public Notice createBy(Writer writer, RegisterNotice registerNotice) {
        NoticeDate noticeDate = NoticeDate.of(registerNotice.getStartDate(), registerNotice.getEndDate());
        return Notice.of(registerNotice.getTitle(),
                        registerNotice.getContent(),
                        writer,
                        noticeDate);
    }
}
