package com.chequer.tendermint.support.api;


public class ExternalApiException extends RuntimeException {
    private int code;

    public ExternalApiException(int code, String message) {
        super(message);
        this.code = code;
    }

    public ExternalApiException(ApiStatus status, String message) {
        super(message);
        this.code = status.getCode();
    }

    public int getCode() {
        return this.code;
    }
}
