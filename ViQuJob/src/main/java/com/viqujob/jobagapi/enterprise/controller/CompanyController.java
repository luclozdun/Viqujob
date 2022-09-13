package com.viqujob.jobagapi.enterprise.controller;

import io.swagger.v3.oas.annotations.Operation;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.Pageable;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;

import com.viqujob.jobagapi.enterprise.domain.model.Company;
import com.viqujob.jobagapi.enterprise.domain.service.CompanyService;
import com.viqujob.jobagapi.enterprise.resource.CompanyResource;
import com.viqujob.jobagapi.enterprise.resource.SaveCompanyResource;

import java.util.List;
import java.util.stream.Collectors;

@RestController
public class CompanyController {
    @Autowired
    private CompanyService companyService;

    @Autowired
    private ModelMapper mapper;

    @Operation(summary = "Post companys", description = "Create companys by employeer Id")
    @PostMapping("/employeers/{employeerId}/sector/{sectorId}/companys")
    public CompanyResource createCompany(
            @PathVariable Long employeerId,
            @PathVariable Long sectorId,
            @Valid @RequestBody SaveCompanyResource resource) {
        return convertToResource(companyService.createCompany(employeerId, sectorId, convertToEntity(resource)));
    }

    @Operation(summary = "Update Company by Employeer Id and Sector Id", description = "Update Company by Employeer Id and Sector Id")
    @PutMapping("/employeers/{employeerId}/sector/{sectorId}/companys")
    public CompanyResource updateCompany(
            @PathVariable Long employeerId,
            @PathVariable Long sectorId,
            @Valid @RequestBody SaveCompanyResource resource) {
        return convertToResource(companyService.updateCompany(employeerId, sectorId, convertToEntity(resource)));
    }

    @Operation(summary = "Delete Company by Employeer Id and Sector Id", description = "Delete Company by Employeer Id and Sector Id")
    @DeleteMapping("/employeers/{employeerId}/sector/{sectorId}/companys")
    public ResponseEntity<?> deleteCompany(
            @PathVariable Long employeerId,
            @PathVariable Long sectorId) {
        return companyService.deleteCompany(employeerId, sectorId);
    }

    @Operation(summary = "Get All Company", description = "Get All Company")
    @GetMapping("/companys")
    public Page<CompanyResource> getAllCompany(Pageable pageable) {
        Page<Company> companyPage = companyService.getAllCompany(pageable);
        List<CompanyResource> resources = companyPage.getContent()
                .stream()
                .map(this::convertToResource)
                .collect(Collectors.toList());
        return new PageImpl<>(resources, pageable, resources.size());
    }

    @Operation(summary = "Get Company by Id", description = "Get Company by Id")
    @GetMapping("/companys/{companyId}")
    public CompanyResource getInterviewById(
            @PathVariable Long companyId) {
        return convertToResource(companyService.getCompanyById(companyId));
    }

    @Operation(summary = "Get Company by Employeer Id And Sector Id", description = "Get Company by Employeer Id And Sector Id")
    @GetMapping("/employeers/{employeerId}/sector/{sectorId}/companys")
    public CompanyResource getCompanyByEmployeerIdAndSectorId(
            @PathVariable Long employeerId,
            @PathVariable Long sectorId) {
        return convertToResource(companyService.getCompanyByEmployeerIdAndSectorId(employeerId, sectorId));
    }

    @Operation(summary = "Get All Company By Employeer Id", description = "Get All Company By Employeer Id")
    @GetMapping("/employeers/{employeerId}/companys")
    public Page<CompanyResource> getAllCompanyByEmployeerId(
            @PathVariable Long employeerId,
            Pageable pageable) {
        Page<Company> companyPage = companyService.getAllCompanyByEmployeerId(employeerId, pageable);
        List<CompanyResource> resources = companyPage.getContent()
                .stream()
                .map(this::convertToResource)
                .collect(Collectors.toList());
        return new PageImpl<>(resources, pageable, resources.size());
    }

    private Company convertToEntity(SaveCompanyResource resource) {
        return mapper.map(resource, Company.class);
    }

    private CompanyResource convertToResource(Company entity) {
        return mapper.map(entity, CompanyResource.class);
    }
}
