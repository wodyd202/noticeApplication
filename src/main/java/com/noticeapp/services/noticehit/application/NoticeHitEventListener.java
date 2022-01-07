package com.noticeapp.services.noticehit.application;

import com.noticeapp.services.notice.application.event.ReadNoticeEvent;
import com.noticeapp.services.notice.application.event.RemovedNoticeEvent;
import lombok.RequiredArgsConstructor;
import org.springframework.context.event.EventListener;
import org.springframework.scheduling.annotation.Async;
import org.springframework.stereotype.Component;

@Async("noticeHitExecutor")
@Component
@RequiredArgsConstructor
public class NoticeHitEventListener {
    private final NoticeHitRepository noticeHitRepository;

    @EventListener(ReadNoticeEvent.class)
    public void handle(ReadNoticeEvent event){
        noticeHitRepository.increament(event.getNoticeId());
    }

    @EventListener(RemovedNoticeEvent.class)
    public void handle(RemovedNoticeEvent event){
        noticeHitRepository.remove(event.getNoticeId());
    }
}
