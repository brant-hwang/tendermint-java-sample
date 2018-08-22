package com.chequer.tendermint.tendermint.api.model;

import lombok.Data;

@Data
public class TxResponse extends TendermintBase {

    private TxResult result;

    private TendermintError error;
}
