package com.noticeapp.services.notice.application;

import com.noticeapp.services.notice.application.event.*;
import com.noticeapp.services.notice.application.model.NoticeModel;
import com.noticeapp.services.notice.application.model.NoticeResponse;
import com.noticeapp.services.notice.application.model.RegisterNotice;
import com.noticeapp.services.notice.application.model.UpdateNotice;
import com.noticeapp.services.notice.domain.*;
import lombok.RequiredArgsConstructor;
import org.springframework.cache.annotation.CacheEvict;
import org.springframework.context.ApplicationEventPublisher;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.multipart.MultipartFile;

import java.util.List;
import java.util.stream.Collectors;

@Service
@Transactional
@RequiredArgsConstructor
public class NoticeService {
    private final NoticeRepository noticeRepository;
    private final NoticeFactory noticeFactory;

    private final ApplicationEventPublisher applicationEventPublisher;

    public NoticeModel register(Writer writer, RegisterNotice registerNotice) {
        Notice notice = noticeFactory.createBy(writer, registerNotice);
        if(registerNotice.hasFiles()){
            addFiles(writer, registerNotice.getFiles(), notice);
        }
        noticeRepository.save(notice);
        return NoticeModel.mapFrom(notice);
    }

    private void addFiles(Writer writer, List<MultipartFile> file, Notice notice) {
        List<NoticeFile> noticeFiles = file.stream()
                .map(multipartFile -> NoticeFile.of(notice, multipartFile, multipartFile.getOriginalFilename()))
                .collect(Collectors.toList());
        notice.addFiles(writer, noticeFiles);
        publishSavedNoticeFileEvent(notice, noticeFiles);
    }

    private void publishSavedNoticeFileEvent(Notice notice, List<NoticeFile> noticeFiles) {
        List<SaveNoticeFile> savedNoticeFiles = noticeFiles.stream()
                .map(file -> SaveNoticeFile.of(notice.getId(), file.getPath(), file.getFile()))
                .collect(Collectors.toList());
        applicationEventPublisher.publishEvent(new SavedNoticeFileEvent(savedNoticeFiles));
    }

    @CacheEvict(value = "notice", key = "#noticeId")
    public NoticeModel update(Writer writer, long noticeId, UpdateNotice updateNotice) {
        Notice notice = getNotice(noticeId);

        boolean isTitleChange = false;
        boolean isContentChange = false;
        boolean isNoticeDateChange = false;

        if(updateNotice.getTitle() != null){
            isTitleChange = notice.updateTitle(writer, updateNotice.getTitle());
        }

        if(updateNotice.getContent() != null){
            isContentChange = notice.updateContent(writer, updateNotice.getContent());
        }

        if(updateNotice.getStartDate() != null && updateNotice.getEndDate() != null){
            isNoticeDateChange = notice.updateNoticeDate(writer, NoticeDate.of(updateNotice.getStartDate(), updateNotice.getEndDate()));
        }

        if(!isTitleChange && !isContentChange && !isNoticeDateChange){
            throw new NoEditNoticeException();
        }

        return NoticeModel.mapFrom(notice);
    }

    public void addFiles(Writer writer, long noticeId, List<MultipartFile> file) {
        Notice notice = getNotice(noticeId);

        addFiles(writer, file, notice);
    }

    public void removeFile(Writer updater, long noticeId, long fileId) {
        Notice notice = getNotice(noticeId);

        notice.removeFile(updater, fileId);
        applicationEventPublisher.publishEvent(new RemovedNoticeFileEvent(noticeId, fileId));
    }

    @CacheEvict(value = "notice", key = "#noticeId")
    public void remove(Writer remover, long noticeId) {
        Notice notice = getNotice(noticeId);

        notice.remove(remover);
        applicationEventPublisher.publishEvent(new RemovedNoticeEvent(noticeId));
    }

    private Notice getNotice(long noticeId) {
        return noticeRepository.findById(noticeId).orElseThrow(NoticeNotFoundException::new);
    }

    // query

    private final NoticeSearchRepository noticeSearchRepository;

    // external
    private final NoticeHitRepository noticeHitRepository;

    public NoticeResponse getNoticeResponse(long noticeId) {
        NoticeModel noticeModel = noticeSearchRepository.findById(noticeId).orElseThrow(NoticeNotFoundException::new);
        long totalHit = noticeHitRepository.incrementHit(noticeId);

        applicationEventPublisher.publishEvent(new ReadNoticeEvent(noticeId));
        return NoticeResponse.of(noticeModel, totalHit);
    }
}
