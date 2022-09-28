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
import com.viqujob.jobagapi.enterprise.service.CompanyServiceImpl;
import com.viqujob.jobagapi.exception.ResourceNotFoundException;
import com.viqujob.jobagapi.security.domain.model.Employer;

import static org.assertj.core.api.Assertions.*;

@DataJpaTest
@AutoConfigureTestDatabase(replace = AutoConfigureTestDatabase.Replace.NONE)
public class CompanyServiceImplTest {
    
    @Spy
    private CompanyRepository companyRepository;

    @InjectMocks
    private CompanyServiceImpl companyServiceImpl;
    
    Company company;

    @BeforeEach
    public void setup(){
        Date date = new Date();

        Sector sector = new Sector();
        
        Employer employer = new Employer();
        
        company = new Company();
        
        company.setCreatedAt(date);
        company.setDescription("description");
        company.setDireccion("direccion");
        company.setEmployeer(employer);
        company.setId(2L);
        company.setLogo("logo");
        company.setName("name");
        company.setRuc(694201337L);
        company.setSector(sector);
        company.setUpdateAt(date);
    }

    @Test
    @DisplayName("When Find By Id Then Return Company")
    public void WhenFindByIdThenReturnCompany(){
        //arrange
        when(companyRepository.findById(69L)).thenReturn(Optional.of(company));

        //act
        Company response = companyServiceImpl.getCompanyById(69L);

        //assert
        assertEquals(2L, response.getId());
    }

    @Test
    @DisplayName("When Find By Id But Section Not Exist")
    public void WhenFindByIdButSectionNotExist(){
        //arrange
        when(companyRepository.findById(69L)).thenReturn(Optional.of(company));

        //act
        String message = "Resource Company not found for Id with value 69";
        Throwable exception = catchThrowable(()->{
            companyServiceImpl.getCompanyById(69L);
        });
        //assert
        assertThat(exception).isInstanceOf(ResourceNotFoundException.class).hasMessage(message);
    }

    @Test
    @DisplayName("When Save A Company")
    public void WhenSaveACompany(){
        //arrange
        when(companyRepository.save(company)).thenReturn(company);
        Employer testEmployer = new Employer();
        testEmployer.setId(420L);
        Sector testSector = new Sector();
        testSector.setId(52L);

        //act
        Company response = companyServiceImpl.createCompany(testEmployer.getId(),testSector.getId(), company);

        //assert
        assertEquals(2L, response.getId());
    }

    @Test
    @DisplayName("When Save A Company But Company Not Exist")
    public void WhenSaveACompanyButCompanyNotExist(){
        //arrange
        when(companyRepository.findById(69L)).thenReturn(Optional.of(company));

        //act
        String message = "Resource Company not found for Id with value 69";
        Throwable exception = catchThrowable(()->{
            companyServiceImpl.getCompanyById(1337L);
        });
        //assert
        assertThat(exception).isInstanceOf(ResourceNotFoundException.class).hasMessage(message);
    }

    @Test
    @DisplayName("When Delete A Company")
    public void WhenDeleteACompany(){
        //arrange
        doNothing().when(companyRepository).delete(company);
        when(companyRepository.findById(2L)).thenReturn(Optional.of(company));
        Employer testEmployer = new Employer();
        testEmployer.setId(420L);
        Sector testSector = new Sector();
        testSector.setId(52L);

        //act
        HttpStatus response = companyServiceImpl.deleteCompany(testEmployer.getId(),testSector.getId()).getStatusCode();

        //assert
        assertEquals(HttpStatus.OK,response.OK);
    }

    @Test
    @DisplayName("When Delete A Company But Company Not Exist")
    public void WhenDeleteACompanyButCompanyNotExist(){
        //arrange
        when(companyRepository.findById(69L)).thenReturn(Optional.of(company));

        //act
        String message = "Resource Company not found for Id with value 69";
        Throwable exception = catchThrowable(()->{
            companyServiceImpl.getCompanyById(1337L);
        });
        //assert
        assertThat(exception).isInstanceOf(ResourceNotFoundException.class).hasMessage(message);
    }

    @Test
    @DisplayName("When Update A Company")
    public void WhenUpdateACompany(){
        //arrange
        when(companyRepository.save(company)).thenReturn(company);
        when(companyRepository.findById(2L)).thenReturn(Optional.of(company));
        Company companyRequest = new Company();
        companyRequest.setDescription("sample text2");
        companyRequest.setId(420L);
        companyRequest.setName("Company 420");
        Employer testEmployer = new Employer();
        testEmployer.setId(420L);
        Sector testSector = new Sector();
        testSector.setId(52L);
        //act
        Company response = companyServiceImpl.updateCompany(testEmployer.getId(),testSector.getId(),companyRequest);
        //assert
        assertEquals(420L, response.getId());

    }

    @Test
    @DisplayName("When Update A Company But Company Not Exist")
    public void WhenUpdateACompanyButCompanyNotExist(){
        //arrange
        when(companyRepository.findById(69L)).thenReturn(Optional.of(company));

        //act
        String message = "Resource Company not found for Id with value 69";
        Throwable exception = catchThrowable(()->{
            companyServiceImpl.getCompanyById(1337L);
        });
        //assert
        assertThat(exception).isInstanceOf(ResourceNotFoundException.class).hasMessage(message);
    }
}