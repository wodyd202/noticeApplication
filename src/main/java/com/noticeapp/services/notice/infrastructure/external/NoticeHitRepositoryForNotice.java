package com.noticeapp.services.notice.infrastructure.external;

import com.noticeapp.services.notice.application.external.NoticeHitRepository;
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
    public long getNoticeHit(long noticeId) {
        Object obj = valueOperations.get(noticeId);
        if(obj == null){
            valueOperations.set(noticeId, 1L);
            return 1;
        }
        return Long.parseLong(valueOperations.get(noticeId).toString());
    }
}
