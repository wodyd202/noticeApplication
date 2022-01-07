package com.noticeapp.services.notice.domain;

import java.util.Optional;

public interface NoticeRepository {
    void save(Notice notice);
    Optional<Notice> findById(long noticeId);
}
