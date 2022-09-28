package com.viqujob.jobagapi.planEmployer;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.Mockito.doNothing;
import static org.mockito.Mockito.when;

import java.util.Optional;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Spy;
import org.springframework.boot.test.autoconfigure.jdbc.AutoConfigureTestDatabase;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.http.HttpStatus;

import com.jayway.jsonpath.Option;
import com.viqujob.jobagapi.exception.ResourceNotFoundException;
import com.viqujob.jobagapi.membership.domain.model.PlanEmployer;
import com.viqujob.jobagapi.membership.domain.repository.PlanEmployerRepository;
import com.viqujob.jobagapi.membership.service.PlanEmployerServiceImpl;
import com.viqujob.jobagapi.security.domain.model.Employer;
import com.viqujob.jobagapi.security.domain.model.Postulant;
import com.viqujob.jobagapi.security.domain.repository.EmployerRepository;
import com.viqujob.jobagapi.security.domain.repository.UserRepository;
import com.viqujob.jobagapi.security.service.EmployerServiceImpl;

import static org.assertj.core.api.Assertions.*;

@DataJpaTest
@AutoConfigureTestDatabase(replace = AutoConfigureTestDatabase.Replace.NONE)

public class PlanEmployerServiceImplTest {

    @Spy
    private EmployerRepository employeerRepository;

    @Spy
    private PlanEmployerRepository planemployeerRepository;

    @InjectMocks
    private PlanEmployerServiceImpl planEmployerServiceImpl;

    PlanEmployer planEmployer;
    Employer employer;

    @BeforeEach
    public void setUp() {
        planEmployer = new PlanEmployer();
        planEmployer.setAsistence(false);
        planEmployer.setDescription("hola");
        planEmployer.setId(123L);
        employer = new Employer();
        employer.setId(123L);

    }

    @Test
    @DisplayName("When Find PlanEmployeer By Id And Employeer Id")
    public void WhenFindByIdThemReturnPlanEmployer() {
        // aranque
        when(planemployeerRepository.findByIdAndEmployeerId(123L, 123L))
                .thenReturn(Optional.of(planEmployer));
        // act
        PlanEmployer response = planEmployerServiceImpl.getPlanemployeerByIdAndEmployeerId(123L, 123L);

        assertEquals(123L, response.getId());
        // assert
    }

    @Test
    @DisplayName("When Find PlanEmployeer By Id But Not Exist")
    public void WhenFindPlanEmployeerByIdByButNotExist() {
        // aranque
        when(planemployeerRepository.findByIdAndEmployeerId(123L, 123L)).thenReturn(Optional.of(planEmployer));
        // act

        // Postulant response =
        // planEmployerServiceImpl.getPlanemployeerByIdAndEmployeerId(Long
        String message = "Lead not found with Id124and EmployeerId124";
        Throwable exception = catchThrowable(() -> {
            planEmployerServiceImpl.getPlanemployeerByIdAndEmployeerId(124L, 124L);
        });

        // planemployeersId, Long employeerId);
        // assertEquals(123L, response.getId());
        assertThat(exception).isInstanceOf(ResourceNotFoundException.class).hasMessage(message);
        // assert

    }

    ///////////////////////////////////////////////////// SAVE

    @Test
    @DisplayName("When Save A PlanEmployeer")

    public void WhenSaveAPlanEmployeer() {
        when(employeerRepository.findById(123L)).thenReturn(Optional.of(employer));
        when(planemployeerRepository.save(planEmployer)).thenReturn(planEmployer);

        // act
        PlanEmployer response = planEmployerServiceImpl.createPlanemployeer(123L, planEmployer);
        // assert
        assertEquals(123L, response.getId());

    }

