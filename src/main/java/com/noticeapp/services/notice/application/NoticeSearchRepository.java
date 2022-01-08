package com.noticeapp.services.notice.application;

import com.noticeapp.services.notice.application.model.NoticeModel;
import org.springframework.cache.annotation.Cacheable;

import java.util.Optional;

public interface NoticeSearchRepository {
    @Cacheable(value = "notice", key = "#noticeId", unless = "#result == null")
    Optional<NoticeModel> findById(long noticeId);
}
