package com.viqujob.jobagapi.security.domain.service;

import com.viqujob.jobagapi.security.domain.model.Employer;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.http.ResponseEntity;

public interface EmployerService {

    Page<Employer> getAllEmployeers(Pageable pageable);

    Employer getEmployeerById(Long employeerId);

    Employer createEmployeer(Employer employeer);

    ResponseEntity<?> deleteEmployeer(Long employeerId);
}
