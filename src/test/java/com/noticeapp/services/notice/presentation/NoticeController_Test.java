package com.noticeapp.services.notice.presentation;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.noticeapp.services.notice.domain.*;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.MediaType;
import org.springframework.mock.web.MockMultipartFile;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.ResultActions;
import org.springframework.test.web.servlet.request.MockMultipartHttpServletRequestBuilder;
import org.springframework.util.LinkedMultiValueMap;
import org.springframework.util.MultiValueMap;
import org.springframework.web.multipart.MultipartFile;

import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@SpringBootTest
@AutoConfigureMockMvc
public class NoticeController_Test {
    @Autowired MockMvc mockMvc;
    @Autowired ObjectMapper objectMapper;

    @Test
    @DisplayName("공지사항 등록시 제목을 입력하지 않으면 400 에러")
    void emptyTitle_400() throws Exception {
        // when
        String writer = "user1";
        RegisterNoticeRequest registerNoticeRequest = RegisterNoticeRequest.builder()
                .title("")
                .content("내용")
                .startDate(LocalDate.now())
                .endDate(LocalDate.now())
                .build();
        assertRegisterNotice(writer, registerNoticeRequest)

        // then
        .andExpect(status().isBadRequest());
    }

    @Test
    @DisplayName("공지사항 등록시 내용을 입력하지 않으면 400 에러")
    void emptyContent_400() throws Exception {
        // when
        String writer = "user1";
        RegisterNoticeRequest registerNoticeRequest = RegisterNoticeRequest.builder()
                .title("제목")
                .content("")
                .startDate(LocalDate.now())
                .endDate(LocalDate.now())
                .build();
        assertRegisterNotice(writer, registerNoticeRequest)

        // then
        .andExpect(status().isBadRequest());
    }

    @Test
    @DisplayName("공지사항 등록시 공지 시작일시를 입력하지 않으면 400 에러")
    void emptyStartDate() throws Exception {
        // when
        String writer = "user1";
        RegisterNoticeRequest registerNoticeRequest = RegisterNoticeRequest.builder()
                .title("제목")
                .content("내용")
                .startDate(null)
                .endDate(LocalDate.now())
                .build();
        assertRegisterNotice(writer, registerNoticeRequest)

        // then
        .andExpect(status().isBadRequest());
    }

    @Test
    @DisplayName("공지사항 등록시 공지 종료일을 입력하지 않으면 400 에러")
    void emptyEndDate() throws Exception {
        // when
        String writer = "user1";
        RegisterNoticeRequest registerNoticeRequest = RegisterNoticeRequest.builder()
                .title("제목")
                .content("내용")
                .startDate(LocalDate.now())
                .endDate(null)
                .build();
        assertRegisterNotice(writer, registerNoticeRequest)

        // then
        .andExpect(status().isBadRequest());
    }

    @Test
    @DisplayName("공지사항 등록")
    void registerNotice() throws Exception {
        // when
        String writer = "user1";
        List<MultipartFile> files = Arrays.asList(
                new MockMultipartFile("file","a","a",new byte[1]),
                new MockMultipartFile("file","a","a",new byte[1]));
        RegisterNoticeRequest registerNoticeRequest = RegisterNoticeRequest.builder()
                .title("제목")
                .content("내용")
                .startDate(LocalDate.now())
                .endDate(LocalDate.now().plusDays(1))
                .file(files)
                .build();
        assertRegisterNotice(writer, registerNoticeRequest)

        // then
        .andExpect(status().isCreated());
    }

