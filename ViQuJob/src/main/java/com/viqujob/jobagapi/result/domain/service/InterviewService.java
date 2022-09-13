package com.viqujob.jobagapi.result.domain.service;

import com.viqujob.jobagapi.result.domain.model.Interview;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.http.ResponseEntity;

public interface InterviewService {
    Interview createInterview(Long postulantId, Long jobOfferId, Interview interview);

    Interview updateInterview(Long postulantId, Long jobOfferId, Interview interviewDetails);

    ResponseEntity<?> deleteInterview(Long postulantId, Long jobOfferId);

    Page<Interview> getAllInterview(Pageable pageable);

    Page<Interview> getAllInterviewsByPostulantId(Long postulantId, Pageable pageable);

    Page<Interview> getAllInterviewsByJobOfferId(Long jobOfferId, Pageable pageable);

    Interview getInterviewById(Long interviewId);
}
