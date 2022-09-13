package com.viqujob.jobagapi.membership.domain.service;

import com.viqujob.jobagapi.membership.domain.model.PlanEmployer;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.http.ResponseEntity;

public interface PlanEmployerService {
    Page<PlanEmployer> getAllPlanemployeersByEmployeerId(Long employeerId, Pageable pageable);

    PlanEmployer getPlanemployeerByIdAndEmployeerId(Long planemployeersId, Long employeerId);

    PlanEmployer createPlanemployeer(Long employeerId, PlanEmployer planemployeer);

    PlanEmployer updatePlanemployeer(Long employeerId, Long planemployeerId, PlanEmployer planemployeerDetails);

    ResponseEntity<?> deletePlanemployeer(Long employeerId, Long planemployeerId);
}
