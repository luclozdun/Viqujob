package com.viqujob.jobagapi.ability.skill;

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

import com.jayway.jsonpath.Option;
import com.viqujob.jobagapi.ability.domain.model.Skill;
import com.viqujob.jobagapi.ability.domain.repository.SkillRepository;
import com.viqujob.jobagapi.ability.service.SkillServiceImpl;
import com.viqujob.jobagapi.apply.domain.model.ProfessionalProfile;
import com.viqujob.jobagapi.exception.ResourceNotFoundException;

import static org.assertj.core.api.Assertions.*;

@DataJpaTest
@AutoConfigureTestDatabase(replace = AutoConfigureTestDatabase.Replace.NONE)
public class SkillServiceImplTest {

    @Spy
    private SkillRepository skillRepository;

    @InjectMocks
    private SkillServiceImpl skillServiceImpl;

    Skill skill;

    @BeforeEach
    public void setUp() {
        Date createdAt = new Date();
        Date updateAt = new Date();
        List arrayTest = new ArrayList<ProfessionalProfile>();
        skill = new Skill();
        skill.setCreatedAt(createdAt);
        skill.setDescription("Habilidad");
        skill.setId(123L);
        skill.setName("Yoni Pacheco");
        skill.setUpdateAt(updateAt);
        skill.setProfessionalprofiles(arrayTest);

    }

    @Test
    @DisplayName("When Find By Id Then Return Skill")
    public void WhenFindByIdThenReturnSkill() {
        // arrange
        when(skillRepository.findById(123L)).thenReturn(Optional.of(skill));
        // act

        Skill response = skillServiceImpl.getSkillById(123L);
        // assert
        assertEquals(123L, response.getId());

    }

    @Test
    @DisplayName("When Find By Id But Not Exist")
    public void WhenFindByIdButNotExist() {
        // arrange
        when(skillRepository.findById(123L)).thenReturn(Optional.of(skill));
        // act
        String message = "Resource Skill not found for Id with value 234";
        Throwable exception = catchThrowable(() -> {
            skillServiceImpl.getSkillById(234L);
        });
        // assert
        assertThat(exception).isInstanceOf(ResourceNotFoundException.class).hasMessage(message);

    }

    @Test
    @DisplayName("When Save A Skill")
    public void WhenSaveASkill() {
        // arrange
        when(skillRepository.save(skill)).thenReturn(skill);
        // act
        Skill response = skillServiceImpl.createSkill(skill);
        // assert
        assertEquals(123L, response.getId());
    }

    @Test
    @DisplayName("When Save A Skill But Not Exist")
    public void WhenSaveASkillButNotExist() {
        // arrange
        when(skillRepository.save(skill)).thenReturn(skill);
        // act
        String message = "Resource Skill not found for Id with value 14";
        Throwable exception = catchThrowable(() -> {
            skillServiceImpl.getSkillById(14L);
        });
        // assert
        assertThat(exception).isInstanceOf(ResourceNotFoundException.class).hasMessage(message);
    }

    @Test
    @DisplayName("When Delete A Skill")
    public void WhenDeleteASkill() {
        // arrange
        doNothing().when(skillRepository).delete(skill);
        when(skillRepository.findById(123L)).thenReturn(Optional.of(skill));
        // act
        HttpStatus response = skillServiceImpl.deleteSkill(123L).getStatusCode();
        // assert
        assertEquals(HttpStatus.OK, response.OK);
    }

    @Test
    @DisplayName("When Delete A Skill But Not Exist")
    public void WhenDeleteASkillButNotExist() {
        // arrange
        doNothing().when(skillRepository).delete(skill);
        when(skillRepository.findById(123L)).thenReturn(Optional.of(skill));
        // act
        String message = "Resource Skill not found for Id with value 14";
        Throwable exception = catchThrowable(() -> {
            skillServiceImpl.getSkillById(14L);
        });
        // assert
        assertThat(exception).isInstanceOf(ResourceNotFoundException.class).hasMessage(message);
    }

    @Test
    @DisplayName("When Update A Skill")
    public void WhenUpdateASkill() {
        // arrange
        when(skillRepository.findById(123L)).thenReturn(Optional.of(skill));
        when(skillRepository.save(skill)).thenReturn(skill);
        Skill skillRequest = new Skill();
        skillRequest.setDescription("Habilidad2");
        skillRequest.setId(1232L);
        skillRequest.setName("Yoni Pacheco2");
        // act
        Skill response = skillServiceImpl.updateSkill(123L, skillRequest);
        // assert
        assertEquals("Yoni Pacheco2", response.getName());
    }

    @Test
    @DisplayName("When Update A Skill But Not Exist")
    public void WhenUpdateASkillButNotExist(){
        //arrange
        when(skillRepository.findById(123L)).thenReturn(Optional.of(skill));
        when(skillRepository.save(skill)).thenReturn(skill);
        Skill skillRequest = new Skill();
        skillRequest.setDescription("Habilidad2");
        skillRequest.setId(1232L);
        skillRequest.setName("Yoni Pacheco2");
        //act
        String message = "Resource Skill not found for Id with value 114";
        Throwable exception = catchThrowable(() -> {
            skillServiceImpl.getSkillById(114L);
        });
        //assert
        assertThat(exception).isInstanceOf(ResourceNotFoundException.class).hasMessage(message);
    }

}
