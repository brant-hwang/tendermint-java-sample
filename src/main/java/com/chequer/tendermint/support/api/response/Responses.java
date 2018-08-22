package com.chequer.tendermint.support.api.response;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.NonNull;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;

import java.util.List;
import java.util.Map;

public class Responses {

    @Data
    @NoArgsConstructor
    @RequiredArgsConstructor(staticName = "of")
    public static class ListResponse {

        @JsonProperty("list")
        @NonNull
        List<?> list;

        @NonNull
        PageableVO page = PageableVO.of(0, 0L, 0, 0);
    }

    @Data
    @NoArgsConstructor
    @RequiredArgsConstructor(staticName = "of")
    public static class MapResponse {

        @NonNull
        @JsonProperty("map")
        Map<String, Object> map;
    }

    @Data
    @NoArgsConstructor
    public static class PageResponse {
        @NonNull
        @JsonProperty("list")
        List<?> list;

        @NonNull
        PageableVO page;

        public static Responses.PageResponse of(List<?> content, Page<?> page) {
            Responses.PageResponse pageResponse = new Responses.PageResponse();
            pageResponse.setList(content);
            pageResponse.setPage(PageableVO.of(page));
            return pageResponse;
        }

        public static Responses.PageResponse of(Page<?> page) {
            return of(page.getContent(), page);
        }
    }
}
