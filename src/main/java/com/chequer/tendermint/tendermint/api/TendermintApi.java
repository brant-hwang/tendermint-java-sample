package com.chequer.tendermint.tendermint.api;

import com.chequer.tendermint.domain.base.TendermintEntity;
import com.chequer.tendermint.support.utils.GsonUtils;
import com.chequer.tendermint.support.utils.HashUtils;
import com.chequer.tendermint.tendermint.api.model.TxRequest;
import com.chequer.tendermint.tendermint.api.model.TxResponse;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.*;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;

import java.util.Arrays;
import java.util.HashMap;
import java.util.Map;

@Service
@Slf4j
public class TendermintApi {

    private RestTemplate restTemplate;

    private static final String BASE_URL = "http://localhost:26657";

    private static final String BROADCAST_TX_ASYNC = "broadcast_tx_async";
    private static final String BROADCAST_TX_SYNC = "broadcast_tx_sync";

    public TendermintApi() {
        restTemplate = new RestTemplate();
    }

    public TxRequest request(String id, String method, Map<String, String> params) {
        TxRequest txRequest = new TxRequest();
        txRequest.setId(id);
        txRequest.setMethod(method);
        txRequest.setParams(params);

        return txRequest;
    }

    public HttpEntity<String> entity(TxRequest txRequest) {
        HttpHeaders headers = new HttpHeaders();
        headers.setAccept(Arrays.asList(MediaType.TEXT_PLAIN));
        return new HttpEntity<>(GsonUtils.toJson(txRequest), headers);
    }

    public TxResponse broadcastTx(String method, TendermintEntity tendermintEntity) {
        String encodedJson = HashUtils.base64Encode(tendermintEntity.toJson());

        Map<String, String> params = new HashMap<>();
        params.put("tx", encodedJson);

        HttpEntity<String> entity = entity(request(tendermintEntity.requestId(), method, params));
        ResponseEntity<TxResponse> response = restTemplate.exchange(BASE_URL, HttpMethod.POST, entity, TxResponse.class);

        log.info("braodcastTxAsyncResponse [" + response.getBody() + "]");

        if (response.getStatusCode() == HttpStatus.OK) {
            return response.getBody();
        }

        return null;
    }

    public TxResponse broadcastTxAsync(TendermintEntity tendermintEntity) {
        return broadcastTx(BROADCAST_TX_ASYNC, tendermintEntity);
    }

    public TxResponse broadcastTxSync(TendermintEntity tendermintEntity) {
        return broadcastTx(BROADCAST_TX_SYNC, tendermintEntity);
    }
}
