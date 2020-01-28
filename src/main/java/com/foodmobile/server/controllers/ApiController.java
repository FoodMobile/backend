package com.foodmobile.server.controllers;

import com.foodmobile.server.datamodels.ApiTestObject;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class ApiController {
    @GetMapping(path = "/api/test", produces="application/json")
    public ApiTestObject test() {
        return new ApiTestObject();
    }
}