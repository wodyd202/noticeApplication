package com.noticeapp.web.config;

import com.noticeapp.web.User;
import org.springframework.core.MethodParameter;
import org.springframework.web.bind.support.WebDataBinderFactory;
import org.springframework.web.context.request.NativeWebRequest;
import org.springframework.web.method.support.HandlerMethodArgumentResolver;
import org.springframework.web.method.support.ModelAndViewContainer;

public class UserArgumentResolver implements HandlerMethodArgumentResolver {
    private final static String AUTHENTICATION = "Authentication";

    @Override
    public boolean supportsParameter(MethodParameter methodParameter) {
        return User.class.equals(methodParameter.getParameterType());
    }

    @Override
    public Object resolveArgument(MethodParameter methodParameter, ModelAndViewContainer modelAndViewContainer, NativeWebRequest nativeWebRequest, WebDataBinderFactory webDataBinderFactory) throws Exception {
        String authentication = nativeWebRequest.getHeader(AUTHENTICATION);
        return User.fromAuthentication(authentication);
    }
}
