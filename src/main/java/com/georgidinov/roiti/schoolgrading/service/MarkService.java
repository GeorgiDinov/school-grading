package com.georgidinov.roiti.schoolgrading.service;

import com.georgidinov.roiti.schoolgrading.api.v1.model.MarkDTO;
import com.georgidinov.roiti.schoolgrading.api.v1.model.MarkListDTO;
import com.georgidinov.roiti.schoolgrading.exception.EntityValidationException;

public interface MarkService {

    MarkListDTO findAllMarks();

    MarkDTO findMarkById(Long id);

    MarkDTO saveMark(MarkDTO markDTO) throws EntityValidationException;

    MarkDTO updateMark(Long id, MarkDTO markDTO) throws EntityValidationException;

}
