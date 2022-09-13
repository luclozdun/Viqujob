package com.viqujob.jobagapi.enterprise.domain.service;

import com.viqujob.jobagapi.enterprise.domain.model.Company;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.http.ResponseEntity;

public interface CompanyService {

    Company createCompany(Long employeerId, Long sectorId, Company companyRequest);

    Company updateCompany(Long employeerId, Long sectorId, Company companyRequest);

    ResponseEntity<?> deleteCompany(Long employeerId, Long sectorId);

    Company getCompanyById(Long companyId);

    Company getCompanyByEmployeerIdAndSectorId(Long employeerId, Long sectorId);

    Page<Company> getAllCompanyByEmployeerId(Long employeerId, Pageable pageable);

    Page<Company> getAllCompany(Pageable pageable);
}
