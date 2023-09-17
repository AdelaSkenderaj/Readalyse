package com.readalyse.entities;

import jakarta.persistence.Column;
import org.springframework.data.annotation.CreatedDate;
import org.springframework.data.annotation.LastModifiedDate;

import java.util.Date;

public class AuditedEntity {

    @CreatedDate
    @Column(name = "INSERT_TIME")
    private Date insertTime;

    @LastModifiedDate
    @Column(name = "UPDATE_TIME")
    private Date updateTime;
}
