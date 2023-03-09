package com.thangpt.researching.feature.customize.entity;

import org.hibernate.annotations.DynamicUpdate;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.Id;
import jakarta.persistence.Table;
import lombok.Data;

@Data
@Entity
@Table(name = "VSDTXREQDTL")
@DynamicUpdate
public class VsdtxreqdtlEntity {
    @Id
    @Column(name = "AUTOID", updatable = false)
    private Long autoId;

    @Column(name = "REQID")
    private Long reqId;

    @Column(name = "FLDNAME")
    private String fldName;

    @Column(name = "CVAL")
    private String cval;

    @Column(name = "NVAL")
    private Long nval;

    @Column(name = "CONVERT")
    private String convert;

}
