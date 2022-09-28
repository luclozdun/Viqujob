package com.viqujob.jobagapi.membership.service;

import com.viqujob.jobagapi.exception.ResourceNotFoundException;
import com.viqujob.jobagapi.membership.domain.model.PlanEmployer;
import com.viqujob.jobagapi.membership.domain.repository.PlanEmployerRepository;
import com.viqujob.jobagapi.membership.domain.service.PlanEmployerService;
import com.viqujob.jobagapi.security.domain.model.Employer;
import com.viqujob.jobagapi.security.domain.repository.EmployerRepository;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

@Service
public class PlanEmployerServiceImpl implements PlanEmployerService {
    @Autowired
    private EmployerRepository employeerRepository;

    @Autowired
    private PlanEmployerRepository planemployeerRepository;

    @Override
    public Page<PlanEmployer> getAllPlanemployeersByEmployeerId(Long employeerId, Pageable pageable) {
        return planemployeerRepository.findByEmployeerId(employeerId, pageable);
    }

    @Override
    public PlanEmployer getPlanemployeerByIdAndEmployeerId(Long planemployeersId, Long employeerId) {
        return planemployeerRepository.findByIdAndEmployeerId(planemployeersId, employeerId)
                .orElseThrow(() -> new ResourceNotFoundException(
                        "Lead not found with Id" + planemployeersId +
                                "and EmployeerId" + employeerId));

    }

    @Override
    public PlanEmployer createPlanemployeer(Long employeerId, PlanEmployer planemployeer) {

        Employer employer = employeerRepository.findById(employeerId)
                .orElseThrow(() -> new ResourceNotFoundException(
                        "Employeer", "Id", employeerId));
        planemployeer.setEmployeer(employer);
        return planemployeerRepository.save(planemployeer);

    }

    @Override
    public PlanEmployer updatePlanemployeer(Long employeerId, Long planemployeerId,
            PlanEmployer planemployeerDetails) {
        if (!employeerRepository.existsById(employeerId))
            throw new ResourceNotFoundException("Employeer", "Id", employeerId);

        return planemployeerRepository.findById(planemployeerId).map(planemployeer -> {

            planemployeer.setDescription(planemployeerDetails.getDescription())
                    .setLimit_videoconference(planemployeerDetails.getLimit_videoconference())
                    .setLimit_modification(planemployeerDetails.getLimit_modification())
                    .setAsistence(planemployeerDetails.isAsistence())
                    .setDuration(planemployeerDetails.getDuration())

            ;

            return planemployeerRepository.save(planemployeer);

        }).orElseThrow(() -> new ResourceNotFoundException(
                "Planemployeer", "Id", planemployeerId));
    }

    @Override
    public ResponseEntity<?> deletePlanemployeer(Long employeerId, Long planemployeerId) {
        if (!planemployeerRepository.existsById(planemployeerId))
            throw new ResourceNotFoundException("Employeer", "Id", employeerId);

        return planemployeerRepository.findById(planemployeerId).map(planemployeer -> {
            planemployeerRepository.delete(planemployeer);
            return ResponseEntity.ok().build();

        }).orElseThrow(() -> new ResourceNotFoundException(
                "Planemployeer", "Id", planemployeerId));

    }
}
