package com.login.client;

import com.login.dtos.CollaboratorCreateDTO;
import com.login.dtos.CollaboratorUpdateDTO;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;

import java.util.HashMap;

@FeignClient(name = "collaborator", url = "http://localhost:8083")
public interface CollaboratorServiceClient {

    @PostMapping("/collaborator/create")
    ResponseEntity<HashMap<String, Object>> create(@RequestBody CollaboratorCreateDTO createDTO);

    @PostMapping("/collaborator/update")
    ResponseEntity<HashMap<String, Object>> update(@RequestBody CollaboratorUpdateDTO updateDTO);
}
