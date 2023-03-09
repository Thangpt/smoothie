package com.thangpt.researching.feature.customize.entity;

import java.util.List;

import org.hibernate.annotations.DynamicUpdate;
import org.hibernate.annotations.NotFound;
import org.hibernate.annotations.NotFoundAction;

import jakarta.persistence.CascadeType;
import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.FetchType;
import jakarta.persistence.Id;
import jakarta.persistence.OneToMany;
import jakarta.persistence.Table;
import lombok.Data;

@Data
@Entity
@Table(name = "VSDTXREQ")
@DynamicUpdate
public class VsdtxreqEntity {
    @Id
    @Column(name = "REQID", updatable = false)
    private Long reqId;

    @Column(name = "TRFCODE")
    private String trfCode;

    @Column(name = "REFCODE")
    private Long refCode;

    @Column(name = "STATUS")
    private String status;

    @Column(name = "MSGSTATUS")
    private String msgStatus;

    @Column(name = "PROCESS_ID")
    private String processId;

    @Column(name = "BOPROCESS")
    private String boProcess;

    @OneToMany(cascade = CascadeType.ALL, mappedBy = "reqId", fetch = FetchType.LAZY, targetEntity = VsdtxreqdtlEntity.class)
    @NotFound(action = NotFoundAction.IGNORE)
    private List<VsdtxreqdtlEntity> vsdtxreqdtlEntities;
}