    private final String AUTHENTICATION = "Authentication";
    private final String REGISTER_NOTICE_URI = "/api/notice";
    private ResultActions assertRegisterNotice(String writer, RegisterNoticeRequest registerNoticeRequest) throws Exception{
        MultiValueMap<String, String> params = new LinkedMultiValueMap<>();
        params.add("title", registerNoticeRequest.getTitle());
        params.add("content", registerNoticeRequest.getContent());
        if(registerNoticeRequest.getStartDate() != null){
            params.add("startDate", registerNoticeRequest.getStartDate().format(DateTimeFormatter.ofPattern("yyyy-MM-dd")));
        }
        if(registerNoticeRequest.getEndDate() != null){
            params.add("endDate", registerNoticeRequest.getEndDate().format(DateTimeFormatter.ofPattern("yyyy-MM-dd")));
        }
        MockMultipartHttpServletRequestBuilder multipart = multipart(REGISTER_NOTICE_URI);
        List<MultipartFile> file = registerNoticeRequest.getFile();
        if(file != null){
            for (MultipartFile multipartFile : file) {
                multipart.file((MockMultipartFile) multipartFile);
            }
        }
        return mockMvc.perform(multipart
                        .header(AUTHENTICATION, writer)
                        .params(params)
                        .contentType(MediaType.MULTIPART_FORM_DATA));
    }

    @Autowired NoticeRepository noticeRepository;

    @Test
    @DisplayName("공지사항 제목 수정")
    void updateNoticeTtile() throws Exception {
        // given
        NoticeDate noticeDate = NoticeDate.of(LocalDate.now().minusDays(2), LocalDate.now());
        Notice notice = saveNotce(Notice.of("타이틀", "내용", Writer.of("user"), noticeDate));

        // when
        String updater = "user";
        long noticeId = notice.getId();
        UpdateNoticeRequest updateNoticeRequest = UpdateNoticeRequest.builder()
                .title("제목 수정")
                .build();
        assertUpdateNotice(updater, noticeId ,updateNoticeRequest)

        // then
        .andExpect(status().isOk());
    }

    @Test
    @DisplayName("공지사항 내용 수정")
    void updateNoticeContent() throws Exception {
        // given
        NoticeDate noticeDate = NoticeDate.of(LocalDate.now().minusDays(2), LocalDate.now());
        Notice notice = saveNotce(Notice.of("타이틀", "내용", Writer.of("user"), noticeDate));

        // when
        String updater = "user";
        long noticeId = notice.getId();
        UpdateNoticeRequest updateNoticeRequest = UpdateNoticeRequest.builder()
                .content("내용 수정")
                .build();
        assertUpdateNotice(updater, noticeId ,updateNoticeRequest)

        // then
        .andExpect(status().isOk());
    }

    @Test
    @DisplayName("공지 날짜 수정")
    void updateNoticeEndDate() throws Exception {
        // given
        NoticeDate noticeDate = NoticeDate.of(LocalDate.now(), LocalDate.now());
        Notice notice = saveNotce(Notice.of("타이틀", "내용", Writer.of("user"), noticeDate));

        // when
        String updater = "user";
        long noticeId = notice.getId();
        UpdateNoticeRequest updateNoticeRequest = UpdateNoticeRequest.builder()
                .startDate(LocalDate.now().plusDays(2))
                .endDate(LocalDate.now().plusDays(2))
                .build();
        assertUpdateNotice(updater, noticeId ,updateNoticeRequest)

        // then
        .andExpect(status().isOk());
    }

    @Test
    @DisplayName("아무런 수정사항이 존재하지 않을 경우 204")
    void noUpdateNotice() throws Exception {
        // given
        NoticeDate noticeDate = NoticeDate.of(LocalDate.now(), LocalDate.now());
        Notice notice = saveNotce(Notice.of("제목", "내용", Writer.of("user"), noticeDate));

        // when
        String updater = "user";
        long noticeId = notice.getId();
        UpdateNoticeRequest updateNoticeRequest = UpdateNoticeRequest.builder()
                .title("제목")
                .content("내용")
                .endDate(LocalDate.now())
                .startDate(LocalDate.now())
                .build();
        assertUpdateNotice(updater, noticeId ,updateNoticeRequest)

        // then
        .andExpect(status().isNoContent());
    }

    @Test
    @DisplayName("수정 사항이 비어있는 경우 204")
    void emptyUpdateNoticeRequest() throws Exception {
        // when
        String updater = "user";
        long noticeId = -1;
        UpdateNoticeRequest updateNoticeRequest = UpdateNoticeRequest.builder()
                .content(null)
                .endDate(null)
                .startDate(null)
                .title(null)
                .build();
        assertUpdateNotice(updater, noticeId ,updateNoticeRequest)

        // then
        .andExpect(status().isNoContent());
    }

