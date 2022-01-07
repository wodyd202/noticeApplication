package com.noticeapp.services.notice.presentation;

import com.noticeapp.services.notice.application.NoEditNoticeException;
import com.noticeapp.services.notice.application.NoticeNotFoundException;
import com.noticeapp.services.notice.domain.NoUpdateNoticePermissionException;
import com.noticeapp.services.notice.domain.NoticeFileNotFoundException;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestControllerAdvice;

@RestControllerAdvice
public class NoticeExceptionHandler {
    @ResponseStatus(HttpStatus.NO_CONTENT)
    @ExceptionHandler(NoEditNoticeException.class)
    public void error(NoEditNoticeException e){}

    @ResponseStatus(HttpStatus.NOT_FOUND)
    @ExceptionHandler({NoticeNotFoundException.class, NoticeFileNotFoundException.class})
    public void error(RuntimeException e){}

    @ExceptionHandler(NoUpdateNoticePermissionException.class)
    public ResponseEntity<String> error(NoUpdateNoticePermissionException e){
        return ResponseEntity.badRequest().body("해당 공지사항에 대한 수정 권한이 존재하지 않습니다.");
    }
}

