package com.viqujob.jobagapi.ability.controller;

import io.swagger.v3.oas.annotations.Operation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.Pageable;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.modelmapper.ModelMapper;

import javax.validation.Valid;

import com.viqujob.jobagapi.ability.domain.model.Skill;
import com.viqujob.jobagapi.ability.domain.service.SkillService;
import com.viqujob.jobagapi.ability.resource.SaveSkillResource;
import com.viqujob.jobagapi.ability.resource.SkillResource;

import java.util.List;
import java.util.stream.Collectors;

@RestController
@RequestMapping("")
public class SkillController {
    @Autowired
    private SkillService skillService;

    @Autowired
    private ModelMapper mapper;

    @Operation(summary = "Update Skill", description = "Create Skill")
    @PutMapping("/{skillId}")
    public SkillResource updateSector(@PathVariable Long skillId, @Valid @RequestBody SaveSkillResource resource) {
        Skill skill = convertToEntity(resource);
        return convertToResource(skillService.updateSkill(skillId, skill));
    }

    @Operation(summary = "Get Skills", description = "Get All Skills")
    @GetMapping
    public Page<SkillResource> getAllSkills(Pageable pageable) {
        Page<Skill> skillPage = skillService.getAllSkills(pageable);
        List<SkillResource> resources = skillPage.getContent()
                .stream()
                .map(this::convertToResource)
                .collect(Collectors.toList());

        return new PageImpl<>(resources, pageable, resources.size());
    }

    @Operation(summary = "Post Skill", description = "Post Skill")
    @PostMapping
    public SkillResource createSkill(@Valid @RequestBody SaveSkillResource resource) {
        Skill skill = convertToEntity(resource);
        return convertToResource(skillService.createSkill(skill));
    }

    @Operation(summary = "Get Skill By Id", description = "Get Skill")
    @GetMapping("/{skillId}}")
    public SkillResource getSkillById(@PathVariable Long skillId) {
        return convertToResource(skillService.getSkillById(skillId));
    }

    @Operation(summary = "Delete Skill", description = "Delete Skill")
    @DeleteMapping("/{skillId}}")

    public ResponseEntity<?> deleteSkill(@PathVariable Long skillId) {
        return skillService.deleteSkill(skillId);
    }

    private Skill convertToEntity(SaveSkillResource resource) {
        return mapper.map(resource, Skill.class);
    }

    private SkillResource convertToResource(Skill entity) {
        return mapper.map(entity, SkillResource.class);
    }

}
