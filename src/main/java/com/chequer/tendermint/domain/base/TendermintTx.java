package com.chequer.tendermint.domain.base;

import lombok.Data;

@Data
public class TendermintTx {

    private String type;

    private Object json;

}
