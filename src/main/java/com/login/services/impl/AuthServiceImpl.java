package com.login.services.impl;

import com.login.client.CollaboratorServiceClient;
import com.login.dtos.CollaboratorCreateDTO;
import com.login.dtos.RequestLoginDTO;
import com.login.entities.Role;
import com.login.services.LoginService;
import com.login.services.RoleService;
import com.nimbusds.jwt.JWTClaimsSet;
import com.login.dtos.RequestRegisterDTO;
import com.login.entities.Login;
import com.login.services.AuthService;
import com.login.services.JWTUtilityService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.HashMap;
import java.util.Optional;

@Service
public class AuthServiceImpl implements AuthService {

    @Autowired
    private LoginService loginService;

    @Autowired
    private RoleService roleService;

    @Autowired
    private JWTUtilityService jwtUtilityService;

    @Autowired
    private CollaboratorServiceClient collaboratorServiceClient;

    @Override
    public HashMap<String, Object> login(RequestLoginDTO requestLoginDTO) throws Exception {
        HashMap<String, Object> response = new HashMap<>();
        try {
            Optional<Login> logindb = loginService.findByIdDocument(requestLoginDTO.getIdDocument());
            if (logindb.isEmpty()) {
                response.put("message", "User not registered");
                response.put("isSucces", false);
                return response;
            }
            if (verifyPassword(requestLoginDTO.getPassword(), logindb.get().getPassword())) {
                response.put("message", "Authentication succes");
                response.put("isSucces", true);
                response.put("token", jwtUtilityService.generateJWT(logindb.get().getIdDocument()));
                response.put("refreshToken", jwtUtilityService.generateRefreshJWT(logindb.get().getIdDocument()));
            } else {
                response.put("message", "Authentication failed");
                response.put("isSucces", false);
            }
            return response;
        } catch (IllegalArgumentException e) {
            System.err.println("Error generating JWT: " + e.getMessage());
//            throw new Exception("Error generating JWT", e);
            response.put("message", "Error generating JWT");
            response.put("isSucces", false);
            return response;
        } catch (Exception e) {
            System.err.println("Unknown error: " + e.toString());
//            throw new Exception("Unknown error", e);
            response.put("message", "Error generating JWT");
            response.put("isSucces", false);
            return response;
        }
    }

    @Override
    public HashMap<String, Object> register(RequestRegisterDTO requestRegisterDTO) throws Exception{

        HashMap<String, Object> response = new HashMap<>();

        try {
            if (requestRegisterDTO.getRole().isEmpty()) {
                response.put("message", "Parameter Role is obligatory");
                response.put("isSucces", false);
                return response;
            }
            Optional<Role> roledb = roleService.findByRoleName(requestRegisterDTO.getRole().toUpperCase());
            if (roledb.isEmpty()) {
                response.put("message", "Role does not exist");
                response.put("isSucces", false);
                return response;
            }

            Optional<Login> logindb = loginService.findByIdDocument(requestRegisterDTO.getIdDocument());
            if (logindb.isPresent()) {
                response.put("message", "User already exists");
                response.put("isSucces", false);
                return response;
            }
            Login login = Login.builder()
                .idDocument(requestRegisterDTO.getIdDocument())
                .password(requestRegisterDTO.getPassword())
                .role(requestRegisterDTO.getRole().toUpperCase())
                .status(true)
                .build();

            BCryptPasswordEncoder encoder = new BCryptPasswordEncoder(12);
            login.setPassword(encoder.encode(login.getPassword()));
            loginService.save(login);
            CollaboratorCreateDTO createDTO = CollaboratorCreateDTO.builder()
                    .idDocument(requestRegisterDTO.getIdDocument())
                    .role(requestRegisterDTO.getRole().toUpperCase())
                    .build();
            collaboratorServiceClient.create(createDTO);
            response.put("message", "User created successfully");
            response.put("isSucces", true);
            return response;
        } catch (Exception e) {
            response.put("message", "Error Exception");
            response.put("isSucces", false);
            System.out.println("Error Exception: " + e.getMessage());
//            throw new Exception(e.getMessage());
            return response;
        }
    }

    @Override
    public HashMap<String, Object> verifyToken(String jwt) throws Exception {
        HashMap<String, Object> response = new HashMap<>();
        try {
            response.put("message", "Authentication..");
            response.put("isSucces", jwtUtilityService.verifyJWT(jwt));
            return response;
        } catch (Exception e) {
            response.put("message", "Authentication failed");
            response.put("isSucces", false);
            return response;
        }
    }

    @Override
    public HashMap<String, Object> refreshToken(String jwt) throws Exception {
        HashMap<String, Object> response = new HashMap<>();
        try {
            boolean verifyJWT = jwtUtilityService.verifyJWT(jwt);
            if (!verifyJWT) {
                response.put("message", "Authentication failed");
                response.put("isSucces", false);
                return response;
            }
            JWTClaimsSet parseJWT = jwtUtilityService.parseJWT(jwt);
            String idDocument = parseJWT.getSubject();
            response.put("isSucces", true);
            response.put("message", "Authentication succes");
            response.put("token", jwtUtilityService.generateJWT(idDocument));
            return response;
        } catch (Exception e) {
            response.put("message", "Authentication failed");
            response.put("isSucces", false);
            return response;
        }
    }

    private boolean verifyPassword(String enteredPassword, String storedPassword) {
        BCryptPasswordEncoder encoder = new BCryptPasswordEncoder();
        return encoder.matches(enteredPassword, storedPassword);
    }
}
