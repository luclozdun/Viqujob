package com.viqujob.jobagapi.result.controller;

import io.swagger.v3.oas.annotations.Operation;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.Pageable;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;

import com.viqujob.jobagapi.result.domain.model.PostulantJob;
import com.viqujob.jobagapi.result.domain.service.PostulantJobService;
import com.viqujob.jobagapi.result.resource.PostulantJobResource;
import com.viqujob.jobagapi.result.resource.SavePostulantJobResource;

import java.util.List;
import java.util.stream.Collectors;

@RestController
public class PostulantJobController {
    @Autowired
    private PostulantJobService postulantJobService;
    @Autowired
    private ModelMapper mapper;

    @Operation(summary = "Postulant Jobs", description = "Create postulantjobs")
    @PostMapping("/postulants/{postulantId}/joboffers/{jobofferId}/postulantjobs")
    public PostulantJobResource createJobOffer(
            @PathVariable Long postulantId,
            @PathVariable Long jobofferId,
            @Valid @RequestBody SavePostulantJobResource resource) {
        return convertToResource(
                postulantJobService.createPostulantJob(postulantId, jobofferId, convertToEntity(resource)));
    }

    @Operation(summary = "Put Postulant Jobs", description = "Update postulantjobs")
    @PutMapping("/postulant/{postulantId}/joboffers/{jobofferId}/postulantjobs")
    public PostulantJobResource updatePostulantJob(
            @PathVariable Long postulantId,
            @PathVariable Long jobofferId,
            @Valid @RequestBody SavePostulantJobResource resource) {
        return convertToResource(
                postulantJobService.updatePostulantJob(postulantId, jobofferId, convertToEntity(resource)));
    }

    @Operation(summary = "Delete postulant job by postulant ID and job offer ID", description = "Delete postulant job by postulant ID and job offer ID")
    @DeleteMapping("/postulants/{postulantId}/joboffers/{jobofferId}/postulantjobs")
    public ResponseEntity<?> deletePostulantJob(
            @PathVariable Long postulantId,
            @PathVariable Long jobofferId) {
        return postulantJobService.deletePostulantJob(postulantId, jobofferId);
    }

    @Operation(summary = "Get All Postulant Job", description = "Get All Postulant Job")
    @GetMapping("/postulantjobs")
    public Page<PostulantJobResource> getAllPostulantJob(Pageable pageable) {
        Page<PostulantJob> postulantJobPage = postulantJobService.getAllPostulantJob(pageable);
        List<PostulantJobResource> resources = postulantJobPage.getContent()
                .stream()
                .map(this::convertToResource)
                .collect(Collectors.toList());
        return new PageImpl<>(resources, pageable, resources.size());
    }

    @Operation(summary = "Get Postulant Job by Id", description = "Get Postulant Job by Id")
    @GetMapping("/postulantjobs/{postulantJobId}")
    public PostulantJobResource getPostulantJobById(
            @PathVariable Long postulantJobId) {
        return convertToResource(postulantJobService.getPostulantJobById(postulantJobId));
    }

    @Operation(summary = "Get Postulant Job", description = "Get all Postulant Job by postulant Id")
    @GetMapping("/postulants/{postulantId}/postulantjobs")
    public Page<PostulantJobResource> getAllPostulantJobByPostulantId(@PathVariable Long postulantId,
            Pageable pageable) {
        Page<PostulantJob> postulantJobPage = postulantJobService.getAllPostulantJobByPostulantId(postulantId,
                pageable);
        List<PostulantJobResource> resources = postulantJobPage.getContent()
                .stream()
                .map(this::convertToResource)
                .collect(Collectors.toList());
        return new PageImpl<>(resources, pageable, resources.size());
    }

    @Operation(summary = "Get Postulant Job", description = "Get all Postulant Job by postulant Id")
    @GetMapping("/joboffers/{jobofferId}/postulantjobs")
    public Page<PostulantJobResource> getAllPostulantJobByJobOfferId(
            @PathVariable Long jobofferId,
            Pageable pageable) {
        Page<PostulantJob> postulantJobPage = postulantJobService.getAllPostulantJobByJobOfferId(jobofferId, pageable);
        List<PostulantJobResource> resources = postulantJobPage.getContent()
                .stream()
                .map(this::convertToResource)
                .collect(Collectors.toList());
        return new PageImpl<>(resources, pageable, resources.size());
    }

    private PostulantJob convertToEntity(SavePostulantJobResource resource) {
        return mapper.map(resource, PostulantJob.class);
    }

    private PostulantJobResource convertToResource(PostulantJob entity) {
        return mapper.map(entity, PostulantJobResource.class);
    }
}
