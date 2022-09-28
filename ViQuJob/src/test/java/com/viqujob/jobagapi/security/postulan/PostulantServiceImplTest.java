package com.viqujob.jobagapi.security.postulan;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Spy;
import org.springframework.boot.test.autoconfigure.jdbc.AutoConfigureTestDatabase;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.http.HttpStatus;

import static org.assertj.core.api.Assertions.*;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.Mockito.doNothing;
import static org.mockito.Mockito.when;

import java.util.Date;
import java.util.Optional;

import com.viqujob.jobagapi.exception.ResourceIncorrectData;
import com.viqujob.jobagapi.exception.ResourceNotFoundException;
import com.viqujob.jobagapi.security.domain.model.Postulant;
import com.viqujob.jobagapi.security.domain.repository.PostulantRepository;
import com.viqujob.jobagapi.security.domain.repository.UserRepository;
import com.viqujob.jobagapi.security.service.PostulantServiceImpl;

@DataJpaTest
@AutoConfigureTestDatabase(replace = AutoConfigureTestDatabase.Replace.NONE)
public class PostulantServiceImplTest {

    @Spy
    private PostulantRepository postulantRepository;

    @Spy
    UserRepository userRepository;

    @InjectMocks
    private PostulantServiceImpl postulantServiceImpl;

    Postulant postulant;

    @BeforeEach
    public void setUp() {
        postulant = new Postulant();
        postulant.setCivil_status("estudiante");
        postulant.setCreatedAt(new Date());
        postulant.setDocument("123456789");
        postulant.setEmail("email@gmail.com");
        postulant.setFirstname("Luciano");
        postulant.setId(1L);
        postulant.setLastname("Lopez");
        postulant.setNumber(123456789L);
        postulant.setPassword("password");
        postulant.setUpdateAt(new Date());
    }

    @Test
    @DisplayName("When Find By Id Then Return Postulant")
    public void WhenFindByIdThenReturnUser() {
        // Arrange
        when(postulantRepository.findById(1L)).thenReturn(Optional.of(postulant));
        // Act

        Postulant response = postulantServiceImpl.getPostulantById(1L);

        // Assert
        assertEquals(1L, response.getId());
        // assertThat(exception).isInstanceOf().hasMessage(message);
    }

    @Test
    @DisplayName("When Find By Id But Not Exist")
    public void WhenFindByIdButNotExist() {
        // Arrange
        when(postulantRepository.findById(1L)).thenReturn(Optional.of(postulant));
        // Act

        String message = "Resource Postulant not found for Id with value 2";
        Throwable exception = catchThrowable(() -> {
            postulantServiceImpl.getPostulantById(2L);
        });

        // Assert
        assertThat(exception).isInstanceOf(ResourceNotFoundException.class).hasMessage(message);
    }

    @Test
    @DisplayName("When Save A Postulant")
    public void WhenSaveAPostulant() {
        // Arrange

        when(userRepository.existsByEmail("email")).thenReturn(false);
        when(postulantRepository.save(postulant)).thenReturn(postulant);

        // Act
        Postulant response = postulantServiceImpl.createPostulant(postulant);

        // Assert
        assertEquals(1L, response.getId());
    }

    @Test
    @DisplayName("When Save A Postulant But Email Exist")
    public void WhenSaveAPostulantButEmailExist() {
        // Arrange

        when(userRepository.existsByEmail("email@gmail.com")).thenReturn(true);
        when(postulantRepository.save(postulant)).thenReturn(postulant);

        String message = "El email ya esta en uso";
        Throwable exception = catchThrowable(() -> {
            postulantServiceImpl.createPostulant(postulant);
        });

        // Assert
        assertThat(exception).isInstanceOf(ResourceIncorrectData.class).hasMessage(message);
    }

    @Test
    @DisplayName("When Update A Postulant")
    public void WhenUpdateAPostulant() {
        // Arrange
        Postulant request = new Postulant();
        request.setId(1L);
        request.setCivil_status("Tecnico");
        request.setDocument("document");

        when(postulantRepository.findById(1L)).thenReturn(Optional.of(postulant));
        when(postulantRepository.save(postulant)).thenReturn(postulant);

        // Act
        Postulant response = postulantServiceImpl.updatePostulant(1L, request);

        // Assert
        assertEquals("Tecnico", response.getCivil_status());
    }

    @Test
    @DisplayName("When Update A Postulant But Not Exist By Id")
    public void WhenUpdateAPostulantButNotExistById() {
        // Arrange
        Postulant request = new Postulant();
        request.setCivil_status("Tecnico");

        when(postulantRepository.findById(1L)).thenReturn(Optional.of(postulant));
        when(postulantRepository.save(postulant)).thenReturn(postulant);

        // Act
        String message = "Resource Postulant not found for Id with value 2";
        Throwable exception = catchThrowable(() -> {
            postulantServiceImpl.getPostulantById(2L);
        });

        // Assert
        assertThat(exception).isInstanceOf(ResourceNotFoundException.class).hasMessage(message);
    }

    @Test
    @DisplayName("When Delete A Postulant")
    public void WhenDeleteAPostulant() {
        // Arrange
        when(postulantRepository.findById(1L)).thenReturn(Optional.of(postulant));
        doNothing().when(postulantRepository).delete(postulant);

        HttpStatus response = postulantServiceImpl.deletePostulant(1L).getStatusCode();

        assertEquals(HttpStatus.OK, response.OK);
    }

    @Test
    @DisplayName("When Delete A Postulant But Not Exist Postulant By Id")
    public void WhenDeleteAPostulantButNotExistPostulantById() {
        // Arrange
        when(postulantRepository.findById(1L)).thenReturn(Optional.of(postulant));
        doNothing().when(postulantRepository).delete(postulant);

        String message = "Resource Postulant not found for Id with value 2";
        Throwable exception = catchThrowable(() -> {
            postulantServiceImpl.getPostulantById(2L);
        });

        // Assert
        assertThat(exception).isInstanceOf(ResourceNotFoundException.class).hasMessage(message);
    }

}
