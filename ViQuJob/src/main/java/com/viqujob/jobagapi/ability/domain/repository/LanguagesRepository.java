package com.viqujob.jobagapi.ability.domain.repository;

import com.viqujob.jobagapi.ability.domain.model.Languages;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface LanguagesRepository extends JpaRepository<Languages, Long> {
    public Page<Languages> findById(Long Id, Pageable page);
}
