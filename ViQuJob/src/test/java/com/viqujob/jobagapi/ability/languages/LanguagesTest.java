package com.viqujob.jobagapi.ability.languages;

import static org.junit.jupiter.api.Assertions.assertEquals;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.boot.test.autoconfigure.jdbc.AutoConfigureTestDatabase;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;

import com.viqujob.jobagapi.ability.domain.model.Languages;
import com.viqujob.jobagapi.apply.domain.model.ProfessionalProfile;

//Ca,mbio
@DataJpaTest
@AutoConfigureTestDatabase(replace = AutoConfigureTestDatabase.Replace.NONE)

public class LanguagesTest {

    Languages languages;

    @Test
    @DisplayName("Valid That Languanges")
    public void language() {
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

        assertEquals(createdAt, languages.getCreatedAt());
        assertEquals(12L, languages.getId());
        assertEquals(45L, languages.getLevel());
        assertEquals("Panchito", languages.getName());
        assertEquals(arrayTest, languages.getProfessionalprofiles());
        assertEquals(updateAt, languages.getUpdateAt());

    }

}
