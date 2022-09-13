package com.viqujob.jobagapi.apply.controller;

import io.swagger.v3.oas.annotations.Operation;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.Pageable;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;

import com.viqujob.jobagapi.apply.domain.model.ProfessionalProfile;
import com.viqujob.jobagapi.apply.domain.service.ProfessionalProfileService;
import com.viqujob.jobagapi.apply.resource.ProfessionalProfileResource;
import com.viqujob.jobagapi.apply.resource.SaveProfessionalProfileResource;

import java.util.List;
import java.util.stream.Collectors;

@RestController
public class ProfessionalProfileController {
    @Autowired
    private ProfessionalProfileService professionalprofileService;

    @Autowired
    private ModelMapper mapper;

    @Operation(summary = "Get profiles", description = "Get all profiles")

    @GetMapping("/postulants/{postulantId}/profiles")
    public Page<ProfessionalProfileResource> getAllProfessionalProfileByPostulantId(@PathVariable Long postulantId,
            Pageable pageable) {
        Page<ProfessionalProfile> professionalprofilePage = professionalprofileService
                .getAllProfessionalProfileByPostulantId(postulantId, pageable);
        List<ProfessionalProfileResource> resources = professionalprofilePage.getContent().stream().map(
                this::convertToResource).collect(Collectors.toList());
        return new PageImpl<>(resources, pageable, resources.size());
    }

    @Operation(summary = "Get profiles", description = "Get profiles by postulantId")

    @GetMapping("/postulants/{postulantId}/profiles/{profileId}")
    public ProfessionalProfileResource getProfessionalProfileByIdAndPostulantId(@PathVariable Long postulantId,
            @PathVariable Long profileId) {
        return convertToResource(
                professionalprofileService.getProfessionalProfileByIdAndPostulantId(postulantId, profileId));
    }

    @Operation(summary = "Post profiles", description = "Create profiles")

    @PostMapping("/postulants/{postulantId}/profiles")
    public ProfessionalProfileResource createProfessionalProfile(
            @PathVariable Long postulantId, @Valid @RequestBody SaveProfessionalProfileResource resource) {
        return convertToResource(
                professionalprofileService.createProfessionalProfile(postulantId, convertToEntity(resource)));
    }

    @Operation(summary = "Put profiles", description = "Update profiles")

    @PutMapping("/postulants/{postulantId}/profiles/{profileId}")
    public ProfessionalProfileResource updateProfessionalProfile(
            @PathVariable Long postulantId,
            @PathVariable Long profileId,
            @Valid @RequestBody SaveProfessionalProfileResource resource) {
        return convertToResource(professionalprofileService.updateProfessionalProfile(postulantId, profileId,
                convertToEntity(resource)));
    }

    @Operation(summary = "Delete profiles", description = "Delete profiles")

    @DeleteMapping("/postulants/{postulantId}/profiles/{profileId}")
    public ResponseEntity<?> deleteProfessionalProfile(
            @PathVariable Long postulantId,
            @PathVariable Long profileId) {
        return professionalprofileService.deleteProfessionalProfile(postulantId, profileId);
    }

    private ProfessionalProfile convertToEntity(SaveProfessionalProfileResource resource) {
        return mapper.map(resource, ProfessionalProfile.class);
    }

    private ProfessionalProfileResource convertToResource(ProfessionalProfile entity) {
        return mapper.map(entity, ProfessionalProfileResource.class);
    }

}
