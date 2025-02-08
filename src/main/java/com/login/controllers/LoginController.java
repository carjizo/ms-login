package com.login.controllers;

import com.login.dtos.CollaboratorUpdateDTO;
import com.login.dtos.RequestUpdatePasswordDTO;
import com.login.services.LoginService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.HashMap;

@RestController
@RequestMapping("/login")
public class LoginController {

    @Autowired
    LoginService loginService;

    @PostMapping("/update-password")
    private ResponseEntity<HashMap<String, Object>> updatePassword(@RequestBody RequestUpdatePasswordDTO updatePasswordDTO) throws Exception {
        return new ResponseEntity<>(loginService.updatePassword(updatePasswordDTO), HttpStatus.OK);
    }

    @PostMapping("/update")
    private ResponseEntity<HashMap<String, Object>> update(@RequestBody CollaboratorUpdateDTO collaboratorUpdateDTO) throws Exception {
        return new ResponseEntity<>(loginService.update(collaboratorUpdateDTO), HttpStatus.OK);
    }
}
