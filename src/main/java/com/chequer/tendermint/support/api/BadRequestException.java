package com.chequer.tendermint.support.api;

public class BadRequestException extends Exception {
    private int code;

    public BadRequestException(int code, String message) {
        super(message);
        this.code = code;
    }
}
