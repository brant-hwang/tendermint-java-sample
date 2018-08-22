package com.chequer.tendermint.tendermint.api.model;

import lombok.Data;

@Data
public abstract class TendermintBase {

    private String jsonrpc;

    private String id;


}
