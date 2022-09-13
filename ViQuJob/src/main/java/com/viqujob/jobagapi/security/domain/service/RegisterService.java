package com.viqujob.jobagapi.security.domain.service;

import com.viqujob.jobagapi.security.domain.model.Employer;
import com.viqujob.jobagapi.security.domain.model.Postulant;

public interface RegisterService {
    Employer createEmployer(Employer employer);

    Postulant createPostulant(Postulant employer);
}
