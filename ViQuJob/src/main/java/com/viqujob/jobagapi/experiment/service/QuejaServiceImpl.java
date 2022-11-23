package com.viqujob.jobagapi.experiment.service;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.viqujob.jobagapi.experiment.domain.model.Queja;
import com.viqujob.jobagapi.experiment.domain.repository.QuejaRepository;
import com.viqujob.jobagapi.experiment.domain.service.QuejaService;

@Service
public class QuejaServiceImpl implements QuejaService {

    @Autowired
    private QuejaRepository quejaRepository;

    @Override
    public List<Queja> findAll(Long companyId) {
        return quejaRepository.findAllByCompanyId(companyId);
    }

    @Override
    public Queja create(Queja queja) {
        return quejaRepository.save(queja);
    }

}