    private final String UPDATE_NOTICE_URI = "/api/notice/{noticeId}";
    private ResultActions assertUpdateNotice(String updater, long noticeId, UpdateNoticeRequest updateNoticeRequest) throws Exception{
        return mockMvc.perform(patch(UPDATE_NOTICE_URI, noticeId)
                .header(AUTHENTICATION, updater)
                .contentType(MediaType.APPLICATION_JSON)
                .content(objectMapper.writeValueAsString(updateNoticeRequest)));
    }

    @Test
    @DisplayName("공지사항 첨부파일 추가")
    void addNoticeFile() throws Exception {
        // given
        NoticeDate noticeDate = NoticeDate.of(LocalDate.now(), LocalDate.now());
        Notice notice = saveNotce(Notice.of("제목", "내용", Writer.of("user"), noticeDate));

        // when
        String updater = "user";
        long noticeId = notice.getId();
        MockMultipartFile multipartFile = new MockMultipartFile("file","a","a", new byte[1]);
        assertAddNoticeFile(updater, noticeId, multipartFile)

        // then
        .andExpect(status().isNoContent());
    }

    @Autowired NoticeFileRepository noticeFileRepository;

    private final String ADD_NOTICE_FILE_URI = "/api/notice/{noticeId}/file";
    private ResultActions assertAddNoticeFile(String updater, long noticeId, MockMultipartFile multipartFile) throws Exception{
        return mockMvc.perform(multipart(ADD_NOTICE_FILE_URI, noticeId)
                .file(multipartFile)
                .header(AUTHENTICATION, updater));
    }

    @Test
    @DisplayName("공지사항 첨부파일 삭제")
    void removeNoticeFile() throws Exception {
        // given
        NoticeDate noticeDate = NoticeDate.of(LocalDate.now(), LocalDate.now());
        Notice notice = saveNotce(Notice.of("제목", "내용", Writer.of("user"), noticeDate));
        NoticeFile noticeFile = NoticeFile.of(notice, null, "path");
        noticeFileRepository.save(noticeFile);

        // when
        String updater = "user";
        long noticeId = notice.getId();
        long fileId = noticeFile.getId();
        assertAddNoticeFile(updater, noticeId, fileId)

        // then
        .andExpect(status().isNoContent());
    }

    private final String REMOVE_NOTICE_FILE_URI = "/api/notice/{noticeId}/file/{fileId}";
    private ResultActions assertAddNoticeFile(String updater, long noticeId, long fileId) throws Exception{
        return mockMvc.perform(delete(REMOVE_NOTICE_FILE_URI, noticeId, fileId)
                .header(AUTHENTICATION, updater));
    }

    @Test
    @DisplayName("공지사항 삭제")
    void removeNotice() throws Exception {
        // given
        NoticeDate noticeDate = NoticeDate.of(LocalDate.now(), LocalDate.now());
        Notice notice = saveNotce(Notice.of("제목", "내용", Writer.of("user"), noticeDate));

        // when
        String remover = "user";
        long noticeId = notice.getId();
        assertRemoveNotice(remover, noticeId)

        // then
        .andExpect(status().isNoContent());
    }

    private final String REMOVE_NOTICE_URI = "/api/notice/{noticeId}";
    private ResultActions assertRemoveNotice(String remover, long noticeId) throws Exception{
        return mockMvc.perform(delete(REMOVE_NOTICE_URI, noticeId)
                .header(AUTHENTICATION, remover));
    }

    @Test
    @DisplayName("공지사항 리스트 조회")
    void getNotices() throws Exception {
        // given
        NoticeDate noticeDate = NoticeDate.of(LocalDate.now(), LocalDate.now());
        Notice notice = saveNotce(Notice.of("제목", "내용", Writer.of("user"), noticeDate));

        // when
        long noticeId = notice.getId();
        mockMvc.perform(get("/api/notice/{noticeId}", noticeId))

        // then
        .andExpect(status().isOk());
    }

    private Notice saveNotce(Notice notice){
        noticeRepository.save(notice);
        return notice;
    }
}
