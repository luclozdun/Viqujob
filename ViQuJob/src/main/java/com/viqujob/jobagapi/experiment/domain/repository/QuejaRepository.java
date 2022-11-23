package com.viqujob.jobagapi.experiment.domain.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import com.viqujob.jobagapi.experiment.domain.model.Queja;

@Repository
public interface QuejaRepository extends JpaRepository<Queja, Long> {

    @Query("select o from Queja o where cid = :cid")
    List<Queja> FindCompany(@Param("cid") Long userStatus);
}
