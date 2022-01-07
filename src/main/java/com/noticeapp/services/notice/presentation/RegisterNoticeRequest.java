package com.noticeapp.services.notice.presentation;

import com.noticeapp.services.notice.application.RegisterNotice;
import lombok.*;
import org.springframework.format.annotation.DateTimeFormat;
import org.springframework.web.bind.annotation.RequestPart;
import org.springframework.web.multipart.MultipartFile;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;
import java.time.LocalDate;
import java.util.List;

@Getter
@Setter
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class RegisterNoticeRequest {
    @NotBlank(message = "공지사항 제목을 입력해주세요.")
    private String title;

    @NotBlank(message = "공지사항 내용을 입력해주세요.")
    private String content;

    @NotNull(message = "공지 시작일을 입력해주세요.")
    @DateTimeFormat(pattern = "yyyy-MM-dd")
    private LocalDate startDate;

    @NotNull(message = "공지 종료일을 입력해주세요.")
    @DateTimeFormat(pattern = "yyyy-MM-dd")
    private LocalDate endDate;

    private List<MultipartFile> file;

    public RegisterNotice toRegisterNotice() {
        return RegisterNotice.of(title, content, startDate, endDate, file);
    }
}
