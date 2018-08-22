package com.chequer.tendermint.support.api.response;

import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.NonNull;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;

@Data
@NoArgsConstructor
@RequiredArgsConstructor(staticName = "of")
public class PageableVO {

    @NonNull
    protected Integer totalPages;

    @NonNull
    protected Long totalElements;

    @NonNull
    protected Integer currentPage;

    @NonNull
    protected Integer pageSize;

    public static PageableVO of(Page pages) {
        return of(pages.getTotalPages(), pages.getTotalElements(), pages.getNumber(), pages.getSize());
    }
}
