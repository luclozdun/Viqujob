package com.viqujob.jobagapi.experiment.controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

import com.viqujob.jobagapi.experiment.domain.model.Queja;
import com.viqujob.jobagapi.experiment.domain.service.QuejaService;

@RestController
public class QuejaController {

    @Autowired
    private QuejaService quejaService;

    @GetMapping("/quejas/{companyId}")
    public List<Queja> find(
            @PathVariable Long companyId) {
        return quejaService.findAll(companyId);
    }

    @PostMapping("/quejas")
    public Queja create(
            @RequestBody Queja queja) {
        return quejaService.create(queja);
    }
}
