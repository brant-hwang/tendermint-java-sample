package com.chequer.tendermint.tendermint.api.model;

import lombok.Data;

@Data
public class TxResult {

    private String data;

    private String log;

    private String hash;

    private int code;

}
