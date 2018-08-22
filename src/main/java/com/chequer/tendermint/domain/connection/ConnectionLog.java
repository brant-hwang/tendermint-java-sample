package com.chequer.tendermint.domain.connection;

import com.chequer.tendermint.domain.base.BaseJpaModel;
import com.chequer.tendermint.domain.base.TendermintEntity;
import com.chequer.tendermint.domain.base.TendermintTx;
import com.chequer.tendermint.support.utils.GsonUtils;
import com.chequer.tendermint.support.utils.HashUtils;
import lombok.Getter;
import lombok.Setter;
import org.hibernate.annotations.DynamicInsert;
import org.hibernate.annotations.DynamicUpdate;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Table;
import java.util.StringJoiner;
import java.util.UUID;

@Getter
@Setter
@DynamicInsert
@DynamicUpdate
@Entity
@Table(name = "CONNECTION_LOG")
public class ConnectionLog extends BaseJpaModel<String> implements TendermintEntity {
    @Id
    @Column(name = "ID")
    private String id = UUID.randomUUID().toString();

    @Column(name = "DATABASE_TYPE")
    private String databaseType;

    @Column(name = "CONNECTION_TYPE")
    private String connectionType;

    @Column(name = "DB_HOST")
    private String dbHost;

    @Column(name = "DB_PORT")
    private Integer dbPort;

    @Column(name = "DB_CONNECTION_MODE")
    private String dbConnectionMode;

    @Column(name = "DB_USER")
    private String dbUser;

    @Column(name = "ORG_ID")
    private Long orgId;

    @Column(name = "USER_ID")
    private Long userId;

    @Column(name = "IP")
    private String ip;

    @Column(name = "DATA_HASH")
    private String dataHash;

    @Column(name = "CS_YN")
    private String csYn;

    @Override
    public String toJson() {
        TendermintTx tendermintJson = new TendermintTx();
        tendermintJson.setType("connectionLog");
        tendermintJson.setJson(GsonUtils.toJson(this));
        return GsonUtils.toJson(tendermintJson);
    }

    @Override
    public String requestId() {
        return id;
    }

    @Override
    public String dataValidationHash() {
        StringJoiner stringJoiner = new StringJoiner("^");
        stringJoiner.add(id);
        stringJoiner.add(databaseType);
        stringJoiner.add(connectionType);
        stringJoiner.add(dbHost);
        stringJoiner.add(Integer.toString(dbPort));
        stringJoiner.add(dbConnectionMode);
        stringJoiner.add(dbUser);
        stringJoiner.add(Long.toString(orgId));
        stringJoiner.add(Long.toString(userId));
        stringJoiner.add(ip);
        return HashUtils.SHA256(stringJoiner.toString());
    }
}
