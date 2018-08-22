package com.chequer.tendermint.domain.connection;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface ConnectionLogRepository extends JpaRepository<ConnectionLog, String> {
}
