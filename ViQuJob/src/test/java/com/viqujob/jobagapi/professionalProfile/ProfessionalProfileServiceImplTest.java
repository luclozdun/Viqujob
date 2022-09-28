package com.viqujob.jobagapi.professionalProfile;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.mockito.InjectMocks;
import org.mockito.Spy;
import org.springframework.boot.test.autoconfigure.jdbc.AutoConfigureTestDatabase;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import static org.mockito.Mockito.doNothing;
import static org.mockito.Mockito.when;
import org.junit.jupiter.api.Test;
import org.springframework.http.HttpStatus;

import com.viqujob.jobagapi.apply.service.ProfessionalProfileImpl;
import com.viqujob.jobagapi.exception.ResourceNotFoundException;
import com.viqujob.jobagapi.ability.domain.model.Languages;
import com.viqujob.jobagapi.ability.domain.model.Skill;
import com.viqujob.jobagapi.ability.domain.model.Studies;
import com.viqujob.jobagapi.ability.domain.repository.LanguagesRepository;
import com.viqujob.jobagapi.ability.domain.repository.SkillRepository;
import com.viqujob.jobagapi.ability.domain.repository.StudiesRepository;
import com.viqujob.jobagapi.apply.domain.model.ProfessionalProfile;
import com.viqujob.jobagapi.apply.domain.repository.ProfessionalProfileRepository;
import com.viqujob.jobagapi.security.domain.model.Employer;
import com.viqujob.jobagapi.security.domain.model.Postulant;
import com.viqujob.jobagapi.security.domain.repository.PostulantRepository;
import static org.assertj.core.api.Assertions.*;
import static org.junit.jupiter.api.Assertions.assertEquals;

import java.util.ArrayList;
import java.util.Optional;

@DataJpaTest
@AutoConfigureTestDatabase(replace = AutoConfigureTestDatabase.Replace.NONE)
public class ProfessionalProfileServiceImplTest {
    @Spy
    private PostulantRepository postulantRepository;

    @Spy
    private SkillRepository skillRepository;

    @Spy
    private StudiesRepository studiesRepository;

    @Spy
    private LanguagesRepository languagesRepository;

    @Spy
    private ProfessionalProfileRepository professionalprofileRepository;

    @InjectMocks
    private ProfessionalProfileImpl professionalProfileServiceImpl;

    Postulant postulant;
    Skill skill;
    Studies studies;
    Languages languages;
    Employer employer;
    ProfessionalProfile professionalProfile;
    ArrayList<Skill> arraySkiList = new ArrayList<Skill>();
    ArrayList<Studies> arrayStudies = new ArrayList<Studies>();
    ArrayList<Languages> arrayLanguages = new ArrayList<Languages>();

    @BeforeEach
    public void setUp() {
        professionalProfile = new ProfessionalProfile();
        professionalProfile.setDescription("hola");
        professionalProfile.setId(123L);
        professionalProfile.setLanguages(arrayLanguages);
        professionalProfile.setOcupation("ocupation");

        professionalProfile.setPostulant(postulant);
        professionalProfile.setSkills(arraySkiList);
        professionalProfile.setStudies(arrayStudies);

        professionalProfile.setVideo("nada");
        postulant = new Postulant();
    }

    /// FIND
    @Test
    @DisplayName("When Find ProfessionalProfile By Id And Employeer Id")
    public void WhenFindProfessionalProfileByIdAndEmployeerId() {
        when(professionalprofileRepository.findByIdAndPostulantId(123L, 123L))
                .thenReturn(Optional.of(professionalProfile));

        ProfessionalProfile response = professionalProfileServiceImpl.getProfessionalProfileByIdAndPostulantId(123L,
                123L);

        assertEquals(123L, response.getId());
    }

    @Test
    @DisplayName("When Find ProfessionalProfile By Id But Not Exist")
    public void WhenFindProfessionalProfileByIdButNotExist() {
        when(professionalprofileRepository.findByIdAndPostulantId(123L, 123L))
                .thenReturn(Optional.of(professionalProfile));

        String message = "Lead not found with Id124and PostulantId124";
        Throwable exception = catchThrowable(() -> {
            professionalProfileServiceImpl.getProfessionalProfileByIdAndPostulantId(124L,
                    124L);
        });

        assertThat(exception).isInstanceOf(ResourceNotFoundException.class).hasMessage(message);

    }

