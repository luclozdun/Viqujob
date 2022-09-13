package com.viqujob.jobagapi.ability.service;

import com.viqujob.jobagapi.ability.domain.model.Languages;
import com.viqujob.jobagapi.ability.domain.repository.LanguagesRepository;
import com.viqujob.jobagapi.ability.domain.service.LanguagesService;
import com.viqujob.jobagapi.exception.ResourceNotFoundException;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

@Service
public class LanguagesServiceImpl implements LanguagesService {
    @Autowired
    private LanguagesRepository languagesRepository;

    @Override
    public Page<Languages> getAllLanguages(Pageable pageable) {
        return languagesRepository.findAll(pageable);
    }

    @Override
    public Languages getLanguagesById(Long languagesId) {
        return languagesRepository.findById(languagesId)
                .orElseThrow(() -> new ResourceNotFoundException("Languages", "Id", languagesId));
    }

    @Override
    public Languages createLanguages(Languages languages) {
        return languagesRepository.save(languages);
    }

    @Override
    public Languages updateLanguages(Long languagesId, Languages languagesRequest) {
        Languages languages = languagesRepository.findById(languagesId)
                .orElseThrow(() -> new ResourceNotFoundException("Languages", "Id", languagesId));
        return languagesRepository.save(
                languages.setName(languagesRequest.getName())
                        .setLevel(languagesRequest.getLevel()));
    }

    @Override
    public ResponseEntity<?> deleteLanguages(Long languagesId) {
        Languages languages = languagesRepository.findById(languagesId)
                .orElseThrow(() -> new ResourceNotFoundException("Languages", "Id", languagesId));
        languagesRepository.delete(languages);
        return ResponseEntity.ok().build();
    }
}
