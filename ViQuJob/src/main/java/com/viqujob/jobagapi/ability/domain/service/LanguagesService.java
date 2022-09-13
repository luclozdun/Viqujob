package com.viqujob.jobagapi.ability.domain.service;

import com.viqujob.jobagapi.ability.domain.model.Languages;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.http.ResponseEntity;

public interface LanguagesService {
    Page<Languages> getAllLanguages(Pageable pageable);

    Languages getLanguagesById(Long languagesId);

    Languages createLanguages(Languages languages);

    Languages updateLanguages(Long languagesId, Languages languagesRequest);

    ResponseEntity<?> deleteLanguages(Long languagesId);
}
