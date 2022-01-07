package com.noticeapp.services.notice.application;

import com.noticeapp.services.notice.domain.Notice;
import com.noticeapp.services.notice.domain.NoticeFile;
import com.noticeapp.services.notice.domain.NoticeRepository;
import com.noticeapp.services.notice.domain.Writer;
import lombok.RequiredArgsConstructor;
import org.springframework.context.ApplicationEventPublisher;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
@Transactional
public class NoticeService {
    private final NoticeRepository noticeRepository;
    private final NoticeFactory noticeFactory;

    private final ApplicationEventPublisher applicationEventPublisher;

    public NoticeModel register(Writer writer, RegisterNotice registerNotice) {
        Notice notice = noticeFactory.createBy(writer, registerNotice);
        noticeRepository.save(notice);
        if(registerNotice.hasFiles()){
            List<NoticeFile> noticeFiles = registerNotice.getFiles().stream()
                    .map(file->NoticeFile.of(notice, file, file.getOriginalFilename()))
                    .collect(Collectors.toList());
            notice.addFiles(noticeFiles);

            publishSavedNoticeFileEvent(notice, noticeFiles);
        }
        return NoticeModel.mapFrom(notice);
    }

    private void publishSavedNoticeFileEvent(Notice notice, List<NoticeFile> noticeFiles) {
        List<SaveNoticeFile> savedNoticeFiles = noticeFiles.stream()
                .map(file -> SaveNoticeFile.of(notice.getId(), file.getPath(), file.getFile()))
                .collect(Collectors.toList());
        applicationEventPublisher.publishEvent(new SavedNoticeFileEvent(savedNoticeFiles));
    }
}
