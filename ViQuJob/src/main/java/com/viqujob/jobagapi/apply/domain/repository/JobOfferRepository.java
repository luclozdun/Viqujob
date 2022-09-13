package com.viqujob.jobagapi.apply.domain.repository;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

import com.viqujob.jobagapi.apply.domain.model.JobOffer;

@Repository
public interface JobOfferRepository extends JpaRepository<JobOffer, Long> {
    public Page<JobOffer> findById(Long Id, Pageable pageable);

    Page<JobOffer> findByEmployeerId(Long EmployeerId, Pageable pageable);

    public Optional<JobOffer> findByIdAndEmployeerId(Long id, Long employerId);
}
