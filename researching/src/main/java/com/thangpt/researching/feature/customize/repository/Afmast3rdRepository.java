package com.thangpt.researching.feature.customize.repository;

import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.transaction.annotation.Transactional;

import com.thangpt.researching.feature.customize.entity.AfmastEntity3rd;

public interface Afmast3rdRepository extends JpaRepository<AfmastEntity3rd, String> {
    @Transactional(readOnly = true)
    Optional<AfmastEntity3rd> findByAcctno(String acctno);
}
