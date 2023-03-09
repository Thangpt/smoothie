package com.thangpt.researching.feature.tech.repository;

import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.transaction.annotation.Transactional;

import com.thangpt.researching.feature.tech.entity.AfmastEntity2nd;

public interface Afmast2ndRepository extends JpaRepository<AfmastEntity2nd, String> {
    @Transactional(readOnly = true)
    Optional<AfmastEntity2nd> findByAcctno(String acctno);
}
