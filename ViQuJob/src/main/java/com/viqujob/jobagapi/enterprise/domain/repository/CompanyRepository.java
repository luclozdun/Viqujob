package com.viqujob.jobagapi.enterprise.domain.repository;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

import com.viqujob.jobagapi.enterprise.domain.model.Company;

@Repository
public interface CompanyRepository extends JpaRepository<Company, Long> {
    Page<Company> findById(Long Id, Pageable pageable);

    Optional<Company> findByEmployeerIdAndSectorId(Long EmployeerId, Long SectorId);

    Page<Company> findByEmployeerId(Long EmployeerId, Pageable pageable);

    Boolean existsByEmployeerId(Long employeerId);
}
