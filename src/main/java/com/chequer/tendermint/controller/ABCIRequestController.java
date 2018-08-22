package com.chequer.tendermint.controller;

import com.chequer.tendermint.domain.connection.ConnectionLog;
import com.chequer.tendermint.domain.connection.ConnectionLogService;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

import javax.inject.Inject;

@RestController
public class ABCIRequestController extends BaseController {

    @Inject
    private ConnectionLogService connectionLogService;

    @PostMapping(value = "/api/v1/abci/connectionLog", produces = APPLICATION_JSON)
    public ConnectionLog connectionLog(@RequestBody ConnectionLog connectionLog) {
        return connectionLogService.save(connectionLog);
    }

}
