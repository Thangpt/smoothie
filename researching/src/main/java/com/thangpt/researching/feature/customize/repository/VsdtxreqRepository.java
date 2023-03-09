package com.thangpt.researching.feature.customize.repository;

import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.transaction.annotation.Transactional;

import com.thangpt.researching.feature.customize.entity.VsdtxreqEntity;

public interface VsdtxreqRepository extends JpaRepository<VsdtxreqEntity, String>{
    @Transactional(readOnly = true)
    Optional<VsdtxreqEntity> findByReqId(Long reqId);
}
