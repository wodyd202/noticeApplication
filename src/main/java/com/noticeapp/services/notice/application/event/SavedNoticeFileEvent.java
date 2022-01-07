package com.noticeapp.services.notice.application.event;

import lombok.AllArgsConstructor;
import lombok.Getter;

import java.util.List;

@Getter
@AllArgsConstructor
public class SavedNoticeFileEvent {
    private List<SaveNoticeFile> savedNoticeFiles;
}
