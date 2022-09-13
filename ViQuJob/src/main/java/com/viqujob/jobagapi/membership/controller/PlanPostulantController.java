package com.viqujob.jobagapi.membership.controller;

import io.swagger.v3.oas.annotations.Operation;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.Pageable;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;

import com.viqujob.jobagapi.membership.domain.model.PlanPostulant;
import com.viqujob.jobagapi.membership.domain.service.PlanPostulantService;
import com.viqujob.jobagapi.membership.resource.PlanPostulantResource;
import com.viqujob.jobagapi.membership.resource.SavePlanPostulantResource;

import java.util.List;
import java.util.stream.Collectors;

@RestController
@RequestMapping("/postulants")
public class PlanPostulantController {

    @Autowired
    private PlanPostulantService planPostulantService;

    @Autowired
    private ModelMapper mapper;

    @Operation(summary = "Get planpostulants", description = "Get all PlanPostulants")
    @GetMapping("/{postulantId}/planpostulants")
    public Page<PlanPostulantResource> getAllPlanPostulantsByPostulantId(@PathVariable Long postulantId,
            Pageable pageable) {
        Page<PlanPostulant> planpostulantPage = planPostulantService.getAllPlanPostulantsByPostulantId(postulantId,
                pageable);
        List<PlanPostulantResource> resources = planpostulantPage.getContent().stream().map(
                this::convertToResource).collect(Collectors.toList());
        return new PageImpl<>(resources, pageable, resources.size());
    }

    @Operation(summary = "Get planpostulants", description = "Get PlanPostulants by postulant Id")
    @GetMapping("/{postulantId}/planpostulants/{planpostulantId}")
    public PlanPostulantResource getPlanemployeerByIdAndEmployeerId(@PathVariable Long postulantId,
            @PathVariable Long planpostulantId) {
        return convertToResource(planPostulantService.getPlanPostulantByIdAndPostulantId(planpostulantId, postulantId));
    }

    @Operation(summary = "Post planpostulants", description = "Create PlanPostulant")
    @PostMapping("/{postulantId}/planpostulants")
    public PlanPostulantResource createPlanPostulant(
            @PathVariable Long postulantId, @Valid @RequestBody SavePlanPostulantResource resource) {
        return convertToResource(planPostulantService.createPlanPostulant(postulantId, convertToEntity(resource)));
    }

    @Operation(summary = "Put planpostulants", description = "Update PlanPostulant")
    @PutMapping("/{postulantId}/planpostulants/{planpostulantId}")
    public PlanPostulantResource updatePlanemployeer(
            @PathVariable Long postulantId,
            @PathVariable Long planpostulantId,
            @Valid @RequestBody SavePlanPostulantResource resource) {
        return convertToResource(
                planPostulantService.updatePlanPostulant(postulantId, planpostulantId, convertToEntity(resource)));
    }

    @Operation(summary = "Delete planpostulants", description = "Delete PlanPostulant")
    @DeleteMapping("/{postulantId}/planpostulants/{planpostulantId}")
    public ResponseEntity<?> deletePlanemployeer(
            @PathVariable Long postulantId,
            @PathVariable Long planpostulantId) {
        return planPostulantService.deletePlanPostulant(postulantId, planpostulantId);
    }

    private PlanPostulant convertToEntity(SavePlanPostulantResource resource) {
        return mapper.map(resource, PlanPostulant.class);
    }

    private PlanPostulantResource convertToResource(PlanPostulant entity) {
        return mapper.map(entity, PlanPostulantResource.class);
    }
}
