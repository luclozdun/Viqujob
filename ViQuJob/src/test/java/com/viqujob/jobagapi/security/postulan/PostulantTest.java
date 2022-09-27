package com.viqujob.jobagapi.security.postulan;

import static org.junit.jupiter.api.Assertions.assertEquals;

import java.util.Date;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.boot.test.autoconfigure.jdbc.AutoConfigureTestDatabase;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;

import com.viqujob.jobagapi.security.domain.model.Postulant;

@DataJpaTest
@AutoConfigureTestDatabase(replace = AutoConfigureTestDatabase.Replace.NONE)
public class PostulantTest {
    Postulant postulant;

    @Test
    @DisplayName("Valid That Postulant")
    public void postulan() {
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

        assertEquals("estudiante", postulant.getCivil_status());

}
}
