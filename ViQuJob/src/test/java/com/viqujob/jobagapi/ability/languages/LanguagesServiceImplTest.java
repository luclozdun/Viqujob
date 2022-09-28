package com.viqujob.jobagapi.ability.languages;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.Mockito.doNothing;
import static org.mockito.Mockito.when;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Optional;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Spy;
import org.springframework.boot.test.autoconfigure.jdbc.AutoConfigureTestDatabase;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.http.HttpStatus;

import com.viqujob.jobagapi.ability.domain.model.Languages;
import com.viqujob.jobagapi.ability.domain.repository.LanguagesRepository;
import com.viqujob.jobagapi.ability.service.LanguagesServiceImpl;
import com.viqujob.jobagapi.exception.ResourceNotFoundException;

import static org.assertj.core.api.Assertions.*;

@DataJpaTest
@AutoConfigureTestDatabase(replace = AutoConfigureTestDatabase.Replace.NONE)
public class LanguagesServiceImplTest {

    @Spy
    private LanguagesRepository languagesRepository;

    @InjectMocks
    private LanguagesServiceImpl languagesServiceImpl;

    Languages languages;

    @BeforeEach
    public void setUp() {
        Date createdAt = new Date();
        Date updateAt = new Date();
        List arrayTest = new ArrayList<ProfessionalProfile>();
        languages = new Languages();
        languages.setCreatedAt(createdAt);
        languages.setId(12L);
        languages.setLevel(45L);
        languages.setName("Panchito");
        languages.setProfessionalprofiles(arrayTest);
        languages.setUpdateAt(updateAt);

    }

    @Test
    @DisplayName("When Find By Id Then Return Languages")
    public void WhenFindByIdThenReturnLanguages() {
        // arrange
        when(languagesRepository.findById(12L)).thenReturn(Optional.of(languages));
        // act
        Languages response = languagesServiceImpl.getLanguagesById(12L);
        // assert
        assertEquals(12L, response.getId());
    }

    @Test
    @DisplayName("When Find By Id But Not Exist")
    public void WhenFindByIdButNotExist() {
        // arrange
        when(languagesRepository.findById(12L)).thenReturn(Optional.of(languages));
        // act
        String message = "Resource Languages not found for Id with value 15";
        Throwable exception = catchThrowable(() -> {
            languagesServiceImpl.getLanguagesById(15L);
        });
        // assert
        assertThat(exception).isInstanceOf(ResourceNotFoundException.class).hasMessage(message);
    }

    @Test
    @DisplayName("When Save Languages")
    public void WhenSaveLanguages() {
        // arrange
        when(languagesRepository.save(languages)).thenReturn(languages);
        // act
        Languages response = languagesServiceImpl.createLanguages(languages);
        // assert
        assertEquals(12L, response.getId());
    }

    @Test
    @DisplayName("When Save Languages But Not Exist")
    public void WhenSaveLanguagesButNotExist() {
        // arrange
        when(languagesRepository.save(languages)).thenReturn(languages);
        // act
        String message = "Resource Languages not found for Id with value 14";
        Throwable exception = catchThrowable(() -> {
            languagesServiceImpl.getLanguagesById(14L);
        });
        // assert
        assertThat(exception).isInstanceOf(ResourceNotFoundException.class).hasMessage(message);
    }

    @Test
    @DisplayName("When Delete Languages")
    public void WhenDeleteLanguages() {
        // arrange
        doNothing().when(languagesRepository).delete(languages);
        when(languagesRepository.findById(12L)).thenReturn(Optional.of(languages));
        // act
        HttpStatus response = languagesServiceImpl.deleteLanguages(12L).getStatusCode();
        // assert
        assertEquals(HttpStatus.OK, response.OK);
    }

    @Test
    @DisplayName("When Delete A languages But Not Exist")
    public void WhenDeleteAlanguagesButNotExist() {
        // arrange
        doNothing().when(languagesRepository).delete(languages);
        when(languagesRepository.findById(123L)).thenReturn(Optional.of(languages));
        // act
        String message = "Resource Languages not found for Id with value 147";
        Throwable exception = catchThrowable(() -> {
            languagesServiceImpl.getLanguagesById(147L);
        });
        // assert
        assertThat(exception).isInstanceOf(ResourceNotFoundException.class).hasMessage(message);
    }

    @Test
    @DisplayName("When Update Languages")
    public void WhenUpdateLanguages() {
        // arrange
        when(languagesRepository.findById(12L)).thenReturn(Optional.of(languages));
        when(languagesRepository.save(languages)).thenReturn(languages);
        Languages languagesRequest = new Languages();
        languagesRequest.setId(121L);
        languagesRequest.setName("Francisco");
        languagesRequest.setLevel(46L);
        // act
        Languages response = languagesServiceImpl.updateLanguages(12L, languagesRequest);
        // assert
        assertEquals("Francisco", response.getName());
    }

    @Test
    @DisplayName("When Update Languages But Not Exist")
    public void WhenUpdateLanguagesButNotExist() {
        // arrange
        when(languagesRepository.findById(12L)).thenReturn(Optional.of(languages));
        when(languagesRepository.save(languages)).thenReturn(languages);
        Languages languagesRequest = new Languages();
        languagesRequest.setId(121L);
        languagesRequest.setName("Francisco");
        languagesRequest.setLevel(46L);
        // act
        String message = "Resource Languages not found for Id with value 114";
        Throwable exception = catchThrowable(() -> {
            languagesServiceImpl.getLanguagesById(114L);
        });
        // assert
        assertThat(exception).isInstanceOf(ResourceNotFoundException.class).hasMessage(message);
    }

}
