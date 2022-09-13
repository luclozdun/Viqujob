package com.viqujob.jobagapi.security.controller;

import java.util.List;
import java.util.stream.Collectors;

import javax.validation.Valid;

import com.viqujob.jobagapi.security.domain.model.Postulant;
import com.viqujob.jobagapi.security.domain.service.PostulantService;
import com.viqujob.jobagapi.security.resource.PostulantResource;
import com.viqujob.jobagapi.security.resource.SavePostulantResource;

import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.Pageable;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import io.swagger.v3.oas.annotations.Operation;

@RestController
@RequestMapping("/postulants")
public class PostulantController {

    @Autowired
    private PostulantService postulantService;

    @Autowired
    private ModelMapper mapper;

    @Operation(summary = "Get Postulants", description = "Get All Postulants")
    @GetMapping
    public Page<PostulantResource> getAllPostulants(Pageable pageable) {
        Page<Postulant> postulantPage = postulantService.getAllPostulants(pageable);
        List<PostulantResource> resources = postulantPage.getContent()
                .stream()
                .map(this::convertToResource)
                .collect(Collectors.toList());

        return new PageImpl<>(resources, pageable, resources.size());
    }

    @Operation(summary = "Post Postulants", description = "Create Postulants")
    @PostMapping
    public PostulantResource createPostulant(@Valid @RequestBody SavePostulantResource resource) {
        Postulant postulant = convertToEntity(resource);
        return convertToResource(postulantService.createPostulant(postulant));
    }

    @Operation(summary = "Get PostulantsById", description = "Get PostulantsById")
    @GetMapping("/{id}")
    public PostulantResource getPostulantById(@PathVariable(name = "id") Long postulantId) {
        return convertToResource(postulantService.getPostulantById(postulantId));
    }

    @Operation(summary = "Delete Postulant By Id", description = "DeletePostulantById")
    @DeleteMapping("/{id}")
    public ResponseEntity<?> deletePostulant(@PathVariable(name = "id") Long postulantId) {
        return postulantService.deletePostulant(postulantId);
    }

    private Postulant convertToEntity(SavePostulantResource resource) {
        return mapper.map(resource, Postulant.class);
    }

    private PostulantResource convertToResource(Postulant entity) {
        return mapper.map(entity, PostulantResource.class);
    }
}
