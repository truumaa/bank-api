package com.pocopay;

import javax.servlet.http.HttpServletRequest;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.ResponseStatus;

import com.pocopay.exception.BadRequestException;
import com.pocopay.exception.ForbiddenException;

@ControllerAdvice
public class RestErrorHandler {


    @ExceptionHandler(ForbiddenException.class)
    @ResponseStatus(HttpStatus.FORBIDDEN)
    @ResponseBody
    public RestErrorWrapper handleForbiddenException(ForbiddenException e, HttpServletRequest request) {
        //logCondition(e, request);
        return new RestErrorWrapper(e.getMessage());
    }

    @ExceptionHandler(BadRequestException.class)
    @ResponseStatus(HttpStatus.BAD_REQUEST)
    @ResponseBody
    public RestErrorWrapper handleForbiddenException(BadRequestException e, HttpServletRequest request) {
        //logCondition(e, request);
        return new RestErrorWrapper(e.getMessage());
    }
}
