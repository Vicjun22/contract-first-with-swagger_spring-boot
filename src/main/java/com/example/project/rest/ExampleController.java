package com.example.project.rest;

import org.openapitools.api.PublicApi;
import org.openapitools.model.Response;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;

@Controller
public class ExampleController implements PublicApi {

    private ExampleController() { }

    @Override
    public ResponseEntity<Response> getExampleResponse() {
        Response response = new Response();
        response.setCode("200");
        response.setMessage("OK!");

        return ResponseEntity.ok(response);
    }
}
