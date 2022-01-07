package com.noticeapp.services.notice.presentation;

import com.noticeapp.services.notice.application.NoticeModel;
import com.noticeapp.services.notice.application.NoticeService;
import com.noticeapp.services.notice.domain.Writer;
import com.noticeapp.web.InvalidRequestException;
import com.noticeapp.web.User;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.Errors;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import javax.validation.Valid;

@RestController
@RequestMapping("/api/notice")
@RequiredArgsConstructor
public class NoticeContoller {
    private final NoticeService noticeService;

    @PostMapping
    public ResponseEntity<NoticeModel> register(User user,
                                                @Valid RegisterNoticeRequest registerNoticeRequest,
                                                Errors errors){
        if(errors.hasErrors()){
            throw new InvalidRequestException(errors);
        }
        NoticeModel noticeModel = noticeService.register(createWriterFrom(user), registerNoticeRequest.toRegisterNotice());
        return ResponseEntity.status(HttpStatus.CREATED).body(noticeModel);
    }

    private Writer createWriterFrom(User user){
        return Writer.of(user.getIdentifier());
    }
}
