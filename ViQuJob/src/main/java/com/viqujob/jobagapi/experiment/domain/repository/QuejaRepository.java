package com.viqujob.jobagapi.experiment.domain.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.viqujob.jobagapi.experiment.domain.model.Queja;

@Repository
public interface QuejaRepository extends JpaRepository<Queja, Long> {
    List<Queja> findAllByCompanyId(Long companyId);
}
