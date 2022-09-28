package com.viqujob.jobagapi.planEmployer;

import static org.junit.jupiter.api.Assertions.assertEquals;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.boot.test.autoconfigure.jdbc.AutoConfigureTestDatabase;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;

import com.viqujob.jobagapi.membership.domain.model.PlanEmployer;

@DataJpaTest
@AutoConfigureTestDatabase(replace = AutoConfigureTestDatabase.Replace.NONE)
public class PlanEmployerTest {
    PlanEmployer planEmployer;

    @Test
    @DisplayName("Valid That PlanEmployer")
    public void planEmployer() {
        planEmployer = new PlanEmployer();
        planEmployer.setAsistence(false);
        planEmployer.setDescription("hola");

        assertEquals("hola", planEmployer.getDescription());

    }

}
