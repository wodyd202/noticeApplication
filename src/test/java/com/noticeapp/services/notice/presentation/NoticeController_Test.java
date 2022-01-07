package com.noticeapp.services.notice.presentation;

import com.fasterxml.jackson.databind.ObjectMapper;
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

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.multipart;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
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

}
