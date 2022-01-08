package com.noticeapp.services.notice.infrastructure;

import com.noticeapp.services.notice.application.NoticeHitRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.data.redis.core.ValueOperations;
import org.springframework.stereotype.Repository;

import javax.annotation.PostConstruct;

@Repository
@RequiredArgsConstructor
public class NoticeHitRepositoryForNotice implements NoticeHitRepository {
    private final RedisTemplate redisTemplate;
    private ValueOperations valueOperations;

    @PostConstruct
    void setUp(){
        valueOperations = redisTemplate.opsForValue();
    }

    @Override
    public long incrementHit(long noticeId) {
        return valueOperations.increment(noticeId);
    }
}
