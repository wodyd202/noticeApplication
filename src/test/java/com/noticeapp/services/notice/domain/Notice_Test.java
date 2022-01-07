package com.noticeapp.services.notice.domain;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import java.time.LocalDate;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;

public class Notice_Test {
    @Test
    @DisplayName("공지 시작일은 종료일보다 작거나 같아야함")
    void invalidNoticeDate(){
        // when
        assertThrows(IllegalArgumentException.class,()->{
            NoticeDate.of(LocalDate.now(), LocalDate.now().minusDays(1));
        });
    }

    @Test
    @DisplayName("공지 날짜 정상 입력")
    void validNoticeDate(){
        // when
        NoticeDate noticeDate = NoticeDate.of(LocalDate.now(), LocalDate.now().plusDays(1));

        // then
        assertEquals(noticeDate.getStartDate(), LocalDate.now());
        assertEquals(noticeDate.getEndDate(), LocalDate.now().plusDays(1));
    }
}
