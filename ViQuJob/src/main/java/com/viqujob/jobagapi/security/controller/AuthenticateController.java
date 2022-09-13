package com.viqujob.jobagapi.security.controller;

import com.viqujob.jobagapi.security.domain.service.AuthenticateService;
import com.viqujob.jobagapi.security.resource.SaveAuthenticateResource;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/authenticate")
public class AuthenticateController {

    @Autowired
    private AuthenticateService authenticateService;

    @PostMapping("/employer")
    public ResponseEntity<String> authenticateEmployer(@RequestBody SaveAuthenticateResource resource) {
        String token = authenticateService.authenticateEmployer(resource);
        return new ResponseEntity<>(token, HttpStatus.OK);
    }

    @PostMapping("/postulant")
    public ResponseEntity<String> authenticatePostulant(@RequestBody SaveAuthenticateResource resource) {
        String token = authenticateService.authenticatePostulant(resource);
        return new ResponseEntity<>(token, HttpStatus.OK);
    }

}
