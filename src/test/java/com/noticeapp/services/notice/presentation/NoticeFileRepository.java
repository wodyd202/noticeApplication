package com.noticeapp.services.notice.presentation;

import com.noticeapp.services.notice.domain.NoticeFile;
import org.springframework.data.jpa.repository.JpaRepository;

public interface NoticeFileRepository extends JpaRepository<NoticeFile, Long> {
}
