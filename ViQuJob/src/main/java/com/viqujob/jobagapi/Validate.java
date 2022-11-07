package com.viqujob.jobagapi;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;

@Controller
@RequestMapping("/message")
public class Validate {

    @Value("${SPRING_DATABASE_URL}")
    private String url;

    @Value("${SPRING_PASSWORD}")
    private String password;

    @Value("${SPRING_USERNAME}")
    private String username;

    @GetMapping("url")
    private ResponseEntity<String> getUrl() {
        return new ResponseEntity<>(url, HttpStatus.OK);
    }

    @GetMapping("username")
    private ResponseEntity<String> getUseraname() {
        return new ResponseEntity<>(username, HttpStatus.OK);
    }

    @GetMapping("password")
    private ResponseEntity<String> getPassword() {
        return new ResponseEntity<>(password, HttpStatus.OK);
    }
}
