package com.viqujob.jobagapi.jobOffer;

import static org.junit.jupiter.api.Assertions.assertEquals;

import java.time.LocalDate;
import java.util.Date;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
//import org.springframework.boot.autoconfigure.batch.BatchProperties.Job;
import org.springframework.boot.test.autoconfigure.jdbc.AutoConfigureTestDatabase;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;

import com.viqujob.jobagapi.apply.domain.model.JobOffer;
import com.viqujob.jobagapi.security.domain.model.Employer;

@DataJpaTest
@AutoConfigureTestDatabase(replace = AutoConfigureTestDatabase.Replace.NONE)

public class JobOfferTest {
    JobOffer jobOffer;

    @Test
    @DisplayName("Valid That JobOffter")
    public void jobOffer() {

        Date createdAt = new Date();
        Date updatedAt = new Date();
        LocalDate begin_date_offer = LocalDate.now();
        LocalDate final_date_offer = LocalDate.now();
        Employer employeer = new Employer();

        jobOffer = new JobOffer();

        jobOffer.setCreatedAt(createdAt);
        jobOffer.setDescription("hola");
        jobOffer.setId(123L);
        jobOffer.setSalary(123L);
        jobOffer.setTitle("GO");
        jobOffer.setType("doctor");
        jobOffer.setUpdateAt(updatedAt);
        jobOffer.setBegin_date_offer(begin_date_offer);
        jobOffer.setEmployeer(employeer);
        jobOffer.setFinal_date_offer(final_date_offer);

        assertEquals("hola", jobOffer.getDescription());
        assertEquals(123L, jobOffer.getId());
        assertEquals(123L, jobOffer.getSalary());
        assertEquals("GO", jobOffer.getTitle());
        assertEquals("doctor", jobOffer.getType());
        assertEquals(updatedAt, jobOffer.getCreatedAt());
        assertEquals(createdAt, jobOffer.getUpdateAt());

    }

}
