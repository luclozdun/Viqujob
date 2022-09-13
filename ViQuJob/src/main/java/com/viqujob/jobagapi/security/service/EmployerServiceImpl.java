package com.viqujob.jobagapi.security.service;

import com.viqujob.jobagapi.exception.ResourceNotFoundException;
import com.viqujob.jobagapi.security.domain.model.Employer;
import com.viqujob.jobagapi.security.domain.repository.EmployerRepository;
import com.viqujob.jobagapi.security.domain.repository.UserRepository;
import com.viqujob.jobagapi.security.domain.service.EmployerService;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

@Service
public class EmployerServiceImpl implements EmployerService {
    @Autowired
    private EmployerRepository employeerRepository;

    @Autowired
    UserRepository userRepository;

    @Override
    public Page<Employer> getAllEmployeers(Pageable pageable) {
        return employeerRepository.findAll(pageable);
    }

    @Override
    public Employer getEmployeerById(Long employeerId) {
        return employeerRepository.findById(employeerId)
                .orElseThrow(() -> new ResourceNotFoundException("Employeer", "Id", employeerId));
    }

    @Override
    public Employer createEmployeer(Employer employeer) {

        if (userRepository.existsByEmail(employeer.getEmail())) {
            throw new ResourceNotFoundException("El email ya esta en uso");
        }
        if (userRepository.existsByNumber(employeer.getNumber())) {
            throw new ResourceNotFoundException("El numero ya esta en uso");

        }
        return employeerRepository.save(employeer);
    }

    @Override
    public ResponseEntity<?> deleteEmployeer(Long employeerId) {
        Employer employeer = employeerRepository.findById(employeerId)
                .orElseThrow(() -> new ResourceNotFoundException("Employeer", "Id", employeerId));
        employeerRepository.delete(employeer);
        return ResponseEntity.ok().build();
    }
}
