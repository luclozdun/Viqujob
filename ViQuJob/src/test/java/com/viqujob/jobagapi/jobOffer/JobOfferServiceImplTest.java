package com.viqujob.jobagapi.jobOffer;

import org.springframework.boot.test.autoconfigure.jdbc.AutoConfigureTestDatabase;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;

import org.springframework.http.HttpStatus;

import com.viqujob.jobagapi.apply.domain.model.JobOffer;
import com.viqujob.jobagapi.apply.domain.repository.JobOfferRepository;
import com.viqujob.jobagapi.apply.service.JobOfferServiceImpl;
import com.viqujob.jobagapi.security.domain.model.Employer;
import com.viqujob.jobagapi.security.domain.repository.EmployerRepository;
import com.viqujob.jobagapi.exception.ResourceNotFoundException;

import static org.assertj.core.api.Assertions.*;
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

@DataJpaTest
@AutoConfigureTestDatabase(replace = AutoConfigureTestDatabase.Replace.NONE)
public class JobOfferServiceImplTest {

    @Spy
    private JobOfferRepository jobOfferRepository;
    @Spy
    private EmployerRepository employeerRepository;

    @InjectMocks
    private JobOfferServiceImpl jobOfferServiceImpl;

    JobOffer jobOffer;

    Employer employer;

    @BeforeEach
    public void setUp() {

        Date createdAt = new Date();
        Date updatedAt = new Date();

        jobOffer = new JobOffer();

        jobOffer.setCreatedAt(createdAt);

        jobOffer.setDescription("hola");
        jobOffer.setId(123L);
        jobOffer.setSalary(940L);
        jobOffer.setTitle("GO");
        jobOffer.setType("doctor");
        jobOffer.setUpdateAt(updatedAt);
        employer = new Employer();
    }

    ///////////// FIND
    @Test
    @DisplayName("When Find JobOffer By Id And Employer Id")
    public void WhenFindJobOferByIdAndEmployerId() {
        // arraque
        when(jobOfferRepository.findByIdAndEmployeerId(123L, 123L))
                .thenReturn(Optional.of(jobOffer));

        // act
        JobOffer response = jobOfferServiceImpl.getJobOfferByIdAndEmployeerId(123L, 123L);
        // assert
        assertEquals(123L, response.getId());

    }

    @Test
    @DisplayName("When Find JobOffer By Id But Not Exist")
    public void WhenFindJobOfferByIdButNotExist() {

        when(jobOfferRepository.findByIdAndEmployeerId(123L, 123L))
                .thenReturn(Optional.of(jobOffer));

        String message = "Job Offer not found with id124and EmployeerId124";
        Throwable exception = catchThrowable(() -> {
            jobOfferServiceImpl.getJobOfferByIdAndEmployeerId(124L, 124L);
        });
        assertThat(exception).isInstanceOf(ResourceNotFoundException.class).hasMessage(message);
    }

    //////////////////////////////////////////////// SAVE
    @Test
    @DisplayName("When Save A JobOffer")
    public void WhenSaveAJobOffer() {

        // arraque
        when(employeerRepository.findById(123L)).thenReturn(Optional.of(employer));
        when(jobOfferRepository.save(jobOffer)).thenReturn(jobOffer);
        // act

        JobOffer response = jobOfferServiceImpl.createJobOffer(123L, jobOffer);

        // assert
        assertEquals(123L, response.getId());

    }

    @Test
    @DisplayName("When Save JobOffer By Id But Not Save")
    public void WhenSaveJobOfferByIdButNotSave() {

        when(employeerRepository.findById(123L)).thenReturn(Optional.of(employer));
        when(jobOfferRepository.save(jobOffer)).thenReturn(jobOffer);

        String message = "Job Offer not found with id124and EmployeerId124";
        Throwable exception = catchThrowable(() -> {
            jobOfferServiceImpl.getJobOfferByIdAndEmployeerId(124L, 124L);
        });
        assertThat(exception).isInstanceOf(ResourceNotFoundException.class).hasMessage(message);
    }

    ////////////////////////////////////////////////////////////////// DELETE
    @Test
    @DisplayName("When Delete A JobOffer")
    public void WhenDeleteAJobOffer() {
        // arraque
        doNothing().when(jobOfferRepository).delete(jobOffer);
        when(jobOfferRepository.findById(123L)).thenReturn(Optional.of(jobOffer));
        when(employeerRepository.existsById(123L)).thenReturn(true);

        // act
        HttpStatus response = jobOfferServiceImpl.deleteJobOffer(123L, 123L)
                .getStatusCode();

        // assert
        assertEquals(HttpStatus.OK, response.OK);

    }

    /////////////////////////////////

    @Test
    @DisplayName("When Update A JobOffer")
    public void WhenUpdateAJobOffer() {
        // arraque
        when(employeerRepository.existsById(123L)).thenReturn(true);
        when(jobOfferRepository.findById(123L)).thenReturn(Optional.of(jobOffer));
        when(jobOfferRepository.save(jobOffer)).thenReturn(jobOffer);

        JobOffer request = new JobOffer();
        request.setDescription("vamos");
        request.setId(123L);
        request.setSalary(123L);
        request.setTitle("GOOOO");
        request.setType("NURSE");

        // act
        JobOffer response = jobOfferServiceImpl.updateJobOffer(123L, 123L, jobOffer);

        // assert
        assertEquals(123L, response.getId());

    }

    @Test
    @DisplayName("When Update JobOffer By Id But Not Exist")
    public void WhenUpdateJobOfferByIdButNotExist() {

        when(employeerRepository.existsById(123L)).thenReturn(true);
        when(jobOfferRepository.findById(123L)).thenReturn(Optional.of(jobOffer));
        when(jobOfferRepository.save(jobOffer)).thenReturn(jobOffer);

        JobOffer request = new JobOffer();
        request.setDescription("vamos");
        request.setId(123L);
        request.setSalary(123L);
        request.setTitle("GOOOO");
        request.setType("NURSE");

        String message = "Resource Employeer not found for Id with value 124";
        Throwable exception = catchThrowable(() -> {
            jobOfferServiceImpl.updateJobOffer(124L, 124L, jobOffer);
        });
        assertThat(exception).isInstanceOf(ResourceNotFoundException.class).hasMessage(message);
    }

}
