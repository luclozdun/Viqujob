package com.viqujob.jobagapi.ability.skill;

import static org.junit.jupiter.api.Assertions.assertEquals;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.boot.test.autoconfigure.jdbc.AutoConfigureTestDatabase;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;

import com.viqujob.jobagapi.ability.domain.model.Skill;
import com.viqujob.jobagapi.apply.domain.model.ProfessionalProfile;

@DataJpaTest
@AutoConfigureTestDatabase(replace = AutoConfigureTestDatabase.Replace.NONE)


public class SkillTest {

    Skill skill;
    

    @Test
    @DisplayName("Valid That Skills")
    public void skil(){
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
        
        
        assertEquals(createdAt, skill.getCreatedAt());
        assertEquals("Habilidad", skill.getDescription());
        assertEquals(123L, skill.getId());
        assertEquals("Yoni Pacheco", skill.getName());
        assertEquals(updateAt, skill.getUpdateAt());
        assertEquals(arrayTest, skill.getProfessionalprofiles());

    }

    
}
