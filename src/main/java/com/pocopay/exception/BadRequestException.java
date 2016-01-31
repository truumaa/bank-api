package com.pocopay.exception;

public class BadRequestException extends RuntimeException {

    private ExceptionCode exceptionCode;

    public BadRequestException(ExceptionCode exceptionCode, String message) {
        super(message);
        this.exceptionCode = exceptionCode;
    }

    public ExceptionCode getExceptionCode() {
        return exceptionCode;
    }

    public enum ExceptionCode {
        INVALID_ACCOUNT_ID,
        INVALID_PAYMENT_ID
    }
}
