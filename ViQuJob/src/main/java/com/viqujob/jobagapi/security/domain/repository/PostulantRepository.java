package com.viqujob.jobagapi.security.domain.repository;

import java.util.Optional;

import com.viqujob.jobagapi.security.domain.model.Postulant;

import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Page;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface PostulantRepository extends JpaRepository<Postulant, Long> {
    public Page<Postulant> findById(Long Id, Pageable page);

    public Optional<Postulant> findByEmail(String email);
}
