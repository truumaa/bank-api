package com.pocopay.exception;

public class ForbiddenException extends RuntimeException {

    private ExceptionCode exceptionCode;

    public ForbiddenException(ExceptionCode exceptionCode, String message) {
        super(message);
        this.exceptionCode = exceptionCode;
    }

    public ExceptionCode getExceptionCode() {
        return exceptionCode;
    }

    public enum ExceptionCode {
        ACCOUNT_NAME_EXISTS, NEGATIVE_AMOUNT, NOT_ENOUGH_FUNDS
    }
}
