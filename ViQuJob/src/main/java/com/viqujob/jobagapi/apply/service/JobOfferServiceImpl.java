package com.viqujob.jobagapi.apply.service;

import com.viqujob.jobagapi.apply.domain.model.JobOffer;
import com.viqujob.jobagapi.apply.domain.repository.JobOfferRepository;
import com.viqujob.jobagapi.apply.domain.service.JobOfferService;
import com.viqujob.jobagapi.exception.ResourceNotFoundException;
import com.viqujob.jobagapi.security.domain.repository.EmployerRepository;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

@Service
public class JobOfferServiceImpl implements JobOfferService {
    @Autowired
    private JobOfferRepository jobOfferRepository;
    @Autowired
    private EmployerRepository employeerRepository;

    @Override
    public Page<JobOffer> getAllJobOffersByEmployeerId(Long employeerId, Pageable pageable) {
        return jobOfferRepository.findByEmployeerId(employeerId, pageable);
    }

    //////////////////////////////
    @Override
    public JobOffer getJobOfferByIdAndEmployeerId(Long employeerId, Long jobOfferId) {
        return jobOfferRepository.findByIdAndEmployeerId(employeerId, jobOfferId)
                .orElseThrow(() -> new ResourceNotFoundException(
                        "Job Offer not found with id" + jobOfferId +
                                "and EmployeerId" + employeerId));
    }

    @Override
    public JobOffer getJobOfferById(Long jobOfferId) {
        return jobOfferRepository.findById(jobOfferId)
                .orElseThrow(() -> new ResourceNotFoundException("Job Offer", "Id", jobOfferId));
    }

    @Override
    public JobOffer createJobOffer(Long employeerId, JobOffer jobOffer) {
        if (jobOffer.getSalary() < 930)
            throw new ResourceNotFoundException("El salario debe ser mayor o igual a 930");
        return employeerRepository.findById(employeerId).map(employeer -> {
            jobOffer.setEmployeer(employeer);
            return jobOfferRepository.save(jobOffer);
        }).orElseThrow(() -> new ResourceNotFoundException("Employeer", "Id", employeerId));
    }

    @Override
    public JobOffer updateJobOffer(Long employeerId, Long jobOfferId, JobOffer jobOfferDetails) {
        if (!employeerRepository.existsById(employeerId))
            throw new ResourceNotFoundException("Employeer", "Id", employeerId);
        return jobOfferRepository.findById(jobOfferId).map(jobOffer -> {
            jobOffer.setTitle(jobOfferDetails.getTitle())
                    .setType(jobOfferDetails.getType())
                    .setDescription(jobOfferDetails.getDescription())
                    .setDirection(jobOfferDetails.getDirection())
                    .setSalary(jobOfferDetails.getSalary())
                    .setBegin_date_offer(jobOfferDetails.getBegin_date_offer())
                    .setFinal_date_offer(jobOfferDetails.getFinal_date_offer());
            return jobOfferRepository.save(jobOffer);
        }).orElseThrow(() -> new ResourceNotFoundException("Job Offer", "Id", jobOfferId));
    }

    @Override
    public Page<JobOffer> getAllJobOffer(Pageable pageable) {
        return jobOfferRepository.findAll(pageable);
    }

    @Override
    public ResponseEntity<?> deleteJobOffer(Long employeerId, Long jobOfferId) {
        if (!employeerRepository.existsById(employeerId))
            throw new ResourceNotFoundException("Employeer", "Id", employeerId);
        return jobOfferRepository.findById(jobOfferId).map(jobOffer -> {
            jobOfferRepository.delete(jobOffer);
            return ResponseEntity.ok().build();
        }).orElseThrow(() -> new ResourceNotFoundException("Job Offer", "Id", jobOfferId));
    }
}
