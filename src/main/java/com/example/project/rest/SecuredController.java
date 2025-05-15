package com.example.project.rest;

import org.openapitools.api.SecurityApi;
import org.openapitools.model.Response;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;

@Controller
public class SecuredController implements SecurityApi {

    private SecuredController() { }

    @Override
    public ResponseEntity<Response> getSecuredData() {
        Response response = new Response();
        response.setCode("200");
        response.setMessage("OK!");

        return ResponseEntity.ok(response);
    }
}
