package com.viqujob.jobagapi.enterprise.company;

import static org.junit.jupiter.api.Assertions.assertEquals;

import java.util.Date;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.boot.test.autoconfigure.jdbc.AutoConfigureTestDatabase;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;

import com.viqujob.jobagapi.enterprise.domain.model.Company;
import com.viqujob.jobagapi.enterprise.domain.model.Sector;
import com.viqujob.jobagapi.security.domain.model.Employer;

@DataJpaTest
@AutoConfigureTestDatabase(replace = AutoConfigureTestDatabase.Replace.NONE)

public class CompanyTest {


    Company company;

    @Test
    @DisplayName("Valid That Company")
     
    public void company() {
        Employer employer = new Employer();
        Date date = new Date();
        Sector sector = new Sector();
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


        assertEquals(date, company.getCreatedAt());
        assertEquals("direccion", company.getDescription());
        assertEquals("direccion", company.getDireccion());
        assertEquals(employer, company.getEmployeer());
        assertEquals(2L, company.getId());
        assertEquals("logo", company.getLogo());
        assertEquals("name", company.getName());
        assertEquals(694201337L, company.getRuc());
        assertEquals(sector, company.getSector());
        assertEquals(date, company.getUpdateAt());

    }
}