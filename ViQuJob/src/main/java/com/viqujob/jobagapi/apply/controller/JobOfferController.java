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

import com.viqujob.jobagapi.apply.domain.model.JobOffer;
import com.viqujob.jobagapi.apply.domain.service.JobOfferService;
import com.viqujob.jobagapi.apply.resource.JobOfferResource;
import com.viqujob.jobagapi.apply.resource.SaveJobOfferResource;

import java.util.List;
import java.util.stream.Collectors;

@RestController
public class JobOfferController {

    @Autowired
    private JobOfferService jobOfferService;
    @Autowired
    private ModelMapper mapper;

    @Operation(summary = "Get joboffers", description = "Get all joboffers")

    @GetMapping("/employeers/{employeerId}/joboffers")
    public Page<JobOfferResource> getAllJobOffers(
            @PathVariable Long employeerId,
            Pageable pageable) {
        Page<JobOffer> jobOfferPage = jobOfferService.getAllJobOffersByEmployeerId(employeerId, pageable);
        List<JobOfferResource> resources = jobOfferPage.getContent()
                .stream()
                .map(this::convertToResource)
                .collect(Collectors.toList());
        return new PageImpl<>(resources, pageable, resources.size());
    }

    @Operation(summary = "Get joboffers", description = "Get joboffers by employeerId")
    @GetMapping("/jobOffers/{jobOfferId}/employeers/{employeerId}")
    public JobOfferResource getJobOfferByIdAndEmployeerId(
            @PathVariable Long employeerId,
            @PathVariable Long jobOfferId) {
        return convertToResource(jobOfferService.getJobOfferByIdAndEmployeerId(jobOfferId, employeerId));
    }

    @Operation(summary = "Post joboffers", description = "Create joboffers")
    @PostMapping("/employeers/{employeerId}/joboffers")
    public JobOfferResource createJobOffer(
            @PathVariable Long employeerId,
            @Valid @RequestBody SaveJobOfferResource resource) {
        return convertToResource(jobOfferService.createJobOffer(employeerId, convertToEntity(resource)));
    }

    @Operation(summary = "Put joboffers", description = "Update joboffers")

    @PutMapping("/employeers/{employeerId}/jobOffers/{jobOfferId}")
    public JobOfferResource updateFarmland(
            @PathVariable Long employeerId,
            @PathVariable Long jobOfferId,
            @Valid @RequestBody SaveJobOfferResource resource) {
        return convertToResource(jobOfferService.updateJobOffer(employeerId, jobOfferId, convertToEntity(resource)));
    }

    @Operation(summary = "Delete joboffers", description = "Delete joboffer by employeer Id")

    @DeleteMapping("/employeers/{employeerId}/jobOffers/{jobOfferId}")
    public ResponseEntity<?> deleteJobOffer(
            @PathVariable Long employeerId,
            @PathVariable Long jobOfferId) {
        return jobOfferService.deleteJobOffer(employeerId, jobOfferId);
    }

    @Operation(summary = "Get All Job Offer", description = "Get All Job Offer")
    @GetMapping("/jobOffers")
    public Page<JobOfferResource> getAllJobOffer(Pageable pageable) {
        Page<JobOffer> jobOfferPage = jobOfferService.getAllJobOffer(pageable);
        List<JobOfferResource> resources = jobOfferPage.getContent()
                .stream()
                .map(this::convertToResource)
                .collect(Collectors.toList());
        return new PageImpl<>(resources, pageable, resources.size());
    }

    @Operation(summary = "Get Job Offer by Id", description = "Get Job Offer by Id")
    @GetMapping("/jobOffers/{jobOfferId}")
    public JobOfferResource getJobOfferById(
            @PathVariable Long jobOfferId) {
        return convertToResource(jobOfferService.getJobOfferById(jobOfferId));
    }

    /* abr */
    private JobOffer convertToEntity(SaveJobOfferResource resource) {
        return mapper.map(resource, JobOffer.class);
    }

    private JobOfferResource convertToResource(JobOffer entity) {
        return mapper.map(entity, JobOfferResource.class);
    }
}
