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

import com.viqujob.jobagapi.ability.domain.model.Languages;
import com.viqujob.jobagapi.ability.domain.service.LanguagesService;
import com.viqujob.jobagapi.ability.resource.LanguagesResource;
import com.viqujob.jobagapi.ability.resource.SaveLanguagesResource;

import java.util.List;
import java.util.stream.Collectors;

@RestController
@RequestMapping("/languages")
public class LanguagesController {
    @Autowired
    private LanguagesService languagesService;
    @Autowired
    private ModelMapper mapper;

    @Operation(summary = "Update Languages", description = "Update Languages")
    @PutMapping("/{languagesId}")
    public LanguagesResource updateLanguages(@PathVariable Long languagesId,
            @Valid @RequestBody SaveLanguagesResource resource) {
        Languages languages = convertToEntity(resource);
        return convertToResource(languagesService.updateLanguages(languagesId, languages));
    }

    @Operation(summary = "Get Languages", description = "Get All Languages")
    @GetMapping
    public Page<LanguagesResource> getAllLanguages(Pageable pageable) {
        Page<Languages> languagesPage = languagesService.getAllLanguages(pageable);
        List<LanguagesResource> resources = languagesPage.getContent()
                .stream()
                .map(this::convertToResource)
                .collect(Collectors.toList());
        return new PageImpl<>(resources, pageable, resources.size());
    }

    @Operation(summary = "Post Languages", description = "Post Languages")
    @PostMapping
    public LanguagesResource createLanguages(@Valid @RequestBody SaveLanguagesResource resource) {
        Languages languages = convertToEntity(resource);
        return convertToResource(languagesService.createLanguages(languages));
    }

    @Operation(summary = "Get Languages by Id", description = "Get Languages by Id")
    @GetMapping("/{languagesId}")
    public LanguagesResource getLanguaesById(@PathVariable Long languagesId) {
        return convertToResource(languagesService.getLanguagesById(languagesId));
    }

    @Operation(summary = "Delete Language", description = "Delete Language")
    @DeleteMapping("/{languagesId}")
    public ResponseEntity<?> deleteLanguage(@PathVariable Long languagesId) {
        return languagesService.deleteLanguages(languagesId);
    }

    private Languages convertToEntity(SaveLanguagesResource resource) {
        return mapper.map(resource, Languages.class);
    }

    private LanguagesResource convertToResource(Languages entity) {
        return mapper.map(entity, LanguagesResource.class);
    }
}
