package com.noticeapp.services.notice.infrastructure;

import com.noticeapp.services.notice.domain.Notice;
import com.noticeapp.services.notice.domain.NoticeRepository;
import com.noticeapp.services.notice.domain.QNotice;
import com.querydsl.jpa.impl.JPAQueryFactory;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import java.util.Optional;

import static com.noticeapp.services.notice.domain.QNotice.notice;

@Repository
@Transactional
@RequiredArgsConstructor
public class QuerydslNoticeRepository implements NoticeRepository {
    private final JPAQueryFactory jpaQueryFactory;
    @PersistenceContext private EntityManager entityManager;

    @Override
    public void save(Notice notice) {
        entityManager.persist(notice);
    }

    @Override
    public Optional<Notice> findById(long noticeId) {
        return Optional.ofNullable(
                jpaQueryFactory.selectFrom(notice)
                        .where(notice.id.eq(noticeId))
                        .fetchFirst()
        );
    }
}
