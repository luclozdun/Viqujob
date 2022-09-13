package com.viqujob.jobagapi.enterprise.domain.repository;

import com.viqujob.jobagapi.enterprise.domain.model.Sector;
import com.viqujob.jobagapi.security.domain.model.Employer;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface SectorRepository extends JpaRepository<Sector, Long> {
    public Page<Employer> findById(Long Id, Pageable page);
}
