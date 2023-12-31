package com.zerobase.store.global.entity;

import com.fasterxml.jackson.annotation.JsonFormat;
import lombok.Getter;
import org.springframework.data.annotation.CreatedDate;
import org.springframework.data.annotation.LastModifiedDate;
import org.springframework.data.jpa.domain.support.AuditingEntityListener;

import javax.persistence.*;
import java.time.LocalDateTime;

@Getter
@MappedSuperclass
@EntityListeners(AuditingEntityListener.class)
public class BaseEntity {
    @Column(name = "CREATED_DT", updatable = false)
    @CreatedDate
    @JsonFormat(shape = JsonFormat.Shape.STRING, pattern = "yyyy-MM-dd HH:mm:ss", timezone = "Asia/Seoul")
    private LocalDateTime createdDt;

    @Column(name = "MODIFIED_DT")
    @LastModifiedDate
    @JsonFormat(shape = JsonFormat.Shape.STRING, pattern = "yyyy-MM-dd HH:mm:ss", timezone = "Asia/Seoul")
    private LocalDateTime modifiedDt;

    // 데이터가 저장되기 전에 실행되는 메서드
    @PrePersist
    public void onPrePersist() {
        this.createdDt = LocalDateTime.now();
        this.modifiedDt = this.createdDt;
    }

    // 데이터가 수정되기 전에 실행되는 메서드
    @PreUpdate
    public void onPreUpdate() {
        this.modifiedDt = LocalDateTime.now();
    }
}
