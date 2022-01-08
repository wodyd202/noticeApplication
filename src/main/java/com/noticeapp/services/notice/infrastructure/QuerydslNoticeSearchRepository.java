package com.noticeapp.services.notice.infrastructure;

import com.noticeapp.services.notice.application.NoticeSearchRepository;
import com.noticeapp.services.notice.application.model.NoticeModel;
import com.noticeapp.services.notice.domain.QNotice;
import com.querydsl.core.types.Projections;
import com.querydsl.jpa.impl.JPAQueryFactory;
import lombok.RequiredArgsConstructor;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

import java.util.Optional;

import static com.noticeapp.services.notice.domain.QNotice.notice;
import static com.querydsl.core.types.dsl.Expressions.asSimple;

@Repository
@RequiredArgsConstructor
@Transactional(readOnly = true)
public class QuerydslNoticeSearchRepository implements NoticeSearchRepository {
    private final JPAQueryFactory jpaQueryFactory;

    @Override
    public Optional<NoticeModel> findById(long noticeId) {
        return Optional.ofNullable(
                jpaQueryFactory.select(Projections.constructor(NoticeModel.class,
                                asSimple(noticeId),
                                notice.title,
                                notice.content,
                                notice.noticeDate(),
                                notice.createDate
                            ))
                        .from(notice)
                        .where(notice.id.eq(noticeId))
                        .fetchFirst()
        );
    }
}
