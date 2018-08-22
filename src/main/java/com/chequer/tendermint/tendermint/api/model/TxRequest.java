package com.chequer.tendermint.tendermint.api.model;

import lombok.Data;

import java.util.Map;

@Data
public class TxRequest {

    private String jsonrpc = "2.0";

    private String id;

    private String method;

    private Map<String, String> params;
}
