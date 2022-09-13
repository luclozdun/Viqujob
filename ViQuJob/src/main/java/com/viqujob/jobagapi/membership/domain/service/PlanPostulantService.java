package com.viqujob.jobagapi.membership.domain.service;

import com.viqujob.jobagapi.membership.domain.model.PlanPostulant;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.http.ResponseEntity;

public interface PlanPostulantService {

    Page<PlanPostulant> getAllPlanPostulantsByPostulantId(Long postulantId, Pageable pageable);

    PlanPostulant getPlanPostulantByIdAndPostulantId(Long planpostulantId, Long postulantId);

    PlanPostulant createPlanPostulant(Long postulantId, PlanPostulant planpostulant);

    PlanPostulant updatePlanPostulant(Long postulantId, Long planpostulantId, PlanPostulant planpostulantDetails);

    ResponseEntity<?> deletePlanPostulant(Long postulantId, Long planpostulantId);
}
