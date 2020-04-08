package com.foodmobile.server.controllers;

import com.foodmobile.server.datamodels.SimpleStatusResponse;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class BasicUserController {
    @GetMapping(path="/profile/update", produces="application/json")
    public SimpleStatusResponse profileUpdate(@RequestParam String token) {
        // TODO: Decide what content needs to be updated in the profile
        return SimpleStatusResponse.success();
    }
}
