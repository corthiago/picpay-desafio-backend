package com.thiago.picpaybackendchallenge.mock;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.HashMap;
import java.util.Map;

@RestController
@RequestMapping("/mock")
public class MockController {

    @GetMapping("/authorize")
    public Map<String, Object> authorize(){
        // { "status" : "success", "data" : { "authorization" : true } }
        Map<String, Object> response = new HashMap<>();
        return Map.of("status", "success",
                      "data", Map.of("authorization", true));
    }

}
