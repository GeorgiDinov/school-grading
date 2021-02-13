package com.georgidinov.roiti.schoolgrading.controller;


import com.georgidinov.roiti.schoolgrading.api.v1.model.MarkDTO;
import com.georgidinov.roiti.schoolgrading.api.v1.model.MarkListDTO;
import com.georgidinov.roiti.schoolgrading.exception.EntityValidationException;
import com.georgidinov.roiti.schoolgrading.service.MarkService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestController;

import static com.georgidinov.roiti.schoolgrading.util.ApplicationConstants.MARK_BASE_URL;

@Slf4j
@RestController
@RequestMapping(MARK_BASE_URL)
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
        log.info("MarkController::findAllMarks");
        return this.markService.findAllMarks();
    }

    @GetMapping("/{id}")
    @ResponseStatus(HttpStatus.OK)
    public MarkDTO findMarkById(@PathVariable String id) {
        log.info("MarkController::findMarkById -> id passed = {}", id);
        return this.markService.findMarkById(Long.valueOf(id));
    }

    @PostMapping
    @ResponseStatus(HttpStatus.CREATED)
    public MarkDTO saveMark(@RequestBody MarkDTO markDTO) throws EntityValidationException {
        log.info("MarkController::saveMark -> markDTO passed = {}", markDTO);
        return this.markService.saveMark(markDTO);
    }

    @PutMapping("/{id}")
    @ResponseStatus(HttpStatus.OK)
    public MarkDTO updateMark(@PathVariable String id,
                              @RequestBody MarkDTO markDTO) throws EntityValidationException {
        log.info("MarkController::updateMark -> id passed = {} markDTO passed = {}", id, markDTO);
        return this.markService.updateMark(Long.valueOf(id), markDTO);
    }

    @DeleteMapping("/{id}")
    public void deleteMarkById(@PathVariable String id) {
        log.info("MarkController::deleteMarkById -> id passed = {}", id);
        this.markService.deleteMarkById(Long.valueOf(id));
    }


}