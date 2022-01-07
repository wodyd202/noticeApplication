package com.noticeapp.services.notice.infrastructure;

import com.noticeapp.services.notice.domain.Notice;
import com.noticeapp.services.notice.domain.NoticeRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;

@Repository
@Transactional
@RequiredArgsConstructor
public class QuerydslNoticeRepository implements NoticeRepository {
    @PersistenceContext private EntityManager entityManager;

    @Override
    public void save(Notice notice) {
        entityManager.persist(notice);
    }
}
