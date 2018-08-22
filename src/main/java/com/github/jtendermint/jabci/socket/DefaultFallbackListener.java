/*
 * The MIT License (MIT)
 * 
 * Copyright (c) 2016 - 2018
 * 
 * Permission is hereby granted, free of charge, to any person obtaining a copy
 * of this software and associated documentation files (the "Software"), to deal
 * in the Software without restriction, including without limitation the rights
 * to use, copy, modify, merge, publish, distribute, sublicense, and/or sell
 * copies of the Software, and to permit persons to whom the Software is
 * furnished to do so, subject to the following conditions:
 * 
 * The above copyright notice and this permission notice shall be included in all
 * copies or substantial portions of the Software.
 * 
 * THE SOFTWARE IS PROVIDED "AS IS", WITHOUT WARRANTY OF ANY KIND, EXPRESS OR
 * IMPLIED, INCLUDING BUT NOT LIMITED TO THE WARRANTIES OF MERCHANTABILITY,
 * FITNESS FOR A PARTICULAR PURPOSE AND NONINFRINGEMENT. IN NO EVENT SHALL THE
 * AUTHORS OR COPYRIGHT HOLDERS BE LIABLE FOR ANY CLAIM, DAMAGES OR OTHER
 * LIABILITY, WHETHER IN AN ACTION OF CONTRACT, TORT OR OTHERWISE, ARISING FROM,
 * OUT OF OR IN CONNECTION WITH THE SOFTWARE OR THE USE OR OTHER DEALINGS IN THE
 * SOFTWARE.
 */
package com.github.jtendermint.jabci.socket;

import com.github.jtendermint.jabci.api.ABCIAPI;
import com.github.jtendermint.jabci.api.CodeType;
import com.github.jtendermint.jabci.types.*;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/**
 * The DefaultFallbackListener answers every incoming ABCI-request with a CodeType.OK
 *
 * @author wolfposd
 */
public final class DefaultFallbackListener implements ABCIAPI {

    private final static Logger LOG = LoggerFactory.getLogger(DefaultFallbackListener.class);

    public final static DefaultFallbackListener instance = new DefaultFallbackListener();

    private DefaultFallbackListener() {
    }

    @Override
    public ResponseDeliverTx receivedDeliverTx(RequestDeliverTx req) {
        LOG.debug("ResponseDeliverTx DefaultFallbackListener");
        return ResponseDeliverTx.newBuilder().setCode(CodeType.OK).build();
    }

    @Override
    public ResponseFlush requestFlush(RequestFlush reqfl) {
        LOG.debug("ResponseFlush DefaultFallbackListener");
        return ResponseFlush.newBuilder().build();
    }

    @Override
    public ResponseCommit requestCommit(RequestCommit requestCommit) {
        LOG.debug("ResponseCommit DefaultFallbackListener");
        return ResponseCommit.newBuilder().build();
    }

    @Override
    public ResponseBeginBlock requestBeginBlock(RequestBeginBlock req) {
        LOG.debug("ResponseBeginBlock DefaultFallbackListener");
        return ResponseBeginBlock.newBuilder().build();
    }

    @Override
    public ResponseCheckTx requestCheckTx(RequestCheckTx req) {
        LOG.debug("ResponseCheckTx DefaultFallbackListener");
        return ResponseCheckTx.newBuilder().setCode(CodeType.OK).build();
    }

    @Override
    public ResponseEndBlock requestEndBlock(RequestEndBlock req) {
        LOG.debug("ResponseEndBlock DefaultFallbackListener");
        return ResponseEndBlock.newBuilder().build();
    }

    @Override
    public ResponseInfo requestInfo(RequestInfo req) {
        LOG.debug("ResponseInfo DefaultFallbackListener");
        return ResponseInfo.newBuilder().setData("NO_INFO").build();
    }

    @Override
    public ResponseInitChain requestInitChain(RequestInitChain req) {
        LOG.debug("ResponseInitChain DefaultFallbackListener");
        return ResponseInitChain.newBuilder().build();
    }

    @Override
    public ResponseQuery requestQuery(RequestQuery req) {
        LOG.debug("ResponseQuery DefaultFallbackListener");
        return ResponseQuery.newBuilder().setCode(CodeType.OK).build();
    }

    @Override
    public ResponseSetOption requestSetOption(RequestSetOption req) {
        LOG.debug("ResponseSetOption DefaultFallbackListener");
        return ResponseSetOption.newBuilder().build();
    }

    @Override
    public ResponseEcho requestEcho(RequestEcho req) {
        LOG.debug("ResponseEcho DefaultFallbackListener");
        return ResponseEcho.newBuilder().setMessage("NOECHO").build();
    }

}
