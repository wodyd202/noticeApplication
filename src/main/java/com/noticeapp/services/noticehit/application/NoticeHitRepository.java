package com.noticeapp.services.noticehit.application;

public interface NoticeHitRepository {
    void increament(long noticeId);
    void remove(long noticeId);
}
