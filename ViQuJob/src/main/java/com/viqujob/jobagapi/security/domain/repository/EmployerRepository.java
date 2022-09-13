package com.viqujob.jobagapi.security.domain.repository;

import java.util.Optional;

import com.viqujob.jobagapi.security.domain.model.Employer;

import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Page;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface EmployerRepository extends JpaRepository<Employer, Long> {
    public Page<Employer> findById(Long Id, Pageable page);

    public Optional<Employer> findByPosicion(String posicion);

    public Optional<Employer> findByEmail(String email);
}
