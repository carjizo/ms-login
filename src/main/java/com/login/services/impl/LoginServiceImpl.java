package com.login.services.impl;

import com.login.client.CollaboratorServiceClient;
import com.login.dtos.CollaboratorUpdateDTO;
import com.login.dtos.RequestUpdatePasswordDTO;
import com.login.entities.Login;
import com.login.repositories.LoginRepository;
import com.login.services.LoginService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.HashMap;
import java.util.List;
import java.util.Optional;

@Service
public class LoginServiceImpl implements LoginService {

    @Autowired
    CollaboratorServiceClient collaboratorServiceClient;
    @Autowired
    LoginRepository loginRepository;

    @Override
    public List<Login> findAll() {
        return loginRepository.findAll();
    }

    @Override
    public Optional<Login> findByIdDocument(String idDocument) {
        return loginRepository.findByIdDocument(idDocument);
    }

    @Override
    public void save(Login login) {
        loginRepository.save(login);
    }

    @Override
    public HashMap<String, Object> updatePassword(RequestUpdatePasswordDTO updatePasswordDTO) throws Exception {
        HashMap<String, Object> response = new HashMap<>();
        try {
            Optional<Login> logindb = loginRepository.findByIdDocument(updatePasswordDTO.getIdDocument());
            if (logindb.isEmpty()) {
                response.put("message", "User not exists");
                response.put("isSucces", false);
                return response;
            }
            Login login = Login.builder()
                    .idDocument(logindb.get().getIdDocument())
                    .password(logindb.get().getPassword())
                    .role(logindb.get().getRole())
                    .status(true)
                    .build();
            BCryptPasswordEncoder encoder = new BCryptPasswordEncoder(12);
            login.setPassword(encoder.encode(updatePasswordDTO.getPassword()));
            loginRepository.save(login);
            response.put("message", "User update successfully");
            response.put("isSucces", true);
            return response;
        } catch (Exception e) {
            response.put("message", "Error Exception");
            response.put("isSucces", false);
            System.out.println("Error Exception: " + e.getMessage());
            return response;
        }
    }

    @Override
    public HashMap<String, Object> update(CollaboratorUpdateDTO collaboratorUpdateDTO) throws Exception {
        HashMap<String, Object> response = new HashMap<>();
        try {
            Optional<Login> logindb = loginRepository.findByIdDocument(collaboratorUpdateDTO.getIdDocument());
            if (logindb.isEmpty()) {
                response.put("message", "User not exists");
                response.put("isSucces", false);
                return response;
            }
            loginRepository.update(collaboratorUpdateDTO.getIdDocument(), collaboratorUpdateDTO.getRole(),
                    collaboratorUpdateDTO.isStatus());
            collaboratorServiceClient.update(collaboratorUpdateDTO);
            response.put("message", "User update successfully");
            response.put("isSucces", true);
            return response;
        } catch (Exception e) {
            response.put("message", "Error Exception");
            response.put("isSucces", false);
            System.out.println("Error Exception: " + e.getMessage());
            return response;
        }
    }

}
