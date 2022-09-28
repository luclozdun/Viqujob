package com.viqujob.jobagapi.enterprise.sector;

import static org.junit.jupiter.api.Assertions.assertEquals;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.boot.test.autoconfigure.jdbc.AutoConfigureTestDatabase;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;

import com.viqujob.jobagapi.enterprise.domain.model.Sector;

@DataJpaTest
@AutoConfigureTestDatabase(replace = AutoConfigureTestDatabase.Replace.NONE)

public class SectorTest {


    Sector sector;

    @Test
    @DisplayName("Valid That Sector")
    public void sector() {
        sector = new Sector();
        sector.setDescription("sample text");
        sector.setId(69L);
        sector.setName("Sector 7");

        assertEquals("sample text", sector.getDescription());
        assertEquals(69L, sector.getId());
        assertEquals("Sector 7", sector.getName());
    }
}