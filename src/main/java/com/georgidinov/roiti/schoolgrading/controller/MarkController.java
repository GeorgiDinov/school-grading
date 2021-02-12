package com.georgidinov.roiti.schoolgrading.controller;


import com.georgidinov.roiti.schoolgrading.api.v1.model.MarkListDTO;
import com.georgidinov.roiti.schoolgrading.service.MarkService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api/v1/mark")
public class MarkController {

    //== fields ==
    private final MarkService markService;

    @Autowired
    public MarkController(MarkService markService) {
        this.markService = markService;
    }

    @GetMapping
    @ResponseStatus(HttpStatus.OK)
    public MarkListDTO findAllMarks() {
        return this.markService.findAllMarks();
    }


}