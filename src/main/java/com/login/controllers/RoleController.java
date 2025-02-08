package com.login.controllers;

import com.login.dtos.RoleAllDTO;
import com.login.services.RoleService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@RequestMapping("/role")
public class RoleController {

    @Autowired
    private RoleService roleService;

    @GetMapping("/all")
    private ResponseEntity<List<RoleAllDTO>> getAllRole(){
        return new ResponseEntity<>(roleService.findAll(), HttpStatus.OK);
    }
}
