package com.viqujob.jobagapi.security.service;

import com.viqujob.jobagapi.exception.ResourceNotFoundException;
import com.viqujob.jobagapi.security.domain.model.Employer;
import com.viqujob.jobagapi.security.domain.model.Postulant;
import com.viqujob.jobagapi.security.domain.repository.EmployerRepository;
import com.viqujob.jobagapi.security.domain.repository.PostulantRepository;
import com.viqujob.jobagapi.security.domain.repository.UserRepository;
import com.viqujob.jobagapi.security.domain.service.RegisterService;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;

@Service
public class RegisterServiceImpl implements RegisterService {

    @Autowired
    private EmployerRepository employerRepository;

    @Autowired
    private PostulantRepository postulantRepository;

    @Autowired
    private UserRepository userRepository;

    @Autowired
    private BCryptPasswordEncoder encrypt;

    @Override
    public Employer createEmployer(Employer employer) {

        System.out.println("Paso por aca ------------------------ 11");
        if (userRepository.existsByEmail(employer.getEmail()).equals(true)) {
            throw new ResourceNotFoundException("El email ya esta en uso");
        }

        System.out.println("Paso por aca ------------------------ 22");
        if (userRepository.existsByNumber(employer.getNumber()).equals(true)) {
            throw new ResourceNotFoundException("El numero ya esta en uso");
        }

        System.out.println("Paso por aca ------------------------");

        String password = encrypt.encode(employer.getPassword());

        employer.setPassword(password);

        try {
            Employer response = employerRepository.save(employer);
            return response;
        } catch (Exception e) {
            throw new ResourceNotFoundException("Error ocurred while saving employer");
        }
    }

    @Override
    public Postulant createPostulant(Postulant postulant) {
        System.out.println("Paso por aca ------------------------ 11");
        if (userRepository.existsByEmail(postulant.getEmail()).equals(true)) {
            throw new ResourceNotFoundException("El email ya esta en uso");
        }
        System.out.println("Paso por aca ------------------------ 22");
        if (userRepository.existsByNumber(postulant.getNumber()).equals(true)) {
            throw new ResourceNotFoundException("El numero ya esta en uso");
        }
        System.out.println("Paso por aca ------------------------");
        String password = encrypt.encode(postulant.getPassword());

        postulant.setPassword(password);
        System.out.println("Paso por aca ------------------------");

        try {
            Postulant response = postulantRepository.save(postulant);
            return response;
        } catch (Exception e) {
            throw new ResourceNotFoundException("Error ocurred while saving employer");
        }
    }

}
