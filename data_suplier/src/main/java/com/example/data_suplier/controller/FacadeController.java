package com.example.data_suplier.controller;

import com.example.data_suplier.model.Information;
import com.example.data_suplier.service.InformationService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

import static java.util.stream.Collectors.toList;

@RestController
public class FacadeController {

    @Autowired
    private InformationService informationService;

    @GetMapping("/")
    public List<Information> index() {
        return informationService.getAll();
    }

    @GetMapping("/ids")
    public List<String> getAllIds() {
        return informationService.getAll().stream().map(Information::getId).collect(toList());
    }

    @GetMapping("/info/{id}")
    public Information getById(@PathVariable("id") String id) {
        return informationService.getById(id).orElse(null);
        //return informationService.getById(id).orElseGet(Information::new);
    }
}
