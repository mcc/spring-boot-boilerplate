package mcc.springbootboilerplate.controller;

import mcc.springbootboilerplate.ServerConfig;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.Collections;
import java.util.HashMap;
import java.util.Map;

@RestController
public class HealthController {
    @GetMapping(path="/health", produces= MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<Object> health(){
        Map<String, Object> health = new HashMap<>();
        health.put("health", true);
        health.put("hostName", ServerConfig.hostName);
        health.put("ipAddress", ServerConfig.ipAddress);
        health.put("serverName", ServerConfig.serverName);
        return ResponseEntity.status(HttpStatus.OK)
                .body(health);
    }
}
