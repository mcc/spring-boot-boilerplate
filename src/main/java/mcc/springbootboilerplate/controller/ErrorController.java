package mcc.springbootboilerplate.controller;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.Mapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api/error/")
public class ErrorController {

    @RequestMapping("/access-denied")
    public ResponseEntity<String> accessDenied(){
        return ResponseEntity.status(HttpStatus.UNAUTHORIZED).body("access-denied");
    }
}
