package com.viqujob.jobagapi.enterprise.domain.service;

import com.viqujob.jobagapi.enterprise.domain.model.Sector;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.http.ResponseEntity;

public interface SectorService {
    Page<Sector> getAllSectors(Pageable pageable);

    Sector getSectorById(Long sectorId);

    Sector createSector(Sector sector);

    Sector updateSector(Long sectorId, Sector sectorRequest);

    ResponseEntity<?> deleteSector(Long sectorId);

}
