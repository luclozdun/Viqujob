package com.viqujob.jobagapi.ability.controller;

import io.swagger.v3.oas.annotations.Operation;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.Pageable;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;

import com.viqujob.jobagapi.ability.domain.model.Studies;
import com.viqujob.jobagapi.ability.domain.service.StudiesService;
import com.viqujob.jobagapi.ability.resource.SaveStudiesResource;
import com.viqujob.jobagapi.ability.resource.StudiesResource;

import java.util.List;
import java.util.stream.Collectors;

@RestController
@RequestMapping("/studies")
public class StudiesController {
    @Autowired
    private StudiesService studiesService;
    @Autowired
    private ModelMapper mapper;

    @Operation(summary = "Update Studies", description = "Update Studies")
    @PutMapping("/{studyId}")
    public StudiesResource updateStudies(@PathVariable Long studyId, @Valid @RequestBody SaveStudiesResource resource) {
        Studies study = convertToEntity(resource);
        return convertToResource(studiesService.updateStudies(studyId, study));
    }

    @Operation(summary = "Get Studies", description = "Get All Studies")
    @GetMapping
    public Page<StudiesResource> getAllStudies(Pageable pageable) {
        Page<Studies> studiesPage = studiesService.getAllStudies(pageable);
        List<StudiesResource> resources = studiesPage.getContent()
                .stream()
                .map(this::convertToResource)
                .collect(Collectors.toList());
        return new PageImpl<>(resources, pageable, resources.size());
    }

    @Operation(summary = "Post Studies", description = "Create Studies")
    @PostMapping
    public StudiesResource createStudies(@Valid @RequestBody SaveStudiesResource resource) {
        Studies studies = convertToEntity(resource);
        return convertToResource(studiesService.createStudies(studies));
    }

    @Operation(summary = "Get Studies by Id", description = "Get Studies by Id")
    @GetMapping("/{studyId}")
    public StudiesResource getStudiesById(@PathVariable Long studyId) {
        return convertToResource(studiesService.getStudiesById(studyId));
    }

    @Operation(summary = "Delete Studies", description = "Delete Studies")
    @DeleteMapping("/{studyId}")
    public ResponseEntity<?> deleteStudies(@PathVariable Long studyId) {
        return studiesService.deleteStudies(studyId);
    }

    private Studies convertToEntity(SaveStudiesResource resource) {
        return mapper.map(resource, Studies.class);
    }

    private StudiesResource convertToResource(Studies entity) {
        return mapper.map(entity, StudiesResource.class);
    }

}
