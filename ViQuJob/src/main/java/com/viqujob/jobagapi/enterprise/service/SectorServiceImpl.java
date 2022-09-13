package com.viqujob.jobagapi.enterprise.service;

import com.viqujob.jobagapi.enterprise.domain.model.Sector;
import com.viqujob.jobagapi.enterprise.domain.repository.SectorRepository;
import com.viqujob.jobagapi.enterprise.domain.service.SectorService;
import com.viqujob.jobagapi.exception.ResourceNotFoundException;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

@Service
public class SectorServiceImpl implements SectorService {
    @Autowired
    private SectorRepository sectorRepository;

    @Override
    public Page<Sector> getAllSectors(Pageable pageable) {
        return sectorRepository.findAll(pageable);
    }

    @Override
    public Sector getSectorById(Long sectorId) {
        return sectorRepository.findById(sectorId)
                .orElseThrow(() -> new ResourceNotFoundException("Sector", "Id", sectorId));
    }

    @Override
    public Sector createSector(Sector sector) {
        return sectorRepository.save(sector);
    }

    @Override
    public Sector updateSector(Long sectorId, Sector sectorRequest) {
        Sector sector = sectorRepository.findById(sectorId)
                .orElseThrow(() -> new ResourceNotFoundException("Sector", "Id", sectorId));
        return sectorRepository.save(
                sector.setName(sectorRequest.getName())
                        .setDescription(sectorRequest.getDescription()));
    }

    @Override
    public ResponseEntity<?> deleteSector(Long sectorId) {
        Sector sector = sectorRepository.findById(sectorId)
                .orElseThrow(() -> new ResourceNotFoundException("Sector", "Id", sectorId));
        sectorRepository.delete(sector);
        return ResponseEntity.ok().build();
    }
}
