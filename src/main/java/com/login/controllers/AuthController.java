package com.login.controllers;

import com.login.dtos.RequestLoginDTO;
import com.login.dtos.RequestRegisterDTO;
import com.login.services.AuthService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.HashMap;

@RestController
@RequestMapping("/auth")
public class AuthController {

    @Autowired
    private AuthService authService;

    @PostMapping("/register")
    private ResponseEntity<HashMap<String, Object>> addUser(@RequestBody RequestRegisterDTO requestRegisterDTO) throws Exception {
        return new ResponseEntity<>(authService.register(requestRegisterDTO), HttpStatus.OK);
    }

    @PostMapping("/login")
    private ResponseEntity<HashMap<String, Object>> login(@RequestBody RequestLoginDTO requestLoginDTO) throws Exception {
        HashMap<String, Object> login = authService.login(requestLoginDTO);
        return new ResponseEntity<>(login, HttpStatus.OK);
    }

    @GetMapping("/validate-token/{token}")
    public ResponseEntity<HashMap<String, Object>> search(@PathVariable String token) throws Exception {
        HashMap<String, Object> login = authService.verifyToken(token);
        return new ResponseEntity<>(login, HttpStatus.OK);
    }

    @GetMapping("/refresh-token/{token}")
    public ResponseEntity<HashMap<String, Object>> refreshToken(@PathVariable String token) throws Exception {
        HashMap<String, Object> login = authService.refreshToken(token);
        return new ResponseEntity<>(login, HttpStatus.OK);
    }
}
