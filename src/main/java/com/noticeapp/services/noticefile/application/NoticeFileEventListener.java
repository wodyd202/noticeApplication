package com.noticeapp.services.noticefile.application;

import com.noticeapp.services.notice.application.SaveNoticeFile;
import com.noticeapp.services.notice.application.SavedNoticeFileEvent;
import org.springframework.context.event.EventListener;
import org.springframework.scheduling.annotation.Async;
import org.springframework.stereotype.Component;

import java.util.List;

@Async("noticeFileExecutor")
@Component
public class NoticeFileEventListener {

    @EventListener
    public void handle(SavedNoticeFileEvent event){
        List<SaveNoticeFile> savedNoticeFiles = event.getSavedNoticeFiles();
        System.out.println("파일 저장");
    }
}
