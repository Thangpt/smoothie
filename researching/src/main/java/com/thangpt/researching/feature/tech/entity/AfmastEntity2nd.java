package com.thangpt.researching.feature.tech.entity;

import jakarta.persistence.Column;

import jakarta.persistence.Entity;
import jakarta.persistence.Id;
import jakarta.persistence.Table;
import lombok.Data;
import org.hibernate.annotations.DynamicUpdate;

@Data
@Entity
@Table(name = "AFMAST")
@DynamicUpdate
public class AfmastEntity2nd {
    @Id
    @Column(name = "ACCTNO", updatable = false)
    private String acctno;

    @Column(name = "ISDEFAULT")
    private String isDefault;

    @Column(name = "CUSTID", updatable = false)
    private String custid;

    @Column(name = "SUBACTYPE")
    private String subActype;
}
