package com.noticeapp.services.notice.application.event;

import lombok.AllArgsConstructor;
import lombok.Getter;
import org.springframework.context.ApplicationEvent;

@Getter
@AllArgsConstructor
public class RemovedNoticeEvent {
    private long noticeId;
}