    @Test
    @DisplayName("When Save PlanEmployeer By Id But Not Exist")
    public void WhenSavePlanEmployeerByIdByButNotExist() {
        // aranque
        when(employeerRepository.findById(123L)).thenReturn(Optional.of(employer));
        when(planemployeerRepository.save(planEmployer)).thenReturn(planEmployer);
        // act

        // Postulant response =
        // planEmployerServiceImpl.getPlanemployeerByIdAndEmployeerId(Long
        String message = "Resource Employeer not found for Id with value 124";
        Throwable exception = catchThrowable(() -> {
            planEmployerServiceImpl.createPlanemployeer(124L, planEmployer);
        });

        // planemployeersId, Long employeerId);
        // assertEquals(123L, response.getId());
        assertThat(exception).isInstanceOf(ResourceNotFoundException.class).hasMessage(message);
        // assert

    }

    //////////////////// DELETE

    @Test
    @DisplayName("When Delete A PlanEmployeer")
    public void WhenDeleteAPlanEmployeer() {
        // arranque
        doNothing().when(planemployeerRepository).delete(planEmployer);
        when(planemployeerRepository.findById(123L)).thenReturn(Optional.of(planEmployer));
        when(planemployeerRepository.existsById(123L)).thenReturn(true);

        // act
        HttpStatus response = planEmployerServiceImpl.deletePlanemployeer(123L, 123L).getStatusCode();

        // assert
        assertEquals(HttpStatus.OK, response.OK);
    }

    @Test
    @DisplayName("When Delete PlanEmployeer By Id But Not Exist")
    public void WhenDeletePlanEmployeerByIdByButNotExist() {
        // aranque
        doNothing().when(planemployeerRepository).delete(planEmployer);
        when(planemployeerRepository.findById(123L)).thenReturn(Optional.of(planEmployer));
        when(planemployeerRepository.existsById(123L)).thenReturn(true);
        // act

        // Postulant response =
        // planEmployerServiceImpl.getPlanemployeerByIdAndEmployeerId(Long
        String message = "Resource Employeer not found for Id with value 125";
        Throwable exception = catchThrowable(() -> {
            planEmployerServiceImpl.deletePlanemployeer(125L, 125L).getStatusCode();
        });

        // planemployeersId, Long employeerId);
        // assertEquals(123L, response.getId());
        assertThat(exception).isInstanceOf(ResourceNotFoundException.class).hasMessage(message);
        // assert

    }

    /////////////////////////////////// update
    @Test
    @DisplayName("When Update A PlanEmployeer")
    public void WhenUpdateAPlanEmployeer() {
        // arranque

        when(employeerRepository.existsById(123L)).thenReturn(true);
        when(planemployeerRepository.findById(123L)).thenReturn(Optional.of(planEmployer));
        when(planemployeerRepository.save(planEmployer)).thenReturn(planEmployer);
        PlanEmployer request = new PlanEmployer();

        request.setAsistence(true);
        request.setDescription("doce");
        request.setId(123L);

        // act
        PlanEmployer response = planEmployerServiceImpl.updatePlanemployeer(123L, 123L, request);

        // assert
        assertEquals("doce", response.getDescription());

    }

    @Test
    @DisplayName("When Update PlanEmployeer By Id But Not Exist")
    public void WhenUpdatePlanEmployeerByIdByButNotExist() {
        // aranque
        when(employeerRepository.existsById(123L)).thenReturn(true);
        when(planemployeerRepository.findById(123L)).thenReturn(Optional.of(planEmployer));
        when(planemployeerRepository.save(planEmployer)).thenReturn(planEmployer);
        // act
        PlanEmployer request = new PlanEmployer();

        request.setAsistence(true);
        request.setDescription("doce");
        request.setId(123L);

        // Postulant response =
        // planEmployerServiceImpl.getPlanemployeerByIdAndEmployeerId(Long
        String message = "Resource Employeer not found for Id with value 125";
        Throwable exception = catchThrowable(() -> {
            planEmployerServiceImpl.deletePlanemployeer(125L, 125L).getStatusCode();
        });

        // planemployeersId, Long employeerId);
        // assertEquals(123L, response.getId());
        assertThat(exception).isInstanceOf(ResourceNotFoundException.class).hasMessage(message);
        // assert

    }

}
