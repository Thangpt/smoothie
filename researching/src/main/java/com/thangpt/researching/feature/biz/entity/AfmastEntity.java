package com.thangpt.researching.feature.biz.entity;

import jakarta.persistence.Column;

// import javax.persistence.Column;
// import javax.persistence.Id;
// import javax.persistence.Table;
// import javax.persistence.CascadeType;
// import javax.persistence.FetchType;
// import javax.persistence.JoinColumn;
// import javax.persistence.OneToOne;

// import org.hibernate.annotations.NotFound;
// import org.hibernate.annotations.NotFoundAction;

import jakarta.persistence.Entity;
import jakarta.persistence.Id;
import jakarta.persistence.Table;
import lombok.Data;
import org.hibernate.annotations.DynamicUpdate;

@Data
@Entity
@Table(name = "AFMAST")
@DynamicUpdate
public class AfmastEntity {
    @Id
    @Column(name = "ACCTNO", updatable = false)
    private String acctno;

    @Column(name = "ISDEFAULT")
    private String isDefault;

    @Column(name = "CUSTID", updatable = false)
    private String custid;

    @Column(name = "SUBACTYPE")
    private String subActype;

    // @OneToOne(cascade = CascadeType.ALL, fetch = FetchType.EAGER)
    // @JoinColumn(name = "ACTYPE", referencedColumnName = "ACTYPE", updatable = false, insertable = false)
    // @NotFound(action = NotFoundAction.IGNORE)
    // @IgnoreField
    // private AftypeEntity aftypeEntity;
}