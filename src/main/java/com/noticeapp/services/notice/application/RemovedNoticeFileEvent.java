package com.noticeapp.services.notice.application;

import lombok.AllArgsConstructor;
import lombok.Getter;
import org.springframework.context.ApplicationEvent;

@Getter
@AllArgsConstructor
public class RemovedNoticeFileEvent {
    private long noticeId;
    private long fileId;
}
