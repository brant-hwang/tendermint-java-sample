package com.chequer.tendermint.tendermint.api.model;

import lombok.Data;

@Data
public class TendermintError {

    private int code;

    private String message;

    private String data;
}
