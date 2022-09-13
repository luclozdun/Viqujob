package com.viqujob.jobagapi.enterprise.service;

import com.viqujob.jobagapi.enterprise.domain.model.Company;
import com.viqujob.jobagapi.enterprise.domain.repository.CompanyRepository;
import com.viqujob.jobagapi.enterprise.domain.repository.SectorRepository;
import com.viqujob.jobagapi.enterprise.domain.service.CompanyService;
import com.viqujob.jobagapi.exception.ResourceNotFoundException;
import com.viqujob.jobagapi.security.domain.repository.EmployerRepository;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

@Service
public class CompanyServiceImpl implements CompanyService {

    @Autowired
    private EmployerRepository employeerRepository;

    @Autowired
    private SectorRepository sectorRepository;

    @Autowired
    private CompanyRepository companyRepository;

    @Override
    public Company createCompany(Long employeerId, Long sectorId, Company company) {
        if (companyRepository.existsByEmployeerId(employeerId))
            throw new ResourceNotFoundException("La compania ya fue registrado por el empleador");

        if (!employeerRepository.existsById(employeerId))
            throw new ResourceNotFoundException("Employeer", "Id", employeerId);

        else if (!sectorRepository.existsById(sectorId))
            throw new ResourceNotFoundException("Sector", "Id", sectorId);

        return employeerRepository.findById(employeerId).map(employeer -> {
            company.setEmployeer(employeer);
            sectorRepository.findById(sectorId).map(sector -> {
                company.setSector(sector);
                return companyRepository.save(company);
            }).orElseThrow(() -> new ResourceNotFoundException("Employeer Id" + employeer));
            return companyRepository.save(company);
        }).orElseThrow(() -> new ResourceNotFoundException("Sector Id" + sectorId));
    }

    @Override
    public Company updateCompany(Long employeerId, Long sectorId, Company companyRequest) {

        if (!employeerRepository.existsById(employeerId))
            throw new ResourceNotFoundException("Employeer", "Id", employeerId);

        else if (!sectorRepository.existsById(sectorId))
            throw new ResourceNotFoundException("Sector", "Id", sectorId);

        return companyRepository.findByEmployeerIdAndSectorId(employeerId, sectorId).map(company -> {
            company.setName(companyRequest.getName())
                    .setDireccion(companyRequest.getDireccion())
                    .setRuc(companyRequest.getRuc())
                    .setLogo(companyRequest.getLogo())
                    .setDescription(companyRequest.getDescription());
            return companyRepository.save(company);
        }).orElseThrow(() -> new ResourceNotFoundException("Employeer Id" + employeerId + "Sector Id" + sectorId));
    }

    @Override
    public ResponseEntity<?> deleteCompany(Long employeerId, Long sectorId) {

        if (!employeerRepository.existsById(employeerId))
            throw new ResourceNotFoundException("Employeer", "Id", employeerId);

        else if (!sectorRepository.existsById(sectorId))
            throw new ResourceNotFoundException("Sector", "Id", sectorId);

        return companyRepository.findByEmployeerIdAndSectorId(employeerId, sectorId).map(company -> {
            companyRepository.delete(company);
            return ResponseEntity.ok().build();
        }).orElseThrow(() -> new ResourceNotFoundException("Employeer Id" + employeerId + "Sector Id" + sectorId));
    }

    @Override
    public Company getCompanyById(Long companyId) {
        return companyRepository.findById(companyId)
                .orElseThrow(() -> new ResourceNotFoundException("Company", "Id", companyId));
    }

    @Override
    public Company getCompanyByEmployeerIdAndSectorId(Long employeerId, Long sectorId) {
        return companyRepository.findByEmployeerIdAndSectorId(employeerId, sectorId)
                .orElseThrow(() -> new ResourceNotFoundException("Employeer", "Id", employeerId));
    }

    @Override
    public Page<Company> getAllCompanyByEmployeerId(Long employeerId, Pageable pageable) {
        return companyRepository.findByEmployeerId(employeerId, pageable);
    }

    @Override
    public Page<Company> getAllCompany(Pageable pageable) {
        return companyRepository.findAll(pageable);
    }
}
