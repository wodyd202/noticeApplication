package com.noticeapp.web;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;

import java.util.List;

@RestControllerAdvice
public class InvalidRequestExcepionHandler {
    @ExceptionHandler(InvalidRequestException.class)
    public ResponseEntity<List<String>> error(InvalidRequestException e){
        return ResponseEntity.badRequest().body(e.getAllErrorMessages());
    }
}
