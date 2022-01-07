package com.noticeapp.services.notice.application;

import lombok.AllArgsConstructor;
import lombok.Getter;
import org.springframework.context.ApplicationEvent;

import java.util.List;

@Getter
@AllArgsConstructor
public class SavedNoticeFileEvent {
    private List<SaveNoticeFile> savedNoticeFiles;
}
