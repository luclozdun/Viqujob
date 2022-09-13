package com.viqujob.jobagapi.ability.resource;

import javax.validation.constraints.Size;

public class SaveStudiesResource {
    @Size(max = 30)
    private String name;
    private Long degree;

    public String getName() {
        return name;
    }

    public SaveStudiesResource setName(String name) {
        this.name = name;
        return this;
    }

    public Long getDegree() {
        return degree;
    }

    public SaveStudiesResource setDegree(Long degree) {
        this.degree = degree;
        return this;
    }
}
