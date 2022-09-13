package com.viqujob.jobagapi.membership.domain.repository;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

import com.viqujob.jobagapi.membership.domain.model.PlanPostulant;

public interface PlanPostulantRepository extends JpaRepository<PlanPostulant, Long> {

    Page<PlanPostulant> findByPostulantId(Long postulantId, Pageable pageable);

    public Optional<PlanPostulant> findByIdAndPostulantId(Long id, Long postulantId);
}
