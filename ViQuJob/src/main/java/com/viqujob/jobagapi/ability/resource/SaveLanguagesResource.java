package com.viqujob.jobagapi.ability.resource;

import javax.validation.constraints.Size;

public class SaveLanguagesResource {
    @Size(max = 30)
    private String name;
    private Long level;

    public String getName() {
        return name;
    }

    public SaveLanguagesResource setName(String name) {
        this.name = name;
        return this;
    }

    public Long getLevel() {
        return level;
    }

    public SaveLanguagesResource setLevel(Long level) {
        this.level = level;
        return this;
    }
}
