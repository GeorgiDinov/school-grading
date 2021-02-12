package com.georgidinov.roiti.schoolgrading.service;

import com.georgidinov.roiti.schoolgrading.api.v1.model.MarkDTO;
import com.georgidinov.roiti.schoolgrading.api.v1.model.MarkListDTO;

public interface MarkService {

    MarkListDTO findAllMarks();

    MarkDTO findMarkById(Long id);

}
