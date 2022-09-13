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

import com.viqujob.jobagapi.membership.domain.model.PlanEmployer;
import com.viqujob.jobagapi.membership.domain.service.PlanEmployerService;
import com.viqujob.jobagapi.membership.resource.PlanEmployerResource;
import com.viqujob.jobagapi.membership.resource.SavePlanEmployerResource;

import java.util.List;
import java.util.stream.Collectors;

@RestController
@RequestMapping("/employeers")
public class PlanEmployerController {
    @Autowired
    private PlanEmployerService planemployeerService;

    @Autowired
    private ModelMapper mapper;

    @Operation(summary = "Get planemployeers", description = "Get all PlanEmployeers")
    @GetMapping("/{employeerId}/planemployeers")
    public Page<PlanEmployerResource> getAllPlanemployeersByEmployeerId(@PathVariable Long employeerId,
            Pageable pageable) {
        Page<PlanEmployer> planemployeerPage = planemployeerService.getAllPlanemployeersByEmployeerId(employeerId,
                pageable);
        List<PlanEmployerResource> resources = planemployeerPage.getContent().stream().map(
                this::convertToResource).collect(Collectors.toList());
        return new PageImpl<>(resources, pageable, resources.size());
    }

    @Operation(summary = "Get planemployeers", description = "Get PlanEmployeer by Employeer ID")
    @GetMapping("/{employeerId}/planemployeers/{planemployeerId}")
    public PlanEmployerResource getPlanemployeerByIdAndEmployeerId(@PathVariable Long employeerId,
            @PathVariable Long planemployeerId) {
        return convertToResource(planemployeerService.getPlanemployeerByIdAndEmployeerId(planemployeerId, employeerId));
    }

    @Operation(summary = "Post planemployeers", description = "Create PlanEmployeer")
    @PostMapping("/{employeerId}/planemployeers")
    public PlanEmployerResource createPlanemployeer(
            @PathVariable Long employeerId, @Valid @RequestBody SavePlanEmployerResource resource) {
        return convertToResource(planemployeerService.createPlanemployeer(employeerId, convertToEntity(resource)));
    }

    @Operation(summary = "Put planemployeers", description = "Update PlanEmployeer")
    @PutMapping("/{employeerId}/planemployeers/{planemployeerId}")
    public PlanEmployerResource updatePlanemployeer(
            @PathVariable Long employeerId,
            @PathVariable Long planemployeerId,
            @Valid @RequestBody SavePlanEmployerResource resource) {
        return convertToResource(
                planemployeerService.updatePlanemployeer(employeerId, planemployeerId, convertToEntity(resource)));
    }

    @Operation(summary = "Delete planemployeers", description = "Delete PlanEmployeer")
    @DeleteMapping("/{employeerId}/planemployeers/{planemployeerId}")
    public ResponseEntity<?> deletePlanemployeer(
            @PathVariable Long employeerId,
            @PathVariable Long planemployeerId) {
        return planemployeerService.deletePlanemployeer(employeerId, planemployeerId);
    }

    private PlanEmployer convertToEntity(SavePlanEmployerResource resource) {
        return mapper.map(resource, PlanEmployer.class);
    }

    private PlanEmployerResource convertToResource(PlanEmployer entity) {
        return mapper.map(entity, PlanEmployerResource.class);
    }
}
