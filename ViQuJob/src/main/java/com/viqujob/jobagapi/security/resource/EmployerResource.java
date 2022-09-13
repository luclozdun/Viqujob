package com.viqujob.jobagapi.security.resource;

import javax.validation.constraints.NotNull;

public class EmployerResource extends UserResource {

    public String posicion;

    public String getPosicion() {
        return posicion;
    }

    public EmployerResource setPosicion(String posicion) {
        this.posicion = posicion;
        return this;
    }

    public EmployerResource() {
        super();
    }

    public EmployerResource(Long id, @NotNull String firstname, @NotNull String lastname, @NotNull String email,
            @NotNull Long number, @NotNull String password, String document, String posicion) {
        super(id, firstname, lastname, email, number, password, document);
        this.posicion = posicion;

    }

}
