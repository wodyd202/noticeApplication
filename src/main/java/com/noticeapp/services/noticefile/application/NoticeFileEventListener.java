package com.noticeapp.services.noticefile.application;

import com.noticeapp.services.notice.application.event.RemovedNoticeEvent;
import com.noticeapp.services.notice.application.event.RemovedNoticeFileEvent;
import com.noticeapp.services.notice.application.event.SaveNoticeFile;
import com.noticeapp.services.notice.application.event.SavedNoticeFileEvent;
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

    @EventListener
    public void handle(RemovedNoticeFileEvent event){
        System.out.println("파일 삭제");
    }

    @EventListener
    public void handle(RemovedNoticeEvent event){
        System.out.println("공지사항 삭제로 인한 파일 삭제");
    }
}
