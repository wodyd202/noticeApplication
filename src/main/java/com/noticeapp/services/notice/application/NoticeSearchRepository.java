package com.noticeapp.services.notice.application;

import com.noticeapp.services.notice.application.model.NoticeModel;

import java.util.Optional;

public interface NoticeSearchRepository {
    Optional<NoticeModel> findById(long noticeId);
}
