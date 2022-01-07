package com.noticeapp.services.notice.presentation;

import com.noticeapp.services.notice.application.NoticeModel;
import com.noticeapp.services.notice.application.NoticeService;
import com.noticeapp.services.notice.domain.Writer;
import com.noticeapp.web.InvalidRequestException;
import com.noticeapp.web.User;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.Errors;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import javax.validation.Valid;
import java.util.List;

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

    @PatchMapping("/{noticeId}")
    public ResponseEntity<NoticeModel> update(User user,
                                              @PathVariable long noticeId,
                                              @RequestBody UpdateNoticeRequest updateNoticeRequest){
        if (updateNoticeRequest.isEmpty()) {
            return ResponseEntity.status(HttpStatus.NO_CONTENT).body(null);
        }
        NoticeModel noticeModel = noticeService.update(createWriterFrom(user), noticeId, updateNoticeRequest.toUpdateNotice());
        return ResponseEntity.ok(noticeModel);
    }

    @PostMapping("/{noticeId}/file")
    @ResponseStatus(HttpStatus.NO_CONTENT)
    public void addNoticeFile(User user,
                              @PathVariable long noticeId,
                              @RequestPart List<MultipartFile> file){
        noticeService.addFiles(createWriterFrom(user), noticeId, file);
    }

    @DeleteMapping("/{noticeId}/file/{fileId}")
    @ResponseStatus(HttpStatus.NO_CONTENT)
    public void removeFile(User user,
                           @PathVariable long noticeId,
                           @PathVariable long fileId){
        noticeService.removeFile(createWriterFrom(user), noticeId, fileId);
    }

    @DeleteMapping("/{noticeId}")
    @ResponseStatus(HttpStatus.NO_CONTENT)
    public void removeNotice(User user,
                             @PathVariable long noticeId){
        noticeService.remove(createWriterFrom(user), noticeId);
    }

    private Writer createWriterFrom(User user){
        return Writer.of(user.getIdentifier());
    }
}
