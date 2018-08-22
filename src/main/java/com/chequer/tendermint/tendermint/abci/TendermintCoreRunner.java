package com.chequer.tendermint.tendermint.abci;

import org.springframework.beans.factory.DisposableBean;
import org.springframework.beans.factory.InitializingBean;
import org.springframework.stereotype.Component;

import javax.inject.Inject;

@Component
public class TendermintCoreRunner implements InitializingBean, DisposableBean {

    private ProcessBuilder builder;
    private Process process;

    @Inject
    private ABCI abci;

    public TendermintCoreRunner() {
    }

    @Override
    public void afterPropertiesSet() throws Exception {
        String tendermintEmbedded = System.getProperty("tendermint.embedded", "false");
        if (Boolean.parseBoolean(tendermintEmbedded)) {
            process = Runtime.getRuntime().exec("/usr/local/bin/tendermint node --consensus.create_empty_blocks=false");
        }
        abci.start();
    }

    @Override
    public void destroy() throws Exception {
        process.destroy();
    }
}
