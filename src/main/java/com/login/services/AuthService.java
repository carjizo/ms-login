package com.login.services;

import com.login.dtos.RequestLoginDTO;
import com.login.dtos.RequestRegisterDTO;

import java.util.HashMap;

public interface AuthService {

    public HashMap<String, Object> login(RequestLoginDTO requestLoginDTO) throws Exception;
    public HashMap<String, Object>  register(RequestRegisterDTO requestRegisterDTO) throws Exception;
    public HashMap<String, Object> verifyToken(String jwt) throws Exception;
    public HashMap<String, Object> refreshToken(String jwt) throws Exception;
}
