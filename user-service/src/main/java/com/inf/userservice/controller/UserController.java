package com.inf.userservice.controller;

import lombok.extern.slf4j.Slf4j;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestHeader;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/user-service")
@Slf4j
public class UserController {

    @GetMapping("/hello")
    public ResponseEntity<String> hello(@RequestHeader("user-request") String userRequest) {
        return ResponseEntity.ok(userRequest);
    }

    @GetMapping("/check")
    public String check() {
        return "user-service/check!";
    }

}
