package com.viqujob.jobagapi.security.service;

import com.viqujob.jobagapi.exception.ResourceNotFoundException;
import com.viqujob.jobagapi.security.domain.model.Employer;
import com.viqujob.jobagapi.security.domain.model.Postulant;
import com.viqujob.jobagapi.security.domain.repository.EmployerRepository;
import com.viqujob.jobagapi.security.domain.repository.PostulantRepository;
import com.viqujob.jobagapi.security.domain.service.AuthenticateService;
import com.viqujob.jobagapi.security.resource.SaveAuthenticateResource;
import com.viqujob.jobagapi.security.util.JwtUtil;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;

@Service
public class AuthenticateServiceImpl implements AuthenticateService {

    @Autowired
    private EmployerRepository employerRepository;

    @Autowired
    private PostulantRepository postulantRepository;

    @Autowired
    private BCryptPasswordEncoder encoder;

    @Autowired
    private JwtUtil jwtUtil;

    @Override
    public String authenticateEmployer(SaveAuthenticateResource resource) {
        var employer = employerRepository.findByEmail(resource.getEmail())
                .orElseThrow(() -> new ResourceNotFoundException("Invalid Credentials"));

        var valid = encoder.matches(resource.getPassword(), employer.getPassword());

        if (!valid) {
            throw new ResourceNotFoundException("Invalid Credentials");
        }

        var token = jwtUtil.generateToken(employer.getFirstname(), employer.getNumber(),
                "EMPLOYER");

        return token;
    }

    @Override
    public String authenticatePostulant(SaveAuthenticateResource resource) {
        var postulant = postulantRepository.findByEmail(resource.getEmail())
                .orElseThrow(() -> new ResourceNotFoundException("Invalid Credentials"));

        var valid = encoder.matches(resource.getPassword(), postulant.getPassword());

        if (!valid) {
            throw new ResourceNotFoundException("Invalid Credentials");
        }

        var token = jwtUtil.generateToken(postulant.getFirstname(), postulant.getNumber(),
                "POSTULANT");

        return token;
    }

    @Override
    public Employer authenticateEmployerFilter(String email) {
        var employer = employerRepository.findByEmail(email)
                .orElseThrow(() -> new ResourceNotFoundException("Invalid Credentials"));

        return employer;
    }

    @Override
    public Postulant authenticatePostulantFilter(String email) {
        var employer = postulantRepository.findByEmail(email)
                .orElseThrow(() -> new ResourceNotFoundException("Invalid Credentials"));

        return employer;
    }

}
