package com.viqujob.jobagapi.apply.controller;

import io.swagger.v3.oas.annotations.Operation;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.Pageable;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.stream.Collectors;

import com.viqujob.jobagapi.apply.domain.model.ProfessionalProfile;
import com.viqujob.jobagapi.apply.domain.service.ProfessionalProfileService;
import com.viqujob.jobagapi.apply.resource.ProfessionalProfileResource;

@RestController
public class ProfessionalProfileSkillsController {

    @Autowired
    private ModelMapper mapper;

    @Autowired
    private ProfessionalProfileService professionalprofileService;

    @Operation(summary = "Assign Skill to Profile", description = "Establishes association between Skills and Profiles")

    @PostMapping("/profiles/{profileId}/skills/{skillId}")
    public ProfessionalProfileResource assignProfessionalProfileSkill(
            @PathVariable Long profileId,
            @PathVariable Long skillId) {
        return convertToResource(professionalprofileService.assignProfessionalProfileSkill(profileId, skillId));
    }

    @Operation(summary = "Remove assignment between Skill and Profile", description = "Ends association between Profile and Skill")
    @DeleteMapping("/profiles/{profileId}/skills/{skillId}")
    public ProfessionalProfileResource unassignProfessionalProfileSkill(
            @PathVariable Long profileId,
            @PathVariable Long skillId) {
        return convertToResource(professionalprofileService.unassignProfessionalProfileSkill(profileId, skillId));
    }

    @Operation(summary = "List assignment between Skill and Profile", description = "List association between Profile and Skill")
    @GetMapping("/skills/{skillId}/profiles")
    public Page<ProfessionalProfileResource> getAllProfessionalProfileBySkillsId(
            @PathVariable Long skillId,
            Pageable pageable) {
        Page<ProfessionalProfile> postsPage = professionalprofileService.getAllProfessionalProfileBySkillId(skillId,
                pageable);
        List<ProfessionalProfileResource> resources = postsPage.getContent().stream()
                .map(this::convertToResource).collect(Collectors.toList());
        return new PageImpl<>(resources, pageable, resources.size());
    }

    /*
     * private ProfessionalProfile convertToEntity(SaveProfessionalProfileResource
     * resource) {
     * return mapper.map(resource, ProfessionalProfile.class);
     * }
     */

    private ProfessionalProfileResource convertToResource(ProfessionalProfile entity) {
        return mapper.map(entity, ProfessionalProfileResource.class);
    }
}
