package com.chequer.tendermint.tendermint.abci;

import com.github.jtendermint.jabci.api.*;
import com.github.jtendermint.jabci.socket.TSocket;
import com.github.jtendermint.jabci.types.*;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.InitializingBean;
import org.springframework.stereotype.Service;

import javax.inject.Inject;

@Service
@Slf4j
public class ABCI implements InitializingBean, IBeginBlock, IEndBlock, IFlush, IInitChain, IQuery, ISetOption, ICheckTx, IDeliverTx, ICommit {

    private TSocket abciSocket;

    @Override
    public void afterPropertiesSet() {
        abciSocket = new TSocket();
        abciSocket.registerListener(this);
    }

    public void start() {
        new Thread(() -> abciSocket.start(26658)).start();

        log.info("Started ABCI Socket Interface");
        log.info("Now waiting on ABCI-Sockets before connecting to Websocket...");

        while (abciSocket.sizeOfConnectedABCISockets() < 3) {
            sleep(2000);
        }

        log.info("ABCI connections : {0}", abciSocket.sizeOfConnectedABCISockets());
    }

    private void sleep(long ms) {
        try {
            Thread.sleep(ms);
        } catch (InterruptedException e) {
        }
    }

    @Override
    public ResponseCheckTx requestCheckTx(RequestCheckTx req) {
        log.info("requestCheckTx : " + req.getTx().toStringUtf8());
        return ResponseCheckTx.newBuilder().setCode(CodeType.OK).build();
    }

    @Override
    public ResponseDeliverTx receivedDeliverTx(RequestDeliverTx req) {
        log.info("receivedDeliverTx : " + req.getTx().toStringUtf8());
        return ResponseDeliverTx.newBuilder().setCode(CodeType.OK).build();
    }

    @Override
    public ResponseCommit requestCommit(RequestCommit requestCommit) {
        log.info("requestCommit");
        return ResponseCommit.newBuilder().build();
    }

    @Override
    public ResponseBeginBlock requestBeginBlock(RequestBeginBlock req) {
        log.info("requestBeginBlock : " + req.getHash().toString());
        return ResponseBeginBlock.newBuilder().build();
    }

    @Override
    public ResponseEndBlock requestEndBlock(RequestEndBlock req) {
        log.info("requestEndBlock");
        return ResponseEndBlock.newBuilder().build();
    }

    @Override
    public ResponseFlush requestFlush(RequestFlush reqfl) {
        log.info("requestFlush");
        return ResponseFlush.newBuilder().build();
    }

    @Override
    public ResponseInitChain requestInitChain(RequestInitChain req) {
        log.info("requestInitChain");
        return ResponseInitChain.newBuilder().build();
    }

    @Override
    public ResponseQuery requestQuery(RequestQuery req) {
        log.info("requestQuery");
        return ResponseQuery.newBuilder().setCode(CodeType.OK).build();
    }

    @Override
    public ResponseSetOption requestSetOption(RequestSetOption req) {
        log.info("requestSetOption");
        return ResponseSetOption.newBuilder().build();
    }
}
