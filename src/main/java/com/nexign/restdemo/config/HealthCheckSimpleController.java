package com.nexign.restdemo.config;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class HealthCheckSimpleController {

    @GetMapping("/ready")
    public ResponseEntity<?> ready(){
        return ResponseEntity.ok().build();
    }
}
