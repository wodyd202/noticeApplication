package com.noticeapp.web;

import lombok.AllArgsConstructor;
import org.springframework.validation.Errors;
import org.springframework.validation.ObjectError;

import java.util.List;
import java.util.stream.Collectors;

@AllArgsConstructor
public class InvalidRequestException extends RuntimeException {
    private Errors errors;

    public List<String> getAllErrorMessages(){
        return errors.getAllErrors().stream().map(ObjectError::getDefaultMessage).collect(Collectors.toList());
    }
}
