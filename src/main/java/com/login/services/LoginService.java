package com.login.services;

import com.login.dtos.CollaboratorUpdateDTO;
import com.login.dtos.RequestUpdatePasswordDTO;
import com.login.entities.Login;

import java.util.HashMap;
import java.util.List;
import java.util.Optional;

public interface LoginService {

    List<Login> findAll();
    Optional<Login> findByIdDocument(String idDocument);
    void save(Login login);
    HashMap<String, Object> updatePassword(RequestUpdatePasswordDTO updatePasswordDTO) throws Exception;
    HashMap<String, Object> update(CollaboratorUpdateDTO collaboratorUpdateDTO) throws Exception;
}
