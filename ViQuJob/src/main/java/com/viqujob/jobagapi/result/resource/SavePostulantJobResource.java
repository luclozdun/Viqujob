package com.viqujob.jobagapi.result.resource;

public class SavePostulantJobResource {
    private boolean aceppt;

    public boolean isAceppt() {
        return aceppt;
    }

    public SavePostulantJobResource setAceppt(boolean aceppt) {
        this.aceppt = aceppt;
        return this;
    }
}
