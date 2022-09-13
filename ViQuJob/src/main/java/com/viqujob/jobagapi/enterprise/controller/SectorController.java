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

import com.viqujob.jobagapi.enterprise.domain.model.Sector;
import com.viqujob.jobagapi.enterprise.domain.service.SectorService;
import com.viqujob.jobagapi.enterprise.resource.SaveSectorResource;
import com.viqujob.jobagapi.enterprise.resource.SectorResource;

import java.util.List;
import java.util.stream.Collectors;

@RestController
@RequestMapping("/sectors")
public class SectorController {
    @Autowired
    private SectorService sectorService;

    @Autowired
    private ModelMapper mapper;

    @Operation(summary = "Get Sectors", description = "Get All Sectors")
    @GetMapping
    public Page<SectorResource> getAllSectors(Pageable pageable) {
        Page<Sector> sectorPage = sectorService.getAllSectors(pageable);
        List<SectorResource> resources = sectorPage.getContent()
                .stream()
                .map(this::convertToResource)
                .collect(Collectors.toList());

        return new PageImpl<>(resources, pageable, resources.size());
    }

    @Operation(summary = "Post Sectors", description = "Create Sectors")
    @PostMapping
    public SectorResource createSector(@Valid @RequestBody SaveSectorResource resource) {
        Sector sector = convertToEntity(resource);
        return convertToResource(sectorService.createSector(sector));
    }

    @Operation(summary = "Update Sector", description = "Update Sector")
    @PutMapping("/{sectorId}")
    public SectorResource updateSector(@PathVariable Long sectorId, @Valid @RequestBody SaveSectorResource resource) {
        Sector sector = convertToEntity(resource);
        return convertToResource(sectorService.updateSector(sectorId, sector));
    }

    @Operation(summary = "Get SectorsById", description = "Get SectorsById")
    @GetMapping("/{sectorId}")
    public SectorResource getSectorById(@PathVariable(name = "id") Long sectorId) {
        return convertToResource(sectorService.getSectorById(sectorId));
    }

    @Operation(summary = "Delete Sector By Id", description = "DeleteSectorById")
    @DeleteMapping("/{sectorId}")
    public ResponseEntity<?> deleteSector(@PathVariable Long sectorId) {
        return sectorService.deleteSector(sectorId);
    }

    private Sector convertToEntity(SaveSectorResource resource) {
        return mapper.map(resource, Sector.class);
    }

    private SectorResource convertToResource(Sector entity) {
        return mapper.map(entity, SectorResource.class);
    }

}
