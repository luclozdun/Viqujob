package com.viqujob.jobagapi.experiment.domain.service;

import java.util.List;

import com.viqujob.jobagapi.experiment.domain.model.Queja;

public interface QuejaService {
    List<Queja> findAll(Long companyId);

    Queja create(Queja queja);
}
