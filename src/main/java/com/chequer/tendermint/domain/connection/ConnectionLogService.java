package com.chequer.tendermint.domain.connection;

import com.chequer.tendermint.domain.base.DriveBaseService;
import com.chequer.tendermint.support.code.Types;
import com.chequer.tendermint.support.parameter.RequestParams;
import com.chequer.tendermint.tendermint.api.TendermintApi;
import com.chequer.tendermint.tendermint.api.model.TxResponse;
import com.querydsl.core.BooleanBuilder;
import lombok.extern.slf4j.Slf4j;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import javax.inject.Inject;
import java.util.List;

@Service
@Slf4j
public class ConnectionLogService extends DriveBaseService<ConnectionLog, String> {

    @Inject
    private ConnectionLogRepository connectionLogRepository;

    @Inject
    private TendermintApi tendermintApi;

    @Transactional
    public ConnectionLog save(ConnectionLog connectionLog) {
        connectionLog.setDataHash(connectionLog.dataValidationHash());
        connectionLogRepository.save(connectionLog);

        TxResponse response = tendermintApi.broadcastTxAsync(connectionLog);

        connectionLog.setCsYn((response != null && response.getError() == null) ? Types.Y : Types.N);
        connectionLogRepository.save(connectionLog);

        return connectionLog;
    }

    public void saveOrValidate(ConnectionLog connectionLog) {
        if (connectionLog.getId() == null) {
            log.error("Invalid data received [{0}]", connectionLog);
        }

        ConnectionLog exist = select().from(qConnectionLog).where(qConnectionLog.id.eq(connectionLog.getId())).fetchOne();
        connectionLog.setCsYn(Types.Y);

        if (exist == null) {
            if (connectionLog.getDataHash().equals(connectionLog.dataValidationHash())) {
                connectionLogRepository.save(connectionLog);
            } else {
                log.error("Validation Error [{0}]", connectionLog);
            }
        } else {
            if (exist.getDataHash().equals(connectionLog.dataValidationHash())) {
                connectionLogRepository.save(connectionLog);
            } else {
                log.error("Validation Error [{0}]", connectionLog);
            }
        }
    }

    public Page<ConnectionLog> findByRequestParams(RequestParams<ConnectionLog> requestParams) {
        BooleanBuilder booleanBuilder = new BooleanBuilder();
        Pageable pageable = requestParams.getPageable();

        String databaseType = requestParams.getString("databaseType", "");
        String dbHost = requestParams.getString("dbHost", "");
        Integer dbPort = requestParams.getInt("dbPort");
        String dbUser = requestParams.getString("dbUser", "");
        Long orgId = requestParams.getLong("orgId");
        Long userId = requestParams.getLong("userId");
        String ip = requestParams.getString("ip", "");
        String csYn = requestParams.getString("csYn", "");

        if (isNotEmpty(databaseType)) {
            booleanBuilder.and(qConnectionLog.databaseType.eq(databaseType));
        }

        if (isNotEmpty(dbHost)) {
            booleanBuilder.and(qConnectionLog.dbHost.eq(dbHost));
        }

        if (dbPort != null) {
            booleanBuilder.and(qConnectionLog.dbPort.eq(dbPort));
        }

        if (isNotEmpty(dbUser)) {
            booleanBuilder.and(qConnectionLog.dbUser.eq(dbUser));
        }

        if (orgId != null) {
            booleanBuilder.and(qConnectionLog.orgId.eq(orgId));
        }

        if (userId != null) {
            booleanBuilder.and(qConnectionLog.userId.eq(userId));
        }

        if (isNotEmpty(ip)) {
            booleanBuilder.and(qConnectionLog.ip.eq(ip));
        }

        if (isNotEmpty(csYn)) {
            booleanBuilder.and(qConnectionLog.csYn.eq(csYn));
        }

        long count = select().from(qConnectionLog)
                .where(booleanBuilder)
                .fetchCount();

        List<ConnectionLog> logs = select().from(qConnectionLog)
                .where(booleanBuilder)
                .orderBy(qConnectionLog.createdAt.desc())
                .limit(pageable.getPageSize())
                .offset(pageable.getOffset())
                .fetch();

        return new PageImpl<>(logs, pageable, count);
    }
}
