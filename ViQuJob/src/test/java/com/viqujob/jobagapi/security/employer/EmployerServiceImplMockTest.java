package com.viqujob.jobagapi.security.employer;

import static org.mockito.Mockito.when;

import java.util.Date;
import java.util.Optional;

import com.viqujob.jobagapi.exception.ResourceNotFoundException;
import com.viqujob.jobagapi.security.domain.model.Employer;
import com.viqujob.jobagapi.security.domain.repository.EmployerRepository;
import com.viqujob.jobagapi.security.domain.repository.UserRepository;
import com.viqujob.jobagapi.security.service.EmployerServiceImpl;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.MockitoAnnotations;
import org.mockito.Spy;
import org.springframework.boot.test.autoconfigure.jdbc.AutoConfigureTestDatabase;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;

import static org.assertj.core.api.Assertions.*;
import static org.junit.jupiter.api.Assertions.assertEquals;

@DataJpaTest
@AutoConfigureTestDatabase(replace = AutoConfigureTestDatabase.Replace.NONE)
public class EmployerServiceImplMockTest {

    @Spy
    EmployerRepository employerRepository;

    @Spy
    UserRepository userRepository;

    @InjectMocks
    EmployerServiceImpl employerServiceImpl;

    Employer employer;

    @BeforeEach
    public void setUp() {
        MockitoAnnotations.openMocks(this);

        employer = new Employer();
        employer.setId(1L);
        employer.setCreatedAt(new Date());
        employer.setDocument("123456789");
        employer.setEmail("employer@gmail.com");
        employer.setFirstname("firstname");
        employer.setLastname("lastname");
        employer.setNumber(123456789L);
        employer.setPassword("password");
        employer.setPosicion("Student");
        employer.setUpdateAt(new Date());
    }

    @Test
    @DisplayName("When Find Employer By Id Then Return Employer")
    public void WhenFindEmployerByIdThenReturnEmployer() {
        // Arrange
        when(employerRepository.findById(1L)).thenReturn(Optional.of(employer));

        // Act
        Employer response = employerServiceImpl.getEmployeerById(1L);

        // Assert
        assertEquals(1L, response.getId());
    }

    @Test
    @DisplayName("When Find Employer By Id But Not Exist")
    public void WhenFindEmployerByIdButNotExist() {
        // Arrange
        when(employerRepository.findById(1L)).thenReturn(Optional.of(employer));

        // Act
        String message = "Error ocurred while creating guide";
        Throwable exception = catchThrowable(() -> {
            employerServiceImpl.getEmployeerById(2L);
        });

        // Assert
        assertThat(exception).isInstanceOf(ResourceNotFoundException.class).hasMessage(message);
    }

}
