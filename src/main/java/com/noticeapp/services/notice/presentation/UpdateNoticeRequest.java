package com.noticeapp.services.notice.presentation;

import com.noticeapp.services.notice.application.UpdateNotice;
import lombok.*;
import org.springframework.util.StringUtils;

import java.time.LocalDate;

import static org.springframework.util.StringUtils.hasText;

@Getter
@Builder
@AllArgsConstructor
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class UpdateNoticeRequest {
    private String title;
    private String content;
    private LocalDate startDate;
    private LocalDate endDate;

    public UpdateNotice toUpdateNotice() {
        if(startDate != null || endDate != null){
            if(startDate == null){
                throw new IllegalArgumentException("변경할 공지사항 시작일을 입력해주세요.");
            }
            if(endDate == null){
                throw new IllegalArgumentException("변경할 공지사항 종료일을 입력해주세요.");
            }
        }
        return UpdateNotice.of(title, content, startDate, endDate);
    }

    public boolean isEmpty() {
        return !hasText(title) &&
                !hasText(content) &&
                startDate == null &&
                endDate == null;
    }
}
