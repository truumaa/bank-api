package com.pocopay;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.ResponseStatus;

import com.pocopay.exception.BadRequestException;
import com.pocopay.exception.ForbiddenException;

@ControllerAdvice
public class ErrorHandler {

    private static final Logger logger = LoggerFactory.getLogger(ErrorHandler.class);

    @ExceptionHandler(ForbiddenException.class)
    @ResponseStatus(HttpStatus.FORBIDDEN)
    @ResponseBody
    public String handleForbiddenException(ForbiddenException e) {
        logger.info("ForbiddenException: {}", e.getMessage());
        return e.getExceptionCode().toString();
    }

    @ExceptionHandler(BadRequestException.class)
    @ResponseStatus(HttpStatus.BAD_REQUEST)
    @ResponseBody
    public String handleForbiddenException(BadRequestException e) {
        logger.info("BadRequestException: {}", e.getMessage());
        return e.getExceptionCode().toString();
    }

}
