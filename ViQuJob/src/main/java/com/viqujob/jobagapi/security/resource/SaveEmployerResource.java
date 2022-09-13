package com.viqujob.jobagapi.security.resource;

import javax.validation.constraints.NotNull;

public class SaveEmployerResource extends SaveUserResource {
    @NotNull
    public String posicion;

    public String getPosicion() {
        return posicion;
    }

    public SaveEmployerResource setPosicion(String posicion) {
        this.posicion = posicion;
        return this;
    }

    public SaveEmployerResource() {
        super();
    }

    public SaveEmployerResource(@NotNull String firstname, @NotNull String lastname, @NotNull String email,
            @NotNull Long number, @NotNull String password, String document, String posicion) {
        super(firstname, lastname, email, number, password, document);
        this.posicion = posicion;

    }
}
