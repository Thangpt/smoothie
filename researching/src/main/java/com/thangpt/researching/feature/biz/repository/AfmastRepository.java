package com.thangpt.researching.feature.biz.repository;

import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

import com.thangpt.researching.feature.biz.entity.AfmastEntity;
@Repository
public interface AfmastRepository extends JpaRepository<AfmastEntity, String> {
    @Transactional(readOnly = true)
    Optional<AfmastEntity> findByAcctno(String acctno);
}