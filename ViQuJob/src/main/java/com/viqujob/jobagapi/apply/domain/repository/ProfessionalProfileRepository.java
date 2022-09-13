package com.viqujob.jobagapi.apply.domain.repository;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

import com.viqujob.jobagapi.apply.domain.model.ProfessionalProfile;

@Repository
public interface ProfessionalProfileRepository extends JpaRepository<ProfessionalProfile, Long> {
    Page<ProfessionalProfile> findByPostulantId(Long postulantId, Pageable pageable); // Encontrar por Id

    Optional<ProfessionalProfile> findByIdAndPostulantId(Long id, Long postulantId);
}
