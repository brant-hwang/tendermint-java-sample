package com.chequer.tendermint.domain.base;


import com.fasterxml.jackson.annotation.JsonIgnore;
import lombok.Getter;
import lombok.Setter;
import org.hibernate.annotations.DynamicInsert;
import org.hibernate.annotations.DynamicUpdate;
import org.springframework.data.domain.Persistable;

import javax.persistence.*;
import java.io.Serializable;
import java.time.LocalDateTime;

@Setter
@Getter
@MappedSuperclass
@DynamicInsert
@DynamicUpdate
public abstract class BaseJpaModel<PK extends Serializable> implements Persistable<PK>, Serializable {

    @Override
    @JsonIgnore
    public boolean isNew() {
        return null == getId();
    }

    @Column(name = "CREATED_AT", updatable = false, columnDefinition = "DATE")
    protected LocalDateTime createdAt = LocalDateTime.now();

    @Column(name = "UPDATED_AT", columnDefinition = "DATE")
    protected LocalDateTime updatedAt;


    @PrePersist
    protected void onPersist() {
        if (this.createdAt == null) {
            this.createdAt = this.updatedAt = LocalDateTime.now();
        }
    }

    @PreUpdate
    protected void onUpdate() {
        this.updatedAt = LocalDateTime.now();
    }

    @PostLoad
    protected void onPostLoad() {

    }
}
