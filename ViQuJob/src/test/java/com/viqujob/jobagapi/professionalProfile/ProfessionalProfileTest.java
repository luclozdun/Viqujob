package com.viqujob.jobagapi.professionalProfile;

import static org.junit.jupiter.api.Assertions.assertEquals;

import java.util.ArrayList;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.boot.test.autoconfigure.jdbc.AutoConfigureTestDatabase;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;

import com.viqujob.jobagapi.ability.domain.model.Languages;
import com.viqujob.jobagapi.ability.domain.model.Skill;
import com.viqujob.jobagapi.ability.domain.model.Studies;
import com.viqujob.jobagapi.apply.domain.model.ProfessionalProfile;
import com.viqujob.jobagapi.security.domain.model.Postulant;

@DataJpaTest
@AutoConfigureTestDatabase(replace = AutoConfigureTestDatabase.Replace.NONE)
public class ProfessionalProfileTest {

    ProfessionalProfile professionalProfile;
    ArrayList<Skill> arraySkiList = new ArrayList<Skill>();
    ArrayList<Studies> arrayStudies = new ArrayList<Studies>();
    ArrayList<Languages> arrayLanguages = new ArrayList<Languages>();
    Postulant postulante;

    @Test
    @DisplayName("Valid That ProfessionalProfile")
    public void professionalProfile() {
        professionalProfile = new ProfessionalProfile();
        professionalProfile.setDescription("hola");
        professionalProfile.setId(123L);
        professionalProfile.setLanguages(arrayLanguages);
        professionalProfile.setOcupation("ocupation");
        professionalProfile.setPostulant(postulante);
        professionalProfile.setSkills(arraySkiList);
        professionalProfile.setStudies(arrayStudies);
        professionalProfile.setVideo("nada");

        assertEquals("hola", professionalProfile.getDescription());
        assertEquals(123L, professionalProfile.getId());
        assertEquals(arrayLanguages, professionalProfile.getLanguages());
        assertEquals("ocupation", professionalProfile.getOcupation());
        assertEquals(postulante, professionalProfile.getPostulant());
        assertEquals(arraySkiList, professionalProfile.getSkills());
        assertEquals(arrayStudies, professionalProfile.getStudies());
        assertEquals("nada", professionalProfile.getVideo());
    }

}
