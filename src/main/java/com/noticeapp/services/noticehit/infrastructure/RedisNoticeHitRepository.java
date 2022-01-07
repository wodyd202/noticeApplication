package com.noticeapp.services.noticehit.infrastructure;

import com.noticeapp.services.noticehit.application.NoticeHitRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.data.redis.core.ValueOperations;
import org.springframework.stereotype.Repository;

import javax.annotation.PostConstruct;

@Repository
@RequiredArgsConstructor
public class RedisNoticeHitRepository implements NoticeHitRepository {
    private final RedisTemplate redisTemplate;
    private ValueOperations valueOperations;

    @PostConstruct
    void setUp(){
        valueOperations = redisTemplate.opsForValue();
    }

    @Override
    public void increament(long noticeId) {
        if(valueOperations.get(noticeId) == null){
            valueOperations.set(noticeId, 1);
            return;
        }
        long hit = Long.parseLong(valueOperations.get(noticeId).toString()) + 1;
        valueOperations.set(noticeId, hit);
    }

    @Override
    public void remove(long noticeId) {
        redisTemplate.delete(noticeId);
    }
}
