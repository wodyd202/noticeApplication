package com.noticeapp.services.notice.domain;

import lombok.AccessLevel;
import lombok.Getter;
import lombok.NoArgsConstructor;

import javax.persistence.Embeddable;
import java.time.LocalDate;
import java.util.Objects;

@Getter
@Embeddable
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class NoticeDate {
    private LocalDate startDate;
    private LocalDate endDate;

    private NoticeDate(LocalDate startDate, LocalDate endDate) {
        if(startDate.isAfter(endDate)){
            throw new IllegalArgumentException("공지 시작일은 종료일보다 같거나 작아야합니다.");
        }
        this.startDate = startDate;
        this.endDate = endDate;
    }

    public static NoticeDate of(LocalDate startDate, LocalDate endDate){
        return new NoticeDate(startDate, endDate);
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        NoticeDate that = (NoticeDate) o;
        return Objects.equals(startDate, that.startDate) && Objects.equals(endDate, that.endDate);
    }

    @Override
    public int hashCode() {
        return Objects.hash(startDate, endDate);
    }
}
