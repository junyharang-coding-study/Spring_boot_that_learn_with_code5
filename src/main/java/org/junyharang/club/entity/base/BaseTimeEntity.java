package org.junyharang.club.entity.base;

import lombok.Getter;
import org.springframework.data.annotation.CreatedDate;
import org.springframework.data.annotation.LastModifiedDate;
import org.springframework.data.jpa.domain.support.AuditingEntityListener;

import javax.persistence.Column;
import javax.persistence.EntityListeners;
import javax.persistence.MappedSuperclass;
import java.time.LocalDateTime;

@EntityListeners(value = {
        AuditingEntityListener.class
})
@MappedSuperclass @Getter
public class BaseTimeEntity {

    @CreatedDate @Column(name = "regDate", updatable = false)
    private LocalDateTime regDate;

    @LastModifiedDate @Column(name = "modDate")
    private LocalDateTime modDate;



} // class 끝