    ////////// SAVE
    @Test
    @DisplayName("When Save A ProfessionalProfile")
    public void WhenSaveAProfessionalProfile() {
        when(postulantRepository.findById(123L)).thenReturn(Optional.of(postulant));
        when(professionalprofileRepository.save(professionalProfile)).thenReturn(professionalProfile);

        ProfessionalProfile response = professionalProfileServiceImpl.createProfessionalProfile(123L,
                professionalProfile);

        assertEquals(123L, response.getId());
    }

    @Test
    @DisplayName("When Save A ProfessionalProfile But Not Save")
    public void WhenSaveAProfessionalProfileButNotSave() {
        when(postulantRepository.findById(123L)).thenReturn(Optional.of(postulant));
        when(professionalprofileRepository.save(professionalProfile)).thenReturn(professionalProfile);

        String message = "Resource Postulant not found for Id with value 124";
        Throwable exception = catchThrowable(() -> {
            professionalProfileServiceImpl.createProfessionalProfile(124L,
                    professionalProfile);
        });

        assertThat(exception).isInstanceOf(ResourceNotFoundException.class).hasMessage(message);

    }

    /////////////////////// DELETE
    @Test
    @DisplayName("When Delete A ProfessionalProfile")
    public void WhenDeleteAProfessionalProfile() {
        // arranque
        doNothing().when(professionalprofileRepository).delete(professionalProfile);
        when(professionalprofileRepository.findById(123L)).thenReturn(Optional.of(professionalProfile));
        when(professionalprofileRepository.existsById(123L)).thenReturn(true);

        // act
        HttpStatus response = professionalProfileServiceImpl.deleteProfessionalProfile(123L, 123L).getStatusCode();

        // assert
        assertEquals(HttpStatus.OK, response.OK);
    }

    @Test
    @DisplayName("When Delete A ProfessionalProfile Not Delete")
    public void WhenDeleteAProfessionalProfileNotDelete() {
        // arranque
        doNothing().when(professionalprofileRepository).delete(professionalProfile);
        when(professionalprofileRepository.findById(123L)).thenReturn(Optional.of(
                professionalProfile));
        when(professionalprofileRepository.existsById(123L)).thenReturn(true);

        // act
        String message = "Resource Postulant not found for Id with value 124";
        Throwable exception = catchThrowable(() -> {
            professionalProfileServiceImpl.deleteProfessionalProfile(124L,
                    124L).getStatusCode();

        });

        // assert
        assertThat(exception).isInstanceOf(ResourceNotFoundException.class).hasMessage(message);
    }

    @Test
    @DisplayName("When Update A ProfessionalProfile")
    public void WhenUpdateAProfessionalProfile() {
        // arraque

        when(professionalprofileRepository.findById(123L)).thenReturn(Optional.of(professionalProfile));
        when(professionalprofileRepository.save(professionalProfile)).thenReturn(professionalProfile);

        ProfessionalProfile request = new ProfessionalProfile();
        request.setDescription("hola");
        request.setId(123L);
        // request.setLanguages(arrayLanguages);
        request.setOcupation("ocupation");

        request.setPostulant(postulant);
        request.setSkills(arraySkiList);
        request.setStudies(arrayStudies);

        request.setVideo("nada");

        // act
        ProfessionalProfile response = professionalProfileServiceImpl.updateProfessionalProfile(123L, 123L,
                professionalProfile);

        // assert
        assertEquals("nada", response.getVideo());

    }

    @Test
    @DisplayName("When Update A ProfessionalProfile Fails")
    public void WhenUpdateAProfessionalProfileFails() {
        // arraque

        when(professionalprofileRepository.findById(123L)).thenReturn(Optional.of(professionalProfile));
        when(professionalprofileRepository.save(professionalProfile)).thenReturn(professionalProfile);

        ProfessionalProfile request = new ProfessionalProfile();
        request.setDescription("hola");
        request.setId(123L);
        // request.setLanguages(arrayLanguages);
        request.setOcupation("ocupation");
        /*
         * request.setPostulant(postulante);
         * request.setSkills(arraySkiList);
         * request.setStudies(arrayStudies);
         */
        request.setVideo("nada");

        // act
        String message = "Resource Professional Profile not found for Id with value 124";

        // assert

        Throwable exception = catchThrowable(() -> {
            professionalProfileServiceImpl.updateProfessionalProfile(124L, 124L,
                    professionalProfile);
        });
        assertThat(exception).isInstanceOf(ResourceNotFoundException.class).hasMessage(message);

    }

}
