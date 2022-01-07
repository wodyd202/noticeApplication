package com.noticeapp.services.notice.domain;

import com.noticeapp.services.notice.domain.Notice;

public interface NoticeRepository {
    void save(Notice notice);
}
