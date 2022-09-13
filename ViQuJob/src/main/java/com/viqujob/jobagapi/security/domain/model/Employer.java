package com.viqujob.jobagapi.security.domain.model;

import org.hibernate.annotations.OnDelete;
import org.hibernate.annotations.OnDeleteAction;

import javax.persistence.*;
import javax.validation.constraints.NotNull;

@Entity
@PrimaryKeyJoinColumn(referencedColumnName = "id")
@OnDelete(action = OnDeleteAction.CASCADE)
@Table(name = "employers")
public class Employer extends User {
    private String posicion;

    public Employer() {
        super();
    }

    public Employer(Long id, @NotNull String firstname, @NotNull String lastname, @NotNull String email,
            @NotNull Long number, @NotNull String password, String document, String posicion) {
        super(id, firstname, lastname, email, number, password, document);
        this.posicion = posicion;

    }

    public String getPosicion() {
        return posicion;
    }

    public Employer setPosicion(String posicion) {
        this.posicion = posicion;
        return this;
    }

}
