package com.viqujob.jobagapi.security.controller;

import java.util.List;
import java.util.stream.Collectors;

import javax.validation.Valid;

import com.viqujob.jobagapi.security.domain.model.Employer;
import com.viqujob.jobagapi.security.domain.service.EmployerService;
import com.viqujob.jobagapi.security.resource.EmployerResource;
import com.viqujob.jobagapi.security.resource.SaveEmployerResource;

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
@RequestMapping("/employeers")
public class EmployerController {
    @Autowired
    private EmployerService employeerService;

    @Autowired
    private ModelMapper mapper;

    @Operation(summary = "Get Employeers", description = "Get All Employeers")
    @GetMapping
    public Page<EmployerResource> getAllEmployeers(Pageable pageable) {
        Page<Employer> employeerPage = employeerService.getAllEmployeers(pageable);
        List<EmployerResource> resources = employeerPage.getContent()
                .stream()
                .map(this::convertToResource)
                .collect(Collectors.toList());

        return new PageImpl<>(resources, pageable, resources.size());
    }

    @Operation(summary = "Post Employeers", description = "Create Employeers")
    @PostMapping
    public EmployerResource createEmployeer(@Valid @RequestBody SaveEmployerResource resource) {
        Employer employeer = convertToEntity(resource);
        return convertToResource(employeerService.createEmployeer(employeer));
    }

    @Operation(summary = "Get EmployeersById", description = "Get EmployeersById")
    @GetMapping("/{id}")
    public EmployerResource getEmployeerById(@PathVariable(name = "id") Long employeerId) {
        return convertToResource(employeerService.getEmployeerById(employeerId));
    }

    @Operation(summary = "Delete Employeer By Id", description = "DeleteEmployeerById")
    @DeleteMapping("/{postId}")
    public ResponseEntity<?> deleteEmployeer(@PathVariable Long postId) {
        return employeerService.deleteEmployeer(postId);
    }

    private Employer convertToEntity(SaveEmployerResource resource) {
        return mapper.map(resource, Employer.class);
    }

    private EmployerResource convertToResource(Employer entity) {
        return mapper.map(entity, EmployerResource.class);
    }
}
