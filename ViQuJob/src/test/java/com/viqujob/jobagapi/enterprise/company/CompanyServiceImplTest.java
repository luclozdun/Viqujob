package com.viqujob.jobagapi.enterprise.company;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.Mockito.doNothing;
import static org.mockito.Mockito.when;

import java.util.Date;
import java.util.Optional;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Spy;
import org.springframework.boot.test.autoconfigure.jdbc.AutoConfigureTestDatabase;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.http.HttpStatus;

import com.viqujob.jobagapi.enterprise.domain.model.Company;
import com.viqujob.jobagapi.enterprise.domain.model.Sector;
import com.viqujob.jobagapi.enterprise.domain.repository.CompanyRepository;
import com.viqujob.jobagapi.enterprise.domain.repository.SectorRepository;
import com.viqujob.jobagapi.enterprise.service.CompanyServiceImpl;
import com.viqujob.jobagapi.exception.ResourceNotFoundException;
import com.viqujob.jobagapi.security.domain.model.Employer;
import com.viqujob.jobagapi.security.domain.repository.EmployerRepository;

import static org.assertj.core.api.Assertions.*;

@DataJpaTest
@AutoConfigureTestDatabase(replace = AutoConfigureTestDatabase.Replace.NONE)
public class CompanyServiceImplTest {

    @Spy
    private EmployerRepository employeerRepository;

    @Spy
    private SectorRepository sectorRepository;

    @Spy
    private CompanyRepository companyRepository;

    @InjectMocks
    private CompanyServiceImpl companyServiceImpl;

    Company company;
    Employer employer;
    Sector sector;

    @BeforeEach
    public void setup() {

        sector = new Sector();
        sector.setDescription("description");
        sector.setId(48L);
        sector.setName("name");

        employer = new Employer();
        employer.setId(123L);

        company = new Company();

        company.setCreatedAt(new Date());
        company.setDescription("description");
        company.setDireccion("direccion");
        company.setEmployeer(employer);
        company.setId(2L);
        company.setLogo("logo");
        company.setName("name");
        company.setRuc(694201337L);
        company.setSector(sector);
        company.setUpdateAt(new Date());
    }

    @Test
    @DisplayName("When Find By Id Then Return Company")
    public void WhenFindByIdThenReturnCompany() {
        // arrange
        when(companyRepository.findById(69L)).thenReturn(Optional.of(company));

        // act
        Company response = companyServiceImpl.getCompanyById(69L);

        // assert
        assertEquals(2L, response.getId());
    }

    @Test
    @DisplayName("When Save A Company")
    public void WhenSaveACompany() {
        // arrange

        when(companyRepository.existsByEmployeerId(420L)).thenReturn(false);
        when(employeerRepository.existsById(420L)).thenReturn(true);
        when(sectorRepository.existsById(52L)).thenReturn(true);

        when(employeerRepository.findById(420L)).thenReturn(Optional.of(employer));
        when(sectorRepository.findById(52L)).thenReturn(Optional.of(sector));
        when(companyRepository.save(company)).thenReturn(company);

        // act
        Company response = companyServiceImpl.createCompany(420L, 52L, company);

        // assert
        assertEquals(2L, response.getId());
    }

    @Test
    @DisplayName("When Save A Company But Company Exist")
    public void WhenSaveACompanyButCompanyNotExist() {
        // arrange
        when(companyRepository.existsByEmployeerId(420L)).thenReturn(true);
        when(employeerRepository.existsById(420L)).thenReturn(true);
        when(sectorRepository.existsById(52L)).thenReturn(true);

        when(employeerRepository.findById(420L)).thenReturn(Optional.of(employer));
        when(sectorRepository.findById(52L)).thenReturn(Optional.of(sector));
        when(companyRepository.save(company)).thenReturn(company);

        // act
        String message = "La compania ya fue registrado por el empleador";
        Throwable exception = catchThrowable(() -> {
            companyServiceImpl.createCompany(420L, 52L, company);
        });
        // assert
        assertThat(exception).isInstanceOf(ResourceNotFoundException.class).hasMessage(message);
    }

    @Test
    @DisplayName("When Delete A Company")
    public void WhenDeleteACompany() {
        // arrange
        when(employeerRepository.existsById(420L)).thenReturn(true);
        when(sectorRepository.existsById(52L)).thenReturn(true);
        doNothing().when(companyRepository).delete(company);
        when(companyRepository.findByEmployeerIdAndSectorId(420L, 52L)).thenReturn(Optional.of(company));

        // act
        HttpStatus response = companyServiceImpl.deleteCompany(420L, 52L)
                .getStatusCode();

        // assert
        assertEquals(HttpStatus.OK, response.OK);
    }

    @Test
    @DisplayName("When Delete A Company But Company Not Exist")
    public void WhenDeleteACompanyButCompanyNotExist() {
        // arrange
        when(employeerRepository.existsById(420L)).thenReturn(true);
        when(sectorRepository.existsById(52L)).thenReturn(true);
        doNothing().when(companyRepository).delete(company);
        when(companyRepository.findByEmployeerIdAndSectorId(421L, 52L)).thenReturn(Optional.of(company));

        // act
        String message = "Employeer Id420Sector Id52";
        Throwable exception = catchThrowable(() -> {
            companyServiceImpl.deleteCompany(420L, 52L);
        });
        // assert
        assertThat(exception).isInstanceOf(ResourceNotFoundException.class).hasMessage(message);
    }

    /*
     * @Test
     * 
     * @DisplayName("When Update A Company")
     * public void WhenUpdateACompany() {
     * // arrange
     * when(companyRepository.save(company)).thenReturn(company);
     * when(companyRepository.findById(2L)).thenReturn(Optional.of(company));
     * Company companyRequest = new Company();
     * companyRequest.setDescription("sample text2");
     * companyRequest.setId(420L);
     * companyRequest.setName("Company 420");
     * Employer testEmployer = new Employer();
     * testEmployer.setId(420L);
     * Sector testSector = new Sector();
     * testSector.setId(52L);
     * // act
     * Company response = companyServiceImpl.updateCompany(testEmployer.getId(),
     * testSector.getId(), companyRequest);
     * // assert
     * assertEquals(420L, response.getId());
     * 
     * }
     * 
     * @Test
     * 
     * @DisplayName("When Update A Company But Company Not Exist")
     * public void WhenUpdateACompanyButCompanyNotExist() {
     * // arrange
     * when(companyRepository.findById(69L)).thenReturn(Optional.of(company));
     * 
     * // act
     * String message = "Resource Company not found for Id with value 69";
     * Throwable exception = catchThrowable(() -> {
     * companyServiceImpl.getCompanyById(1337L);
     * });
     * // assert
     * assertThat(exception).isInstanceOf(ResourceNotFoundException.class).
     * hasMessage(message);
     * }
     */
}