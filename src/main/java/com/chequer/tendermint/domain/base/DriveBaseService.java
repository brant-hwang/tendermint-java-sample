package com.chequer.tendermint.domain.base;

import com.chequer.tendermint.domain.connection.QConnectionLog;
import com.chequer.tendermint.support.parameter.RequestParams;
import com.querydsl.core.types.EntityPath;
import com.querydsl.jpa.impl.JPADeleteClause;
import com.querydsl.jpa.impl.JPAQuery;
import com.querydsl.jpa.impl.JPAUpdateClause;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang3.StringUtils;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import java.io.Serializable;
import java.math.BigDecimal;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.Collection;


@Slf4j
public class DriveBaseService<T, ID extends Serializable> {

    protected QConnectionLog qConnectionLog = QConnectionLog.connectionLog;

    @PersistenceContext
    protected EntityManager em;

    protected DriveJPAQueryDSLRepository<T, ID> repository;

    public DriveBaseService() {

    }

    public DriveBaseService(DriveJPAQueryDSLRepository<T, ID> repository) {
        this.repository = repository;
    }

    public int getInt(Integer integer) {
        if (integer == null) {
            return 0;
        }
        return integer;
    }

    public long getLong(Long _long) {
        if (_long == null) {
            return 0;
        }
        return _long;
    }

    public int getInt(BigDecimal bigDecimal) {
        if (bigDecimal == null)
            return 0;
        return bigDecimal.intValue();
    }

    public long getLong(BigDecimal bigDecimal) {
        if (bigDecimal == null)
            return 0;

        return bigDecimal.longValue();
    }

    protected String like(String field) {
        return "%" + field + "%";
    }

    public boolean isNotEmpty(String value) {
        return StringUtils.isNotEmpty(value);
    }

    public boolean isEmpty(String value) {
        return StringUtils.isEmpty(value);
    }

    public boolean isEmpty(Collection<?> list) {
        return list == null || list.size() == 0;
    }

    public boolean isNotEmpty(Collection<?> list) {
        return !isEmpty(list);
    }

    public boolean equals(Object o1, Object o2) {
        if (o1 == null) {
            return false;
        }

        if (o2 == null) {
            return false;
        }

        return o1.equals(o2);
    }

    public boolean notEquals(Object o1, Object o2) {
        return !equals(o1, o2);
    }

    protected JPAQuery<T> select() {
        return new JPAQuery<>(em);
    }

    protected JPAUpdateClause update(EntityPath<?> entityPath) {
        return new JPAUpdateClause(em, entityPath);
    }

    protected JPADeleteClause delete(EntityPath<?> entityPath) {
        return new JPADeleteClause(em, entityPath);
    }

    public RequestParams<T> buildRequestParams() {
        return new RequestParams<>();
    }

    public void sleep(long mills) {
        try {
            Thread.sleep(mills);
        } catch (Exception e) {
            // ignore
            Thread.currentThread().interrupt();
        }
    }

    public long minus(long value) {
        return -(Math.abs(value));
    }

    public int minus(int value) {
        return -(Math.abs(value));
    }

    public LocalDate getDate(String date) {
        return LocalDate.parse(date, DateTimeFormatter.ofPattern("yyyy-MM-dd"));
    }

    public String replace(String text, String search, String replacement) {
        return StringUtils.replace(text, search, replacement);
    }

    public String toFirstUpper(String s) {
        return Character.toUpperCase(s.charAt(0)) + s.substring(1);
    }
}
