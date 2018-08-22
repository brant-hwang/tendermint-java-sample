package com.chequer.tendermint.support.parameter;


import com.chequer.tendermint.support.api.ApiException;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang3.StringUtils;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;

import java.util.Arrays;
import java.util.HashMap;
import java.util.Map;
import java.util.stream.Collectors;

@Slf4j
public class RequestParams<T> {

    private Map<String, Object> map;

    private Class<T> clazz;

    public RequestParams(Class<T> clazz) {
        this.clazz = clazz;
        this.map = new HashMap<>();
    }

    public RequestParams() {
        this.map = new HashMap<>();
    }

    public void put(String key, Object value) {
        this.map.put(key, value);
    }

    public void setParameterMap(Map<String, String[]> map) {
        for (String key : map.keySet()) {
            String[] values = map.get(key);

            String value = Arrays.stream(values).collect(Collectors.joining(","));
            put(key, value);
        }
    }

    public String getString(String key, String defaultValue) {
        if (map.containsKey(key)) {
            String value = (String) map.get(key);

            if (StringUtils.isEmpty(value)) {
                return defaultValue;
            }

            return value;
        }

        return defaultValue;
    }

    public String getString(String key) {
        return getString(key, null);
    }

    public Integer getInt(String key, Integer defaultValue) {
        if (map.containsKey(key)) {
            Object value = map.get(key);

            if (value instanceof Integer) {
                return (Integer) value;
            } else if (value instanceof String) {
                if (StringUtils.isNotEmpty((String) value)) {
                    return Integer.parseInt((String) value);
                }
            }
        }
        return defaultValue;
    }

    public Integer getInt(String key) {
        return getInt(key, null);
    }

    public Long getLong(String key, Long defaultValue) {
        if (map.containsKey(key)) {
            Object value = map.get(key);

            if (value instanceof Long) {
                return (Long) value;
            } else if (value instanceof String) {
                if (StringUtils.isNotEmpty((String) value)) {
                    return Long.parseLong((String) value);
                }
            }
        }
        return defaultValue;
    }

    public Long getLong(String key) {
        return getLong(key, null);
    }

    public boolean getBoolean(String key, boolean defaultValue) {
        String value = getString(key);

        if (StringUtils.isEmpty(value)) {
            return defaultValue;
        }

        return Boolean.parseBoolean(value);
    }

    public boolean getBoolean(String key) {
        return getBoolean(key, false);
    }

    public Pageable getPageable() {
        int page = getInt("pageNumber", 0);
        int size = getInt("pageSize", 200);

        return PageRequest.of(page, size);
    }


    public boolean hasSortParameter() {
        return StringUtils.isNotEmpty(getString("sort"));
    }

    public Object getObject(String key) {
        if (map.containsKey(key)) {
            return map.get(key);
        }
        return null;
    }

    public Object getNotEmptyObject(String key) {
        if (!map.containsKey(key)) {
            throw new ApiException(key + " is not present");
        }

        Object object = map.get(key);

        if (object == null || StringUtils.isEmpty(object.toString())) {
            throw new ApiException(key + " is not present");
        }

        return object;
    }

    public Integer getNotEmptyInt(String key) {
        return Integer.parseInt(getNotEmptyString(key));
    }

    public String getNotEmptyString(String key) {
        return (String) getNotEmptyObject(key);
    }

    public Long getNotEmptyLong(String key) {
        return Long.parseLong(getNotEmptyString(key));
    }
}
