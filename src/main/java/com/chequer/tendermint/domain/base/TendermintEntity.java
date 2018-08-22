package com.chequer.tendermint.domain.base;

public interface TendermintEntity {

    String toJson();

    String requestId();

    String dataValidationHash();
}
